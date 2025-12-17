package org.schlunzis.jduino.connection;

import org.schlunzis.jduino.CommunicatorMessageListener;
import org.schlunzis.jduino.connection.serial.SerialConfiguration;
import org.schlunzis.jduino.connection.serial.SerialConnection;
import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.Protocol;
import org.schlunzis.jduino.proto.tlv.TLVMessage;
import org.schlunzis.jduino.proto.tlv.TLVProtocol;

public interface Connection<M extends Message> {

    static <M extends Message> ConnectionBuilder<M> builder() {
        return new ConnectionBuilder<>();
    }

    void setProtocol(Protocol<M> protocol);

    void addListener(CommunicatorMessageListener<M> callback);

    void sendMessage(M message);

    void open();

    void close();

    void setConfiguration(Configuration configuration);

    class ConnectionBuilder<M extends Message> {
        private Connection<M> connection;
        private Protocol<M> protocol;
        private Configuration configuration;

        public ConnectionBuilder<M> withConnection(Connection<M> connection) {
            this.connection = connection;
            return this;
        }

        public ConnectionBuilder<M> withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ConnectionBuilder<M> withProtocol(Protocol<M> protocol) {
            this.protocol = protocol;
            return this;
        }

        public Connection<M> build() {
            if (connection == null) {
                throw new IllegalStateException("Connection must be provided");
            }
            if (protocol == null) {
                throw new IllegalStateException("Protocol must be provided");
            }
            if (configuration == null) {
                throw new IllegalStateException("Configuration must be provided");
            }
            connection.setConfiguration(configuration);
            connection.setProtocol(protocol);
            return connection;
        }

        public ConnectionBuilder<M> serial() {
            this.connection = new SerialConnection<>();
            this.configuration = new SerialConfiguration("/dev/ttyACM0", 250000);
            return this;
        }

        @SuppressWarnings("unchecked")
        public ConnectionBuilder<TLVMessage> tlv() {
            ConnectionBuilder<TLVMessage> b = new ConnectionBuilder<>();
            b.protocol = new TLVProtocol();
            if (this.connection != null) {
                b.connection = (Connection<TLVMessage>) this.connection;
                b.configuration = this.configuration;
            }
            return b;
        }
    }

}
