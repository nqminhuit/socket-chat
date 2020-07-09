## Client setup

for development, execute browser-sync:

```bash
cd socket-chat-client; browser-sync start --server --files **/*.html,**/*.css,**/*.js,**/*.json
```

for deployment, need to combines all javascript files into a single files:

```bash
rm -rf socket-chat-client/dist/;\
mkdir socket-chat-client/dist/;\
(cd socket-chat-client;cat elements/chat-box.js elements/login-form.js scripts/websocket-handler.js > dist/app.js);\
sed -i 's/export//g' socket-chat-client/dist/app.js;\
sed -i '/^import/d' socket-chat-client/dist/app.js;\
sed -i 's/^[ \t]*//' socket-chat-client/dist/app.js;\
sed -i '/^$/d' socket-chat-client/dist/app.js;\
sed -i ':a;N;$!ba;s/\n/ /g' socket-chat-client/dist/app.js;
```

notes:

- first, bundling, we concatenate all the javascript files into 1 single file `app.js` in `dist` directory.
- next, we use sed commands to "transform" and "minify" javascript codes in `app.js`
  - the first 2 sed commands is to "transform" mudular copied code into 1 single file.
  - the "transform" progress is a simple one which remove "import" and "export" keywords.
  - the rest sed commands is to "minify" javascript code.
  - this "minify" progress is just a minimal, simple one like remove all empty lines and newlines (advanced one can "compress" variables and code).
- this is just a simple progress to illustrate how javascript code is bundled and transformed for deployment, for larger project we should use tool like Webpack.

## Server setup

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
