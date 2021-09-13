package dev.phonis.schemupload.schematica;

import com.github.lunatrius.schematica.api.ISchematic;
import dev.phonis.schemupload.networking.SUChannel;
import dev.phonis.schemupload.networking.SUPacket;
import dev.phonis.schemupload.networking.StartUpload;
import dev.phonis.schemupload.networking.UploadSegment;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class SchemUtils {

    public static void uploadSchem() {
        try {
            BlockPos.MutableBlockPos startPosition = new BlockPos.MutableBlockPos();
            byte[] schemData = SchemUtils.getLoadedSchemData(startPosition);
            StartUpload startUpload = new StartUpload(schemData.length, startPosition);
            List<SUPacket> packets = new LinkedList<>();
            int current = 0;
            int left = schemData.length;

            packets.add(startUpload);

            while (left > 0) {
                int currentChunk = Math.min(left, UploadSegment.maxLength);
                byte[] segmentData = new byte[currentChunk];

                System.arraycopy(schemData, current, segmentData, 0, currentChunk);

                left -= currentChunk;
                current += currentChunk;

                packets.add(new UploadSegment(segmentData));
            }

            packets.forEach(packet -> SUChannel.instance.send(packet));
        } catch (Throwable e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Failed to retrieve loaded schematica."));
            e.printStackTrace();
        }
    }

    private static byte[] getLoadedSchemData(BlockPos.MutableBlockPos startPosition) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, NoSuchFieldException, NullPointerException {
        Class<?> renderSchematicClass = Class.forName("com.github.lunatrius.schematica.client.renderer.RenderSchematic");
        Field worldField = renderSchematicClass.getDeclaredField("world");

        worldField.setAccessible(true);

        Field instanceField = renderSchematicClass.getDeclaredField("INSTANCE");
        Object renderSchematic = worldField.get(instanceField.get(null));
        Field schematicField = renderSchematic.getClass().getDeclaredField("schematic");

        schematicField.setAccessible(true);

        Field positionField = renderSchematic.getClass().getDeclaredField("position");

        positionField.setAccessible(true);

        ISchematic schematic = (ISchematic) schematicField.get(renderSchematic);
        final BlockPos position = (BlockPos) positionField.get(renderSchematic);

        startPosition.set(position.getX(), position.getY(), position.getZ());

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        Class<?> schematicaAlphaClass = Class.forName("com.github.lunatrius.schematica.world.schematic.SchematicAlpha");
        Object schematicaAlpha = schematicaAlphaClass.newInstance();
        Method writeToNBTMethod = schematicaAlphaClass.getMethod("writeToNBT", NBTTagCompound.class, ISchematic.class);

        writeToNBTMethod.invoke(schematicaAlpha, nbtTagCompound, schematic);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(baos))) {
            Method writeEntryMethod = NBTTagCompound.class.getDeclaredMethod("func_150298_a", String.class, NBTBase.class, DataOutput.class);

            writeEntryMethod.setAccessible(true);
            writeEntryMethod.invoke(null, "Schematic", nbtTagCompound, dataOutputStream);
        } catch (NoSuchMethodException ignored) { }

        return baos.toByteArray();
    }

}
