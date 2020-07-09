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

notes for deployment:

- need to combines all javascript files into a single files. See the function `bundleJs()` and `minifyJs()` in `build-and-run-socket-chat.sh`
- first, bundling, we concatenate all the javascript files into 1 single file `app.js` in `dist` directory.
- next, we use sed commands to "transform" and "minify" javascript codes in `app.js`
- this is just a simple progress to illustrate how javascript code is bundled and transformed for deployment, for larger project we should use tool like Webpack.

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

## References

- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers
- https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications
- https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
- https://javascript.info/websocket
- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html

note:
reason when closing (on server-side) java.util.Scanner, web socket on client also close: "the server is capable of exchanging messages with the client endlessly until the socket is closed with its streams."

## Upgrades

There are some idea to upgrade this application:

- [Webpack](https://github.com/webpack/webpack) for client code bundling
- [Tailwind](https://github.com/tailwindcss/tailwindcss) for CSS framework
- Use [Java API for WebSocket](https://docs.oracle.com/javaee/7/tutorial/websocket.htm)
- Use ReactJs or Angular for the client
