package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Protocol;

@FunctionalInterface
public interface ChannelFactory<P extends Protocol<P>, C extends Channel<P>> {

    C createChannel(P protocol);

}
