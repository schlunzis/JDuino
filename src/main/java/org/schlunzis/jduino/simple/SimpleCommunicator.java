package org.schlunzis.jduino.simple;

import org.schlunzis.jduino.SerialCommunicator;
import org.schlunzis.jduino.proto.MessageEncoder;
import org.schlunzis.jduino.proto.tlv.TLVMessage;

import java.nio.charset.StandardCharsets;

public class SimpleCommunicator extends SerialCommunicator<TLVMessage> {


    public SimpleCommunicator(MessageEncoder<TLVMessage> messageEncoder) {
        super(messageEncoder);
    }

    public void sendEchoCommand(String msg) {
        sendMessage(new TLVMessage(SimpleProtocol.CMD_ECHO.getCode(), (byte) msg.getBytes(StandardCharsets.UTF_8).length, msg.getBytes(StandardCharsets.UTF_8)));
    }

    public void sendLEDCommand(int ledPin, boolean state) {
        byte[] payload = new byte[2];
        payload[0] = (byte) ledPin;
        payload[1] = (byte) (state ? 1 : 0);
        sendMessage(new TLVMessage(SimpleProtocol.CMD_LED.getCode(), (byte) payload.length, payload));

    }
}
