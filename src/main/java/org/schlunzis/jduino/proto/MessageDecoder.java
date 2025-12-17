package org.schlunzis.jduino.proto;

public interface MessageDecoder<T extends Message> {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    T getDecodedMessage();

}
