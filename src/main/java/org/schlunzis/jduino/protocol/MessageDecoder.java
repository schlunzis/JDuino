package org.schlunzis.jduino.protocol;

public interface MessageDecoder<P extends Protocol<P>> {

    void pushNextByte(byte next);

    boolean isMessageComplete();

    Message<P> getDecodedMessage();

}
