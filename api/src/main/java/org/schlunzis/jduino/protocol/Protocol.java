package org.schlunzis.jduino.protocol;

public interface Protocol<P extends Protocol<P>> {

    MessageEncoder<P> getMessageEncoder();

    MessageDecoder<P> getMessageDecoder();

}
