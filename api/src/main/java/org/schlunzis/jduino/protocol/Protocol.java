package org.schlunzis.jduino.protocol;

public interface Protocol {

    MessageEncoder getMessageEncoder();

    MessageDecoder getMessageDecoder();

}
