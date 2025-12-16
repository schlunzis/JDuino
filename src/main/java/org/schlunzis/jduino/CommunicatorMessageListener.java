package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Message;

@FunctionalInterface
public interface CommunicatorMessageListener<M extends Message> {

    void onMessageReceived(M message);

}
