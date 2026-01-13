package org.schlunzis.jduino.protocol.tlv;

import org.schlunzis.jduino.protocol.Message;

public record TLVMessage(
        byte type,
        byte[] value
) implements Message {

    public byte length() {
        return (byte) value.length;
    }

    @Override
    public byte getMessageType() {
        return type;
    }

    @Override
    public byte[] getPayload() {
        return value;
    }

}
