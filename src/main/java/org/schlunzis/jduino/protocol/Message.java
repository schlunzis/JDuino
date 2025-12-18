package org.schlunzis.jduino.protocol;

public interface Message<P extends Protocol<P>> {

    byte getMessageType();

    byte[] getPayload();

}
