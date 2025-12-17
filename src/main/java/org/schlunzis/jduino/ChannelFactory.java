package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Protocol;

@FunctionalInterface
public interface ChannelFactory<P extends Protocol<P>> {

    Channel<P> createChannel(P protocol);

}
