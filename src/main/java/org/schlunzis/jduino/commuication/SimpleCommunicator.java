package org.schlunzis.jduino.commuication;

import java.nio.charset.StandardCharsets;

public class SimpleCommunicator extends SerialCommunicator {

    public void sendEchoCommand(String msg) {
        sendCommand(Protocol.CMD_ECHO, msg.getBytes(StandardCharsets.UTF_8));
    }

    public void sendLEDCommand(int ledPin, boolean state) {
        byte[] payload = new byte[2];
        payload[0] = (byte) ledPin;
        payload[1] = (byte) (state ? 1 : 0);
        sendCommand(Protocol.CMD_LED, payload);
    }

    public void sendLCDCommand(String msg) {
        sendCommand(Protocol.CMD_LCD, msg.getBytes(StandardCharsets.UTF_8));
    }
}
