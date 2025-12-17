package org.schlunzis.jduino.proto.tlv;

import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.MessageEncoder;

public class TLVMessageEncoder implements MessageEncoder<TLV> {

    @Override
    public byte[] encode(Message<TLV> m) {
        if (!(m instanceof TLVMessage message)) {
            throw new IllegalArgumentException("Invalid message type");
        }
        if (message.value().length > 255) {
            throw new IllegalArgumentException("Message payload too long");
        }

        byte[] encoded = new byte[2 + message.value().length];
        encoded[0] = message.type();
        encoded[1] = (byte) message.value().length;
        System.arraycopy(message.value(), 0, encoded, 2, message.value().length);
        return encoded;
    }

}
