package org.schlunzis.jduino;

import org.schlunzis.jduino.connection.Connection;
import org.schlunzis.jduino.proto.tlv.TLVMessage;
import org.schlunzis.jduino.simple.SimpleCommandType;

import java.nio.charset.StandardCharsets;

public class Test {

    static void main() {
        Connection<TLVMessage> conn1 = Connection.builder().serial().tlv().build();
        conn1.open();

        conn1.addListener(message -> System.out.println("Received message: " + new String(message.value(), StandardCharsets.UTF_8)));

        byte[] msg = "Hello, Arduino!".getBytes(StandardCharsets.UTF_8);
        conn1.sendMessage(new TLVMessage(SimpleCommandType.CMD_ECHO.getCode(), (byte) msg.length, msg));

        byte[] ledPayload = new byte[2];
        ledPayload[0] = 13; // LED pin
        ledPayload[1] = 1;  // ON
        conn1.sendMessage(new TLVMessage(SimpleCommandType.CMD_LED.getCode(), (byte) ledPayload.length, ledPayload));
        conn1.close();

    }

}
