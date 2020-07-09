#!/bin/bash

bundleJs() {
    rm -rf socket-chat-client/dist/
    mkdir socket-chat-client/dist/
    (cd socket-chat-client;cat elements/chat-box.js elements/login-form.js scripts/websocket-handler.js > dist/app.js)
}

# this "minify" progress is just a minimal, simple one like remove all empty lines and newlines and spaces (advanced one can "compress" variables and code).
minifyJs() {
    sed -i 's/export//' socket-chat-client/dist/app.js # remove 'export' keyword
    sed -i '/^import/d' socket-chat-client/dist/app.js # remove line started with 'import' keyword
    sed -i '/^$/d' socket-chat-client/dist/app.js # remove empty lines
    sed -i 's/^[ \t]*//' socket-chat-client/dist/app.js # remove leading spaces or tabs
    sed -i ':a;N;$!ba;s/\n/ /g' socket-chat-client/dist/app.js # replace the newline(s) with a spaces
}

compileJava() {
    rm -rf socket-chat-server/build/*
    mkdir socket-chat-server/build/classes
    javac socket-chat-server/src/main/java/utility/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/observer/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/websocket/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/*.java -d socket-chat-server/build/classes/
}

startServer() {
    java -cp socket-chat-server/build/classes/ WebSocketApp
}

run() {
    bundleJs
    minifyJs
    compileJava
    local webPage=$(readlink -f socket-chat-client/index.html)
    echo "You can now access the web application at: ${webPage}"
    startServer
}

run
