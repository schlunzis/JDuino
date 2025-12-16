package org.schlunzis.jduino;

import com.fazecast.jSerialComm.SerialPort;
import org.schlunzis.jduino.proto.Message;

public interface Communicator<M extends Message> {

    void open(String portDescriptor, int baudRate);

    void close();

    void sendMessage(M message);

    SerialPort[] getPorts();

    void addMessageListener(CommunicatorMessageListener<M> listener);

    void removeMessageListener(CommunicatorMessageListener<M> listener);
}
