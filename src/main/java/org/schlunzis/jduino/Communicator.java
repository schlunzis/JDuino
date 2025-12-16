package org.schlunzis.jduino;

import com.fazecast.jSerialComm.SerialPort;

public interface Communicator {

    void open(String portDescriptor, int baudRate);

    void close();

    void sendCommand(CommandType commandType, byte[] payload);

    SerialPort[] getPorts();

    void addMessageListener(CommunicatorMessageListener listener);

    void removeMessageListener(CommunicatorMessageListener listener);
}
