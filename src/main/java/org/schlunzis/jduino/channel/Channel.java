package org.schlunzis.jduino.channel;

import org.schlunzis.jduino.channel.serial.SerialChannel;
import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;
import org.schlunzis.jduino.protocol.tlv.TLV;
import org.schlunzis.jduino.simple.SimpleChannel;

import java.util.List;

public interface Channel<P extends Protocol<P>> {

    static ChannelBuilder<?, ?> builder() {
        return new ChannelBuilder<>(null, null);
    }

    void open(DeviceConfiguration deviceConfiguration);

    void close();

    void sendMessage(Message<P> message);

    List<Device> getDevices();

    void addMessageListener(ChannelMessageListener<P> listener);

    void removeMessageListener(ChannelMessageListener<P> listener);

    boolean isConnected();

    @FunctionalInterface
    interface ChannelFactory<P extends Protocol<P>, C extends Channel<P>> {

        C createChannel(P protocol);

    }

    class ChannelBuilder<P extends Protocol<P>, C extends Channel<P>> {

        private final P protocol;
        private final ChannelFactory<P, C> channelFactory;

        public ChannelBuilder(P protocol, ChannelFactory<P, C> channelFactory) {
            this.protocol = protocol;
            this.channelFactory = channelFactory;
        }

        public <Proto extends Protocol<Proto>> ChannelBuilder<Proto, Channel<Proto>> protocol(Proto protocol) {
            return new ChannelBuilder<>(protocol, null);
        }

        public ChannelBuilder<TLV, Channel<TLV>> tlv() {
            return protocol(new TLV());
        }

        public <Chan extends Channel<P>> ChannelBuilder<P, Chan> channelFactory(ChannelFactory<P, Chan> channelFactory) {
            return new ChannelBuilder<>(protocol, channelFactory);
        }

        public ChannelBuilder<P, SerialChannel<P>> serial() {
            return channelFactory(SerialChannel::new);
        }

        public ChannelBuilder<TLV, SimpleChannel> simple() {
            return tlv().channelFactory(SimpleChannel::new);
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
