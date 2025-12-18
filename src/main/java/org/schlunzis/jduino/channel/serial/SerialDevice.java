package org.schlunzis.jduino.channel.serial;

import org.schlunzis.jduino.channel.Device;

public record SerialDevice(
        String portDescriptor
) implements Device {

    @Override
    public String getDisplayName() {
        return portDescriptor;
    }

}
