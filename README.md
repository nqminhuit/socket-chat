## Demo

- Download and untar Kafka
- Start the `zookeeper`:
```bash
./zookeeper-server-start.sh ../config/zookeeper.properties
```
- Start Kafka server:
```bash
./kafka-server-start.sh ../config/server.properties
```
- Create Kafka topic:
```bash
kafka-topics --create --topic kafka-chat --zookeeper localhost:2181 --replication-factor 1 --partitions 1
```
- Build and run the application:
```bash
./gradlew run
```

## For development

### Client setup

Execute webpack-dev-server

```bash
cd socket-chat-client
node_modules/webpack-dev-server/bin/webpack-dev-server.js --content-base dist/ --inline --hot --watch-poll
```

compile jsx:

```bash
cd socket-chat-client
node_modules/@babel/cli/bin/babel.js src/jsx/chat-box.jsx -o dist/chat-box.js --presets @babel/preset-react
```

### Server setup

Compile:

```bash
./gradlew clean assemble
```

Start server:

```bash
java -jar socket-chat-server/build/libs/socket-chat-server.jar
```

### Sending message
```bash
curl -X POST http://localhost:8080/api/send -H 'Content-Type:application/json' -d 'Hello world!'
```

### Checking message in the topic
```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic socket-chat
```

## References

- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications
- https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
- https://javascript.info/websocket
- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
- https://dev.to/subhransu/realtime-chat-app-using-kafka-springboot-reactjs-and-websockets-lc

notes:

- Reason when closing (on server-side) java.util.Scanner, web socket on client also close: "the server is capable of exchanging messages with the client endlessly until the socket is closed with its streams."
- When you edit just 1 java file and want to update the whole jar file, follow this shortcut:

  - compile the edited java file
  - update the compiled java file to the jar file
  - example:

    ```bash
    $ javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/websocket/WebSocket.java  -d socket-chat-server/build/classes/
    $ jar uf socket-chat-server/build/socket-chat-server.jar -C socket-chat-server/build/classes/ websocket/WebSocket.class
    ```
