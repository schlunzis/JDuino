import org.jspecify.annotations.NullMarked;

@NullMarked
module org.schlunzis.jduino.api {
    requires org.jspecify;

    exports org.schlunzis.jduino.protocol;
    exports org.schlunzis.jduino.channel;
}
