import org.jspecify.annotations.NullMarked;

@NullMarked
module org.schlunzis.jduino.serial {
    requires transitive org.schlunzis.jduino.api;

    requires org.slf4j;
    requires com.fazecast.jSerialComm;
    requires org.jspecify;

    exports org.schlunzis.jduino.channel.serial;
}
