package org.schlunzis.jduino.protocol.tlv;

import org.schlunzis.jduino.protocol.MessageDecoder;
import org.schlunzis.jduino.protocol.MessageEncoder;
import org.schlunzis.jduino.protocol.Protocol;

public class TLV implements Protocol<TLV> {

    private final MessageEncoder<TLV> encoder = new TLVMessageEncoder();
    private final MessageDecoder<TLV> decoder = new TLVMessageDecoder();

    @Override
    public MessageEncoder<TLV> getMessageEncoder() {
        return encoder;
    }

    @Override
    public MessageDecoder<TLV> getMessageDecoder() {
        return decoder;
    }

}
