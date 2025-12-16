package org.schlunzis.jduino.proto.tlv;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.schlunzis.jduino.proto.MessageCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataListener expecting data in the TLV Format
 *
 * @see <a href="https://de.wikipedia.org/wiki/Type-Length-Value">Wikipedia</a>
 */
public class TLVDataListener implements SerialPortDataListener {

    private static final Logger log = LoggerFactory.getLogger(TLVDataListener.class);

    private final SerialPort serialPort;
    private final MessageCallback<TLVMessage> callback;
    private final TLVMessageDecoder messageDecoder = new TLVMessageDecoder();

    public TLVDataListener(SerialPort serialPort, MessageCallback<TLVMessage> callback) {
        this.serialPort = serialPort;
        this.callback = callback;
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
            messageDecoder.pushNextByte(b);
            if (messageDecoder.isMessageComplete()) {
                TLVMessage message = messageDecoder.getDecodedMessage();
                callback.onMessage(message);
            }
        }
    }

}
