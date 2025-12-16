package org.schlunzis.jduino.commuication;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.schlunzis.jduino.CommunicatorMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * DataListener expecting data in the TLV Format
 *
 * @see <a href="https://de.wikipedia.org/wiki/Type-Length-Value">Wikipedia</a>
 */
public class TLVDataListener implements SerialPortDataListener {

    private static final Logger log = LoggerFactory.getLogger(TLVDataListener.class);

    private final ByteArrayOutputStream payload = new ByteArrayOutputStream();
    private final SerialPort serialPort;
    private final List<CommunicatorMessageListener> listeners;
    private int expectedLength = -1;           // -1 = waiting for length
    private byte type = -1;                 // -1 = waiting for type

    public TLVDataListener(SerialPort serialPort, List<CommunicatorMessageListener> listeners) {
        this.serialPort = serialPort;
        this.listeners = listeners;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] buffer = new byte[serialPort.bytesAvailable()];
        serialPort.readBytes(buffer, buffer.length);
        for (byte b : buffer) {
            processByte(b);
        }
    }

    private void processByte(byte b) {
        if (type < 0) {
            // First byte = message type
            type = b;
            log.debug("message type {}", type);
        } else if (expectedLength < 0) {
            // Second byte = message length
            expectedLength = b & 0xFF;  // convert to unsigned
            log.debug("expected length {}", expectedLength);
            payload.reset();
        } else {
            // Subsequent bytes = payload
            payload.write(b);
            if (payload.size() == expectedLength) {
                // Full message received
                String message = payload.toString(StandardCharsets.UTF_8);
                log.debug("Received: {}", message);
                for (CommunicatorMessageListener listener : listeners) {
                    listener.onMessageReceived(type, message);
                }

                // Reset for next message
                reset();
            }
        }
    }

    private void reset() {
        expectedLength = -1;
        type = -1;
        payload.reset();
    }

}
