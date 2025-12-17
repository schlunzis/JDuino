package org.schlunzis.jduino;

import com.fazecast.jSerialComm.SerialPort;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.Protocol;

public interface Channel<P extends Protocol<P>> {

    void open(String portDescriptor, int baudRate);

    void close();

    void sendMessage(Message<P> message);

    SerialPort[] getPorts();

    void addMessageListener(ChannelMessageListener<P> listener);

    void removeMessageListener(ChannelMessageListener<P> listener);
}
