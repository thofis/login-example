"use strict";

angular.module('websockets-chat', []).
        controller('ChatController', function($scope) {
            // flag for enabling/disabling the current chat
            $scope.chatEnabled = false;

            // helper variable used by the focusMe directive
            $scope.focusMessage = false;

            // currently edited message
            $scope.currentMessage = "";

            // username of the chatter
            $scope.username = "Bob";

            // string for displaying the currently active users
            $scope.usersOnline = "";

            // array for displaying messages
            $scope.messages = [
            ];

            // initialize message array with 10 empty messages
            for (var i = 0; i < 10; i++) {
                $scope.messages.push("");
            }

            // current user sends a new message 
            $scope.send = function() {
                sendChatEvent("send", $scope.username, $scope.currentMessage);
                $scope.focusMessage = true;
                $scope.currentMessage = "";
            };

            // current user enters the chat
            $scope.enter = function() {
                $scope.chatEnabled = true;
                $scope.focusMessage = true;
                sendChatEvent("enter", $scope.username);
            };
            
            // current user leaves the chat
            $scope.leave = function() {
                $scope.chatEnabled = false;
                $scope.currentMessage = "";
                sendChatEvent("leave", $scope.username);
            };

            // helper function for sending chat events over websocket
            var sendChatEvent = function(chatEventType, chatEventUser, chatEventMsg) {
                if (typeof chatEventMsg === 'undefined') {
                    // set the msg to empty string, if no msg gets passed
                    chatEventMsg = "";
                }
                // create a new chat event object
                var chatEvent = {
                    type: chatEventType,
                    msg: chatEventMsg,
                    user: chatEventUser
                };
                // convert object to string
                var stringifiedMsg = JSON.stringify(chatEvent);
                console.log("sending: " + stringifiedMsg);
                // send via websocket
                websocket.send(stringifiedMsg);
            };

            // enqueues messages retrieved over websocket in the message array
            var enqueueMessage = function(chatEvent) {
                // convert chatevent to message string dependent on its type
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
                // add new message and remove oldest (queue)
                $scope.$apply(function() {
                    $scope.messages.push(newMessage);
                    $scope.messages.shift();
                });
            };

            // setup the websocket connection
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

            // handles incoming websocket messages
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

        }).
        // directive for adding a listener for key-enter events
        directive('customEnter', function() {
            return function(scope, element, attrs) {
                element.bind("keydown keypress", function(event) {
                    if (event.which === 13) {
                        scope.$apply(function() {
                            scope.$eval(attrs.customEnter);
                        });

                        event.preventDefault();
                    }
                });
            };
        }).
        // directive for focusing on an input element
        directive('focusMe', function($timeout) {
            return {
                link: function(scope, element, attrs) {
                    scope.$watch(attrs.focusMe, function(value) {
                        if (value === true) {
                            console.log('value=', value);
                            $timeout(function() {
                                element[0].focus();
                                scope[attrs.focusMe] = false;
                            });
                        }
                    });
                }
            };
        });


