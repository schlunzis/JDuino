package org.schlunzis.jduino.connection;

import org.schlunzis.jduino.proto.Message;

public interface MessageCallback<M extends Message> {

    void onMessage(M message);

}
