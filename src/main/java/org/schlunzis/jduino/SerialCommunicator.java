package org.schlunzis.jduino;

import com.fazecast.jSerialComm.SerialPort;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SerialCommunicator<M extends Message> implements Communicator<M> {

    private static final Logger log = LoggerFactory.getLogger(SerialCommunicator.class);
    private final List<CommunicatorMessageListener<M>> listeners;
    private final MessageEncoder<M> messageEncoder;
    private SerialPort serialPort;
    private boolean connected = false;

    public SerialCommunicator(MessageEncoder<M> messageEncoder) {
        this.messageEncoder = messageEncoder;
        listeners = new ArrayList<>();
    }

    public boolean isConnected() {
        return connected;
    }

    public SerialPort[] getPorts() {
        return SerialPort.getCommPorts();
    }

    @Override
    public void addMessageListener(CommunicatorMessageListener<M> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeMessageListener(CommunicatorMessageListener<M> listener) {
        listeners.remove(listener);
    }

    @Override
    public void open(String portDescriptor, int baudRate) {
        if (connected) {
            log.warn("Communicator is already connected");
            return;
        }

        serialPort = SerialPort.getCommPort(portDescriptor);

        serialPort.setComPortParameters(
                baudRate,
                8,       // Data bits
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING,
                1000,    // Read timeout (ms)
                0
        );
        // serialPort.addDataListener(new TLVDataListener(serialPort, message ->
        //                 listeners.forEach(listener ->
        //                         listener.onMessageReceived(message))
        //         )
        //   );

        if (!serialPort.openPort())
            log.error("Failed to open port");
        else {
            connected = true;
            log.info("Port opened successfully");
        }
    }

    @Override
    public void close() {
        if (!connected) {
            log.info("Communicator is not connected");
            return;
        }
        if (serialPort.closePort()) {
            connected = false;
            log.info("Port closed successfully");
        } else {
            log.error("Failed to close port");
        }
    }

    @Override
    public void sendMessage(M message) {
        byte[] encodedMessage = messageEncoder.encode(message);

        serialPort.writeBytes(encodedMessage, encodedMessage.length);
    }

}
