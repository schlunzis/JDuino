package org.schlunzis.jduino.connection.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.schlunzis.jduino.CommunicatorMessageListener;
import org.schlunzis.jduino.connection.Configuration;
import org.schlunzis.jduino.connection.Connection;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SerialConnection<M extends Message> implements Connection<M> {

    private static final Logger log = LoggerFactory.getLogger(SerialConnection.class);
    private final List<CommunicatorMessageListener<M>> listeners = new ArrayList<>();
    private SerialConfiguration configuration;
    private SerialPort serialPort;
    private Protocol<M> protocol;
    private boolean connected = false;

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void setProtocol(Protocol<M> protocol) {
        this.protocol = protocol;
    }

    @Override
    public void addListener(CommunicatorMessageListener<M> callback) {
        listeners.add(callback);
    }

    @Override
    public void sendMessage(M message) {
        byte[] encodedMessage = protocol.getMessageEncoder().encode(message);
        serialPort.writeBytes(encodedMessage, encodedMessage.length);
    }

    @Override
    public void open() {
        if (connected) {
            log.warn("Communicator is already connected");
            return;
        }

        serialPort = SerialPort.getCommPort(configuration.portName());

        serialPort.setComPortParameters(
                configuration.baudRate(),
                8,       // Data bits
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING,
                1000,    // Read timeout (ms)
                0
        );

        serialPort.addDataListener(new SerialDataListener<M>(serialPort, protocol.getMessageDecoder(), message -> {
            for (CommunicatorMessageListener<M> listener : listeners) {
                listener.onMessageReceived(message);
            }
        }));

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
    public void setConfiguration(Configuration configuration) {

        if (configuration instanceof SerialConfiguration serialConfig) {
            this.configuration = serialConfig;
            log.info("Setting serial configuration: Port={}, Baudrate={}", serialConfig.portName(), serialConfig.baudRate());
        } else {
            log.warn("Invalid configuration type provided to Serial interface");
        }
    }


}
