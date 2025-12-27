import org.jspecify.annotations.NullMarked;

@NullMarked
module org.schlunzis.jduino.simple {
    requires transitive org.schlunzis.jduino.api;
    requires transitive org.schlunzis.jduino.serial;
    requires transitive org.schlunzis.jduino.tlv;

    requires org.jspecify;

    exports org.schlunzis.jduino.simple;
}
