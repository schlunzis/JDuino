package org.schlunzis.jduino;

import org.schlunzis.jduino.channel.serial.SerialChannel;
import org.schlunzis.jduino.proto.tlv.TLV;
import org.schlunzis.jduino.simple.SimpleChannel;

public class Test {

    static void main() {
        SerialChannel<TLV> com = ChannelBuilder.builder()
                .protocol(new TLV())
                .channelFactory(SerialChannel::new)
                .build();
        com.close();

        SimpleChannel simpleCom = ChannelBuilder.builder()
                .protocol(new TLV())
                .channelFactory(SimpleChannel::new)
                .build();

        simpleCom.close();
    }

}
