package org.schlunzis.jduino.channel.serial;

import org.schlunzis.jduino.channel.DeviceConfiguration;

public record SerialDeviceConfiguration(
        SerialDevice device,
        int baudRate
) implements DeviceConfiguration {

    @Override
    public SerialDevice getDevice() {
        return device;
    }
}
