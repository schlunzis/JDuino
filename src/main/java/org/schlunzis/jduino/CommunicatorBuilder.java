package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.Message;

public class CommunicatorBuilder<C extends Communicator<M>, M extends Message> {

    private Class<C> communicatorClass;
    private Class<M> messageClass;

    private CommunicatorBuilder(Class<C> communicatorClass, Class<M> messageClass) {
        this.communicatorClass = communicatorClass;
        this.messageClass = messageClass;
    }

    public static CommunicatorBuilder<Communicator<Message>, Message> builder() {
        return new CommunicatorBuilder<>(null, null);
    }

    public <Com extends Communicator<M>> CommunicatorBuilder<Com, M> withCommunicator(Class<Com> communicator) {
        return new CommunicatorBuilder<>(communicator, this.messageClass);
    }

    @SuppressWarnings("unchecked")
    public <Msg extends Message> CommunicatorBuilder<Communicator<Msg>, Msg> withMessage(Class<Msg> message) {
        return new CommunicatorBuilder<>((Class<Communicator<Msg>>) (this.communicatorClass), message);
    }

    public C build() {
        return null;
    }


}
