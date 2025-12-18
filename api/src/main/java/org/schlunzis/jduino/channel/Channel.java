package org.schlunzis.jduino.channel;

import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

import java.util.List;

public interface Channel<P extends Protocol<P>> {

    static <P extends Protocol<P>, C extends Channel<P>> ChannelBuilder<P, C> builder() {
        return new ChannelBuilder<>(null, null);
    }

    void open(DeviceConfiguration deviceConfiguration);

    void close();

    void sendMessage(Message<P> message);

    List<? extends Device> getDevices();

    void addMessageListener(ChannelMessageListener<P> listener);

    void removeMessageListener(ChannelMessageListener<P> listener);

    boolean isConnected();

    @FunctionalInterface
    interface ChannelFactory<P extends Protocol<P>, C extends Channel<P>> {

        C createChannel(P protocol);

    }

    class ChannelBuilder<P extends Protocol<P>, C extends Channel<P>> {

        protected P protocol;
        protected ChannelFactory<P, C> channelFactory;

        public ChannelBuilder(P protocol, ChannelFactory<P, C> channelFactory) {
            this.protocol = protocol;
            this.channelFactory = channelFactory;
        }

        public <P2 extends Protocol<P2>> ChannelBuilder<P2, Channel<P2>> protocol(P2 protocol) {
            return new ChannelBuilder<>(protocol, null);
        }

        public <C2 extends Channel<P>> ChannelBuilder<P, C2> channelFactory(ChannelFactory<P, C2> channelFactory) {
            return new ChannelBuilder<>(protocol, channelFactory);
        }

        public C build() {
            if (protocol == null) {
                throw new IllegalStateException("Protocol must be set before building the channel.");
            }
            if (channelFactory == null) {
                throw new IllegalStateException("ChannelFactory must be set before building the channel.");
            }

            return channelFactory.createChannel(protocol);
        }

    }

}
