package org.schlunzis.jduino.proto;

public interface MessageEncoder<P extends Protocol<P>> {

    byte[] encode(Message<P> message);

}
