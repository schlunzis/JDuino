package org.schlunzis.jduino.simple;

import org.schlunzis.jduino.CommandType;

public enum SimpleProtocol implements CommandType {
    CMD_ECHO((byte) 0x01),
    CMD_BUTTON((byte) 0x02),
    CMD_LED((byte) 0x03),
    CMD_LCD((byte) 0x04);

    private final byte code;

    SimpleProtocol(byte code) {
        this.code = code;
    }

    @Override
    public byte getCode() {
        return code;
    }
}
