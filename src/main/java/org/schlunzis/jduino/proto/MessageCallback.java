package org.schlunzis.jduino.proto;

public interface MessageCallback<P extends Protocol> {

    void onMessage(Message<P> message);

}
