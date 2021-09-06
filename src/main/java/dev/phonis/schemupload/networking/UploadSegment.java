package dev.phonis.schemupload.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UploadSegment implements SUPacket {

    public static final short maxLength = 30000;

    public final int length;
    public final byte[] payload;

    public UploadSegment(byte[] payload) {
        this.length = payload.length;
        this.payload = payload;
    }

    @Override
    public byte packetID() {
        return Packet.Out.uploadSegmentID;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException {
        dos.writeInt(this.length);
        dos.write(this.payload);
    }

    public static UploadSegment fromBytes(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        byte[] data = new byte[size];

        dis.readFully(data);

        return new UploadSegment(data);
    }

}
