package dev.phonis.schemupload.networking;

import dev.phonis.schemupload.SchemUploadMod;
import net.minecraft.network.INetHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SUChannel extends PluginChannel {

    public static SUChannel instance;

    public SUChannel(String name) {
        super(name);
    }

    public static void initialize() {
        SUChannel.instance = new SUChannel(SchemUploadMod.channelName);
    }

    @Override
    public void onMessage(byte[] in, INetHandler netHandler) {

    }

    public void send(SUPacket packet) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeByte(packet.packetID());
            packet.toBytes(dos);
            this.sendToServer(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
