package org.schlunzis.jduino.protocol;

public interface MessageCallback<P extends Protocol<P>> {

    void onMessage(Message<P> message);

}
