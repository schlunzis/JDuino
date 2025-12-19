package org.schlunzis.jduino.channel;

import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

@FunctionalInterface
public interface ChannelMessageListener {

    void onMessageReceived(Message message);

}
