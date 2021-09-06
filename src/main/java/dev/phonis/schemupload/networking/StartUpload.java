package dev.phonis.schemupload.networking;

import net.minecraft.util.BlockPos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StartUpload implements SUPacket {

    public final int length;
    public final int startX;
    public final int startY;
    public final int startZ;

    public StartUpload(int length, int startX, int startY, int startZ) {
        this.length = length;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public StartUpload(int length, BlockPos blockPos) {
        this(length, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public byte packetID() {
        return Packet.Out.startUploadID;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException {
        dos.writeInt(this.length);
        dos.writeInt(this.startX);
        dos.writeInt(this.startY);
        dos.writeInt(this.startZ);
    }

    public static StartUpload fromBytes(DataInputStream in) throws IOException {
        return new StartUpload(in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }

}
