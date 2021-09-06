package dev.phonis.schemupload.networking;

import java.io.DataOutputStream;
import java.io.IOException;

public interface SUSerializable {

    void toBytes(DataOutputStream dos) throws IOException;

}
