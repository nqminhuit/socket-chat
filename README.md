client: execute browser-sync:

```bash
$ cd socket-chat-client; browser-sync start --server --files **/*.html,**/*.css,**/*.js,**/*.json
```

Spin up the server:
precondition:

```bash
$ mkdir -p build/classes
```

compile and start server:

```bash
$ rm -rf socket-chat-server/build/classes/*; javac socket-chat-server/src/main/java/WebSocket.java -d socket-chat-server/build/classes/; java -cp socket-chat-server/build/classes/ WebSocket
```

references:

- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications
- https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
- https://javascript.info/websocket
