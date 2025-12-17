package org.schlunzis.jduino.proto;

public interface MessageEncoder<P extends Protocol> {

    byte[] encode(Message<P> message);

}
