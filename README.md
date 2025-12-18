# JDuino

JDuino is a Java library that allows you to easily interface with Arduino boards. It provides a simple and intuitive API
for communicating with Arduino over serial connections, making it easy to control and monitor your Arduino projects from
Java applications.

## Usage

### Getting Started

To get started with a proof of concept, you can use the `SimpleChannel` class, which combines the serial channel and TLV
protocol into a single class for easy use.

This assumes that you have an Arduino running that can handle TLV messages. An example Arduino sketch can be found
[here](https://github.com/JayPi4c/SerialController).

First, add JDuino to your project using JitPack by adding the following to your `pom.xml`:

<!-- @formatter:off -->
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
<!-- @formatter:on -->

Then, add the dependencies:

<!-- @formatter:off -->
```xml
<dependency>
    <groupId>com.github.schlunzis.jduino</groupId>
    <artifactId>simple</artifactId>
    <version>COMMIT_HASH_OR_TAG</version>
</dependency>
```
<!-- @formatter:on -->

Finally, you can use the following code to communicate with your Arduino:

```java
void main() {
    SimpleChannel channel = SimpleChannel.create();
    List<SerialDevice> devices = channel.getDevices();
    SerialDevice device = devices.getFirst(); // Select the desired device
    channel.open(new SerialDeviceConfiguration(device, 250000));

    // Listen for incoming TLV messages
    channel.addMessageListener(message -> System.out.println("Received: " + new String(message.getPayload())));

    // Send a TLV message to the Arduino
    channel.sendEchoCommand("Hello Arduino"); // Arduino will echo back the message
    channel.sendLEDCommand(13, true); // Turn on LED on pin 13
}
```

This should cause the Arduino to respond to the echo command and turn on the LED on pin 13, which should be the
built-in LED on most Arduino boards.

### General Usage

If you want to use JDuino in your project, you need two components: a protocol and a channel.
The `serial` channel for serial communication and the `tlv` protocol for encoding and decoding data in TLV
(Type-Length-Value) format are currently available.

Since JDuino is currently only available via JitPack, add the following to your `pom.xml`:

<!-- @formatter:off -->
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
<!-- @formatter:on -->

Then, add the dependencies:

<!-- @formatter:off -->
```xml
<dependency>
    <groupId>com.github.schlunzis.jduino</groupId>
    <artifactId>CHANNEL_IMPLEMENTATION</artifactId>
    <version>COMMIT_HASH_OR_TAG</version>
</dependency>
<dependency>
    <groupId>com.github.schlunzis.jduino</groupId>
    <artifactId>PROTOCOL_IMPLEMENTATION</artifactId>
    <version>COMMIT_HASH_OR_TAG</version>
</dependency>
```
<!-- @formatter:on -->

Here is a general example of how to use JDuino to communicate with an Arduino board using the TLV protocol over a serial
connection:

```java
void main() {
    SerialChannel<TLV> channel = Channel.builder()
            .protocol(new TLV())
            .channelBuilder(SerialChannel::new)
            .build();
    List<SerialDevice> devices = channel.getDevices();
    SerialDevice device = devices.getFirst(); // Select the desired device
    channel.open(new SerialDeviceConfiguration(device, 250000));

    // Listen for incoming TLV messages
    channel.addMessageListener(message -> System.out.println("Received: " + new String(message.getPayload())));

    // Send a TLV message to the Arduino
    channel.sendMessage(new TLVMessage(0x01, "Hello Arduino".getBytes()));
}
```

### Protocols

JDuino uses protocols to define how data is encoded and decoded during communication. The `TLV` protocol is provided
out of the box, but you can implement your own protocols by implementing the `Protocol` interface.

### Channels

JDuino can support different types of channels for communication. The most common channel is the `SerialChannel`, which
allows communication over serial ports. Other channel types can be implemented as needed by implementing the `Channel`
interface.

To build a channel, you can use the `Channel.builder()` method, providing the desired protocol and a `ChannelFactory`.

## Build

To build the project, make sure you have Java 25 or higher installed.
You can build the project using Maven:

```bash
./mvnw clean install
```
