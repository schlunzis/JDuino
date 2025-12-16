package org.schlunzis.jduino.proto;

public interface MessageCallback<M extends Message> {

    void onMessage(M message);

}
