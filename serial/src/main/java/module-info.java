module org.schlunzis.jduino.serial {
    requires org.schlunzis.jduino.api;

    requires org.slf4j;
    requires com.fazecast.jSerialComm;

    exports org.schlunzis.jduino.channel.serial;
}
