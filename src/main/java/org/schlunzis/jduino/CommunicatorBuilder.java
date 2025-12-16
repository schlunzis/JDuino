package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.tlv.TLVMessage;
import org.schlunzis.jduino.proto.tlv.TLVMessageEncoder;

public class CommunicatorBuilder {

    public static CommunicatorBuilder builder() {
        return new CommunicatorBuilder();
    }

    public SerialCommunicatorBuilder serial() {
        return new SerialCommunicatorBuilder();
    }

    public static class SerialCommunicatorBuilder {
        public SerialTLVCommunicatorBuilder tlv() {
            return new SerialTLVCommunicatorBuilder();
        }
    }

    public static class SerialTLVCommunicatorBuilder extends SerialCommunicatorBuilder {
        public SerialCommunicator<TLVMessage> build() {
            return new SerialCommunicator<>(new TLVMessageEncoder());
        }
    }

}
