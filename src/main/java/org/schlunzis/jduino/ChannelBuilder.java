package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Protocol;

public class ChannelBuilder<P extends Protocol, C extends Channel<P>> {

    private final P protocol;
    private final ChannelFactory<P> channelFactory;

    public ChannelBuilder(P protocol, ChannelFactory<P> channelFactory) {
        this.protocol = protocol;
        this.channelFactory = channelFactory;
    }

    public static ChannelBuilder<?, ?> builder() {
        return new ChannelBuilder<>(null, null);
    }

    public <Proto extends Protocol> ChannelBuilder<Proto, Channel<Proto>> protocol(Proto protocol) {
        return new ChannelBuilder<>(protocol, null);
    }

    public ChannelBuilder<P, C> channelFactory(ChannelFactory<P> channelFactory) {
        return new ChannelBuilder<>(protocol, channelFactory);
    }

    public Channel<P> build() {
        if (protocol == null) {
            throw new IllegalStateException("Protocol must be set before building the channel.");
        }
        if (channelFactory == null) {
            throw new IllegalStateException("ChannelFactory must be set before building the channel.");
        }

        return channelFactory.createChannel(protocol);
    }

}
