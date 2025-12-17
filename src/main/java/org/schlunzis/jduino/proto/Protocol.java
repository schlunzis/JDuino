package org.schlunzis.jduino.proto;

public interface Protocol<P extends Protocol<P>> {

    MessageEncoder<P> getMessageEncoder();

    MessageDecoder<P> getMessageDecoder();

}
