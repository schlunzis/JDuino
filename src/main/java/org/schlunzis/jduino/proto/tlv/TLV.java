package org.schlunzis.jduino.proto.tlv;

import org.schlunzis.jduino.proto.MessageDecoder;
import org.schlunzis.jduino.proto.MessageEncoder;
import org.schlunzis.jduino.proto.Protocol;

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
