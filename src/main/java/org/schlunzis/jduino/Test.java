package org.schlunzis.jduino;

import org.schlunzis.jduino.proto.tlv.TLVMessage;

public class Test {

    static void main() {
        Communicator<TLVMessage> com = CommunicatorBuilder.builder()
                                                          .withCommunicator(SerialCommunicator.class)
                                                          .withMessage(TLVMessage.class)
                                                          .build();
        com.close();
    }

}
