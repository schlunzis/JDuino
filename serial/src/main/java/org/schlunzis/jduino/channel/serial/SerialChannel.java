package org.schlunzis.jduino.channel.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.jspecify.annotations.Nullable;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;
import org.schlunzis.jduino.channel.DeviceConfiguration;
import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerialChannel implements Channel {

    private static final Logger log = LoggerFactory.getLogger(SerialChannel.class);
    private final List<ChannelMessageListener> listeners;
    private final Protocol protocol;
    @Nullable
    private SerialPort serialPort;
    private boolean connected = false;

    public SerialChannel(Protocol protocol) {
        this.protocol = protocol;
        listeners = new ArrayList<>();
    }

    public static <P extends Protocol> Builder<P> builder() {
        return new Builder<>();
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public List<SerialDevice> getDevices() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(port -> new SerialDevice(port.getDescriptivePortName(), port.getSystemPortPath()))
                .toList();
    }

    @Override
    public void addMessageListener(ChannelMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeMessageListener(ChannelMessageListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void open(DeviceConfiguration deviceConfiguration) {
        if (!(deviceConfiguration instanceof SerialDeviceConfiguration serialConfig)) {
            log.error("Invalid device configuration type");
            return;
        }

        if (connected) {
            log.warn("Channel is already connected");
            return;
        }

        serialPort = SerialPort.getCommPort(serialConfig.getDevice().portPath());

        serialPort.setComPortParameters(
                serialConfig.baudRate(),
                8,       // Data bits
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING,
                1000,    // Read timeout (ms)
                0
        );
        serialPort.addDataListener(new SerialDataListener(serialPort, (Message message) ->
                        listeners.forEach(listener ->
                                listener.onMessageReceived(message)),
                        protocol.getMessageDecoder()
                )
        );

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
            log.info("Channel is not connected");
            return;
        }
        if (serialPort != null && serialPort.closePort()) {
            connected = false;
            log.info("Port closed successfully");
        } else {
            log.error("Failed to close port");
        }
    }

    @Override
    public void sendMessage(Message message) {
        if (!connected || serialPort == null) {
            log.error("Channel is not connected");
            return;
        }

        byte[] encodedMessage = protocol.getMessageEncoder().encode(message);

        serialPort.writeBytes(encodedMessage, encodedMessage.length);
    }

    public static class Builder<P extends Protocol> extends ChannelBuilder<P, SerialChannel> {
        private Builder() {
            super(null, null);
        }

        @Override
        public SerialChannel build() {
            this.channelFactory = SerialChannel::new;
            return super.build();
        }
    }

}
