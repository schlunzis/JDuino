package org.schlunzis.jduino.simple;

import org.schlunzis.jduino.channel.serial.SerialChannel;
import org.schlunzis.jduino.protocol.tlv.TLV;
import org.schlunzis.jduino.protocol.tlv.TLVMessage;

import java.nio.charset.StandardCharsets;

public class SimpleChannel extends SerialChannel<TLV> {

    private static final byte CMD_ECHO = ((byte) 0x01);
    private static final byte CMD_BUTTON = ((byte) 0x02);
    private static final byte CMD_LED = ((byte) 0x03);
    private static final byte CMD_LCD = ((byte) 0x04);

    public SimpleChannel(TLV protocol) {
        super(protocol);
    }

    public void sendEchoCommand(String msg) {
        sendMessage(new TLVMessage(CMD_ECHO, (byte) msg.getBytes(StandardCharsets.UTF_8).length, msg.getBytes(StandardCharsets.UTF_8)));
    }

    public void sendLEDCommand(int ledPin, boolean state) {
        byte[] payload = new byte[2];
        payload[0] = (byte) ledPin;
        payload[1] = (byte) (state ? 1 : 0);
        sendMessage(new TLVMessage(CMD_LED, (byte) payload.length, payload));
    }
}
