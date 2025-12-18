package org.schlunzis.jduino.channel.serial;

import org.schlunzis.jduino.channel.Device;

public record SerialDevice(
        String portName,
        String portPath
) implements Device {

    @Override
    public String getDisplayName() {
        return portName;
    }

}
