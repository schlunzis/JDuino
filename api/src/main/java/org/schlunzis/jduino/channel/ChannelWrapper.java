package org.schlunzis.jduino.channel;

import org.jspecify.annotations.Nullable;
import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

import java.util.List;

public class ChannelWrapper extends AbstractChannel {

    @Nullable
    private ChannelFactory<Protocol, Channel> channelFactory;
    @Nullable
    private Channel channel;

    public ChannelWrapper() {
    }

    public void setChannelFactory(ChannelFactory<Protocol, Channel> channelFactory) {
        this.channelFactory = channelFactory;
    }

    public void setProtocol(Protocol protocol) {
        if (this.channel != null) {
            this.channel.close();
        }
        if (channelFactory != null) {
            this.channel = channelFactory.createChannel(protocol);
            channelMessageListeners.forEach(channel::addMessageListener);
        } else {
            throw new IllegalStateException("ChannelBuilder is not set. Cannot create channel.");
        }
    }

    @Override
    public void open(DeviceConfiguration deviceConfiguration) {
        if (this.channel == null) {
            throw new IllegalStateException("Protocol not set. Call setProtocol() before opening the channel.");
        }
        this.channel.open(deviceConfiguration);
    }

    @Override
    public void close() {
        if (this.channel != null) {
            this.channel.close();
        }
        this.channel = null;
    }

    @Override
    public void sendMessage(Message message) {
        if (this.channel == null) {
            throw new IllegalStateException("Channel is not open. Cannot send message.");
        }
        this.channel.sendMessage(message);
    }

    @Override
    public List<? extends Device> getDevices() {
        if (this.channel == null) {
            throw new IllegalStateException("Channel is not open. Cannot get devices.");
        }

        return this.channel.getDevices();
    }

    @Override
    public boolean isConnected() {
        return this.channel != null && this.channel.isConnected();
    }
}
