package org.schlunzis.jduino.proto.tlv;

import org.schlunzis.jduino.proto.Message;

public record TLVMessage(
        byte type,
        byte length,
        byte[] value
) implements Message {
}
