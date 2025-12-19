package org.schlunzis.jduino.protocol.tlv;

import org.schlunzis.jduino.protocol.MessageDecoder;
import org.schlunzis.jduino.protocol.MessageEncoder;
import org.schlunzis.jduino.protocol.Protocol;

public class TLV implements Protocol {

    private final MessageEncoder encoder = new TLVMessageEncoder();
    private final MessageDecoder decoder = new TLVMessageDecoder();

    @Override
    public MessageEncoder getMessageEncoder() {
        return encoder;
    }

    @Override
    public MessageDecoder getMessageDecoder() {
        return decoder;
    }

}
