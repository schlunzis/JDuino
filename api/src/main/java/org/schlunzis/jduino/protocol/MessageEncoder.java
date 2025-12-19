package org.schlunzis.jduino.protocol;

public interface MessageEncoder {

    byte[] encode(Message message);

}
