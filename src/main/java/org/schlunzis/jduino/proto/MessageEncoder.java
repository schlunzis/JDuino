package org.schlunzis.jduino.proto;

public interface MessageEncoder<T extends Message> {

    byte[] encode(T message);

}
