package org.schlunzis.jduino.proto;

public interface MessageDecoder<P extends Protocol<P>> {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    Message<P> getDecodedMessage();

}
