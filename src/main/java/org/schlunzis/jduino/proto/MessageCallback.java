package org.schlunzis.jduino.proto;

public interface MessageCallback<P extends Protocol<P>> {

    void onMessage(Message<P> message);

}
