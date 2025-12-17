package org.schlunzis.jduino.connection.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.schlunzis.jduino.connection.MessageCallback;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.MessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataListener expecting data in the TLV Format
 *
 * @see <a href="https://de.wikipedia.org/wiki/Type-Length-Value">Wikipedia</a>
 */
public class SerialDataListener<M extends Message> implements SerialPortDataListener {

    private static final Logger log = LoggerFactory.getLogger(SerialDataListener.class);

    private final SerialPort serialPort;
    private final MessageCallback<M> callback;
    private final MessageDecoder<M> messageDecoder;

    public SerialDataListener(SerialPort serialPort, MessageDecoder<M> messageDecoder, MessageCallback<M> callback) {
        this.serialPort = serialPort;
        this.messageDecoder = messageDecoder;
        this.callback = callback;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        log.debug("Serial event: {}", event.getEventType());
        byte[] buffer = new byte[serialPort.bytesAvailable()];
        serialPort.readBytes(buffer, buffer.length);
        for (byte b : buffer) {
            log.debug("Received byte: 0x{}", b);
            messageDecoder.pushNextByte(b);
            if (messageDecoder.isMessageComplete()) {
                M message = messageDecoder.getDecodedMessage();
                callback.onMessage(message);
            }
        }
    }

}
