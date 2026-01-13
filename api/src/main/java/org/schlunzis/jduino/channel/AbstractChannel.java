package org.schlunzis.jduino.channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractChannel implements Channel {

    protected final List<ChannelMessageListener> channelMessageListeners = new CopyOnWriteArrayList<>();

    @Override
    public void addMessageListener(ChannelMessageListener listener) {
        channelMessageListeners.add(listener);
    }

    @Override
    public void removeMessageListener(ChannelMessageListener listener) {
        channelMessageListeners.remove(listener);
    }

}
