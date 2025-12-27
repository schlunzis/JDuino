import org.jspecify.annotations.NullMarked;

@NullMarked
module org.schlunzis.jduino.tlv {
    requires transitive org.schlunzis.jduino.api;

    requires org.jspecify;

    exports org.schlunzis.jduino.protocol.tlv;
}
