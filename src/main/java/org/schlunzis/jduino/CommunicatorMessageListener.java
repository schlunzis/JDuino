package org.schlunzis.jduino;

@FunctionalInterface
public interface CommunicatorMessageListener {

    void onMessageReceived(byte command, String message);

}
