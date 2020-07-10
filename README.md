## Demo

To run the application, execute:

```bash
$ bash build-and-run-socket-chat.sh
```

## For development

### Client setup

Execute browser-sync:

```bash
cd socket-chat-client; browser-sync start --server --files **/*.html,**/*.css,**/*.js,**/*.json
```

### Server setup

Compile and start server:

```bash
rm -rf socket-chat-server/build/classes/*;\
javac socket-chat-server/src/main/java/utility/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/observer/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/websocket/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/*.java -d socket-chat-server/build/classes/;\
java -cp socket-chat-server/build/classes/ WebSocketApp
```

#### Or:

Create an executable jar:

```bash
jar cvfm socket-chat-server/build/socket-chat-server.jar socket-chat-server/manifest -C socket-chat-server/build/classes/ .
```

Execute:

```bash
java -jar socket-chat-server/build/socket-chat-server.jar
```

## References

- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications
- https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
- https://javascript.info/websocket
- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html

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

## Upgrades

There are some idea to upgrade this application:

- [Tailwind](https://github.com/tailwindcss/tailwindcss) for CSS framework
- Use [Java API for WebSocket](https://docs.oracle.com/javaee/7/tutorial/websocket.htm)
- Use ReactJs or Angular for the client
