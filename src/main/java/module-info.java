module org.schlunzis.jduino {
    requires com.fazecast.jSerialComm;
    requires org.slf4j;

    exports org.schlunzis.jduino;
    exports org.schlunzis.jduino.proto.tlv;
    exports org.schlunzis.jduino.simple;
    exports org.schlunzis.jduino.proto;
}
