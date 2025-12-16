package org.schlunzis.jduino.proto;

public interface MessageDecoder<T> {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    T getDecodedMessage();

}
