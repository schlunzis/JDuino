package org.schlunzis.jduino.proto;

public interface MessageDecoder<P extends Protocol> {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    Message<P> getDecodedMessage();

}
