package org.schlunzis.jduino;

@FunctionalInterface
public interface CommunicatorMessageListener {

    void onMessageReceived(String message);

}
