"use strict";

function ChatController($scope) {

    $scope.chatEnabled = false;

    $scope.currentMessage = "";

    $scope.username = "Bob";

    $scope.usersOnline = "";

    $scope.messages = [
    ];

    for (var i = 0; i < 10; i++) {
        $scope.messages.push("");
    }


    $scope.send = function() {
        sendChatEvent("send", $scope.username, $scope.currentMessage);
        $scope.currentMessage = "";
    };

    $scope.enter = function() {
        $scope.chatEnabled = true;
        sendChatEvent("enter", $scope.username);
    };

    $scope.leave = function() {
        $scope.chatEnabled = false;
        $scope.currentMessage = "";
        sendChatEvent("leave", $scope.username);
    };

    var sendChatEvent = function(chatEventType, chatEventUser, chatEventMsg) {
        if (typeof chatEventMsg === 'undefined') {
            chatEventMsg = "";
        }
        var chatEvent = {
            type: chatEventType,
            msg: chatEventMsg,
            user: chatEventUser
        };
        var stringifiedMsg = JSON.stringify(chatEvent);
        console.log("sending: " + stringifiedMsg);
        websocket.send(stringifiedMsg);
    };

    var enqueueMessage = function(chatEvent) {
        var newMessage = "";
        if (chatEvent.type === "send") {
            newMessage = chatEvent.createdAt + " - " + chatEvent.user + ": " + chatEvent.msg;
        } else if (chatEvent.type === "enter") {
            newMessage = chatEvent.createdAt + " - " + chatEvent.user + " betritt den Chat.";
        } else if (chatEvent.type === "leave") {
            newMessage = chatEvent.createdAt + " - " + chatEvent.user + " verlässt den Chat.";
        } else {
            newMessage = chatEvent.createdAt + " - " + "unknown chat event...";
        }
        $scope.$apply(function() {
            $scope.messages.push(newMessage);
            $scope.messages.shift();
        });
    };

    var wsUri = "ws://" + document.location.host + "/login-example/chat";
    var websocket = new WebSocket(wsUri);

    websocket.onopen = function(evt) {
        onOpen(evt);
    };
    websocket.onmessage = function(evt) {
        onMessage(evt);
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };

    function onOpen() {
        console.log("Connected to " + wsUri);
    }

    function onMessage(evt) {
        console.log("onMessage: " + evt.data);
        var chatEvent = JSON.parse(evt.data);
        if (chatEvent.type === "usersChanged") {
            $scope.$apply(function() {
                $scope.usersOnline = chatEvent.msg;
            });
        } else {
            enqueueMessage(chatEvent);
        }
    }

    function onError(evt) {
        console.log("Error: " + evt.data);
    }

}



