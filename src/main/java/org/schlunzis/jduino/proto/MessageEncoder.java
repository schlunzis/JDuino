package org.schlunzis.jduino.proto;

public interface MessageEncoder<T> {

    byte[] encode(T message);

}
