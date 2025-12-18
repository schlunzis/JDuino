package org.schlunzis.jduino.protocol.tlv;

import org.schlunzis.jduino.protocol.MessageDecoder;

import java.io.ByteArrayOutputStream;

public class TLVMessageDecoder implements MessageDecoder<TLV> {

    private final ByteArrayOutputStream payload = new ByteArrayOutputStream();
    private byte type = -1;
    private int expectedLength = -1;

    public void pushNextByte(byte next) {
        if (type < 0) {
            // First byte = message type
            type = next;
        } else if (expectedLength < 0) {
            // Second byte = message length
            expectedLength = next & 0xFF;  // convert to unsigned
            payload.reset();
        } else {
            // Subsequent bytes = payload
            payload.write(next);
        }
    }

    public boolean isMessageComplete() {
        return type >= 0 && expectedLength >= 0 && payload.size() == expectedLength;
    }

    public TLVMessage getDecodedMessage() {
        TLVMessage message = new TLVMessage(type, payload.toByteArray());
        reset();
        return message;
    }

    private void reset() {
        type = -1;
        expectedLength = -1;
        payload.reset();
    }

}
