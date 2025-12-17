package org.schlunzis.jduino.proto;

public interface Protocol<T extends Message> {

    MessageEncoder<T> getMessageEncoder();

    MessageDecoder<T> getMessageDecoder();

}
