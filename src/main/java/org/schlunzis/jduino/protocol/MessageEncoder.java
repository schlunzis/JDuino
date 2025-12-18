package org.schlunzis.jduino.protocol;

public interface MessageEncoder<P extends Protocol<P>> {

    byte[] encode(Message<P> message);

}
