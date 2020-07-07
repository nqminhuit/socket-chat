client: execute browser-sync:

```bash
cd socket-chat-client; browser-sync start --server --files **/*.html,**/*.css,**/*.js,**/*.json
```

Spin up the server:

precondition:

```bash
$ mkdir -p build/classes
```

compile and start server:

```bash
rm -rf socket-chat-server/build/classes/*;\
javac socket-chat-server/src/main/java/utility/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/observer/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/websocket/*.java -d socket-chat-server/build/classes/;\
javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/*.java -d socket-chat-server/build/classes/;\
java -cp socket-chat-server/build/classes/ WebSocketApp
```

references:

- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications
- https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
- https://javascript.info/websocket
- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html

note:
reason when closing (on server-side) java.util.Scanner, web socket on client also close: "the server is capable of exchanging messages with the client endlessly until the socket is closed with its streams."
