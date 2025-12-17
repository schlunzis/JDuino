package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.Protocol;

@FunctionalInterface
public interface ChannelMessageListener<P extends Protocol> {

    void onMessageReceived(Message<P> message);

}
