package org.schlunzis.jduino.connection.serial;

import org.schlunzis.jduino.connection.Configuration;

public record SerialConfiguration(String portName, int baudRate) implements Configuration {

}
