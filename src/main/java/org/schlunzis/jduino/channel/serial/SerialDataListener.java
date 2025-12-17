package org.schlunzis.jduino.channel.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.MessageCallback;
import org.schlunzis.jduino.proto.MessageDecoder;
import org.schlunzis.jduino.proto.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataListener expecting data in the TLV Format
 *
 * @see <a href="https://de.wikipedia.org/wiki/Type-Length-Value">Wikipedia</a>
 */
class SerialDataListener<P extends Protocol<P>> implements SerialPortDataListener {

    private static final Logger log = LoggerFactory.getLogger(SerialDataListener.class);

    private final SerialPort serialPort;
    private final MessageCallback<P> callback;
    private final MessageDecoder<P> messageDecoder;

    public SerialDataListener(SerialPort serialPort, MessageCallback<P> callback, MessageDecoder<P> messageDecoder) {
        this.serialPort = serialPort;
        this.callback = callback;
        this.messageDecoder = messageDecoder;
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
                Message<P> message = messageDecoder.getDecodedMessage();
                callback.onMessage(message);
            }
        }
    }

}
