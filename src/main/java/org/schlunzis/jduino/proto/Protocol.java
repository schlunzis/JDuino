package org.schlunzis.jduino.proto;

public interface Protocol<P extends Protocol<?>> {

    MessageEncoder<P> getMessageEncoder();

    MessageDecoder<P> getMessageDecoder();

}
