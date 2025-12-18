package org.schlunzis.jduino.simple;

import org.schlunzis.jduino.channel.serial.SerialChannel;
import org.schlunzis.jduino.protocol.tlv.TLV;
import org.schlunzis.jduino.protocol.tlv.TLVMessage;

import java.nio.charset.StandardCharsets;

public class SimpleChannel extends SerialChannel<TLV> {

    public static final byte CMD_ECHO = ((byte) 0x01);
    public static final byte CMD_BUTTON = ((byte) 0x02);
    public static final byte CMD_LED = ((byte) 0x03);
    public static final byte CMD_LCD = ((byte) 0x04);

    public SimpleChannel(TLV protocol) {
        super(protocol);
    }

    public static SimpleChannel create() {
        return SerialChannel.builder()
                .protocol(new TLV())
                .channelFactory(SimpleChannel::new)
                .build();
    }

    public void sendEchoCommand(String msg) {
        sendMessage(new TLVMessage(CMD_ECHO, msg.getBytes(StandardCharsets.UTF_8)));
    }

    public void sendLEDCommand(int ledPin, boolean state) {
        byte[] payload = new byte[2];
        payload[0] = (byte) ledPin;
        payload[1] = (byte) (state ? 1 : 0);
        sendMessage(new TLVMessage(CMD_LED, payload));
    }
}
