package org.schlunzis.jduino.protocol;

public interface MessageDecoder {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    Message getDecodedMessage();

}
