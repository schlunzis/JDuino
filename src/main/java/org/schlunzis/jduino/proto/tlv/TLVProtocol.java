package org.schlunzis.jduino.proto.tlv;

import org.schlunzis.jduino.proto.MessageDecoder;
import org.schlunzis.jduino.proto.MessageEncoder;
import org.schlunzis.jduino.proto.Protocol;

public class TLVProtocol implements Protocol<TLVMessage> {

    @Override
    public MessageEncoder<TLVMessage> getMessageEncoder() {
        return new TLVMessageEncoder();
    }

    @Override
    public MessageDecoder<TLVMessage> getMessageDecoder() {
        return new TLVMessageDecoder();
    }
}
