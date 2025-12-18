package org.schlunzis.jduino.protocol.tlv;

import org.schlunzis.jduino.protocol.Message;

public record TLVMessage(
        byte type,
        byte length,
        byte[] value
) implements Message<TLV> {
}
