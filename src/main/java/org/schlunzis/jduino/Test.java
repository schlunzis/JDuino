package org.schlunzis.jduino;

import org.schlunzis.jduino.channel.serial.SerialChannel;
import org.schlunzis.jduino.proto.tlv.TLV;

public class Test {

    static void main() {
        Channel<TLV> com = ChannelBuilder.builder()
                .protocol(new TLV())
                .channelFactory(SerialChannel::new)
                .build();
        com.close();
    }

}
