package org.schlunzis.jduino.channel;

import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

import java.util.List;

public interface Channel {

    static <P extends Protocol, C extends Channel> ChannelBuilder<P, C> builder() {
        return new ChannelBuilder<>(null, null);
    }

    void open(DeviceConfiguration deviceConfiguration);

    void close();

    void sendMessage(Message message);

    List<? extends Device> getDevices();

    void addMessageListener(ChannelMessageListener listener);

    void removeMessageListener(ChannelMessageListener listener);

    boolean isConnected();

    @FunctionalInterface
    interface ChannelFactory<P extends Protocol, C extends Channel> {

        C createChannel(P protocol);

    }

    class ChannelBuilder<P extends Protocol, C extends Channel> {

        protected P protocol;
        protected ChannelFactory<P, C> channelFactory;

        public ChannelBuilder(P protocol, ChannelFactory<P, C> channelFactory) {
            this.protocol = protocol;
            this.channelFactory = channelFactory;
        }

        public <P2 extends Protocol> ChannelBuilder<P2, Channel> protocol(P2 protocol) {
            return new ChannelBuilder<>(protocol, null);
        }

        public <C2 extends Channel> ChannelBuilder<P, C2> channelFactory(ChannelFactory<P, C2> channelFactory) {
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
