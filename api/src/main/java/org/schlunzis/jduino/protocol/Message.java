package org.schlunzis.jduino.protocol;

public interface Message {

    byte getMessageType();

    byte[] getPayload();

}
