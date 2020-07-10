#!/bin/bash

buildClient() {
    cd socket-chat-client/
    rm -rf socket-chat-client/dist/
    npm run build
}

compileJava() {
    rm -rf socket-chat-server/build/*
    mkdir socket-chat-server/build/classes
    javac socket-chat-server/src/main/java/utility/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/observer/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/websocket/*.java -d socket-chat-server/build/classes/
    javac -cp socket-chat-server/build/classes/ socket-chat-server/src/main/java/*.java -d socket-chat-server/build/classes/
}

bundleJar() {
    jar cfm socket-chat-server/build/socket-chat-server.jar socket-chat-server/manifest -C socket-chat-server/build/classes/ .
}

startServer() {
    # java -cp socket-chat-server/build/classes/ WebSocketApp
    java -jar socket-chat-server/build/socket-chat-server.jar
}

run() {
    (buildClient)
    compileJava
    bundleJar
    local webPage=$(readlink -f socket-chat-client/index.html)
    echo "You can now access the web application at: ${webPage}"
    startServer
}

run
