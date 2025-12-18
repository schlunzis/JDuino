module org.schlunzis.jduino {
    requires com.fazecast.jSerialComm;
    requires org.slf4j;

    exports org.schlunzis.jduino;
    exports org.schlunzis.jduino.protocol.tlv;
    exports org.schlunzis.jduino.simple;
    exports org.schlunzis.jduino.protocol;
    exports org.schlunzis.jduino.channel.serial;
    exports org.schlunzis.jduino.channel;
}
