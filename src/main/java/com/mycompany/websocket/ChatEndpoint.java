package com.mycompany.websocket;

import java.io.IOException;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/chat",
        encoders = {ChatEventEncoder.class},
        decoders = {ChatEventDecoder.class}
)
public class ChatEndpoint {

    private static final Logger log = Logger.getLogger(ChatEndpoint.class.getName());

    @OnMessage
    public void message(ChatEvent chatEvent, Session client) throws IOException, EncodeException {
        log.info("in onMessage()");
        
        ChatEventDatabase.getInstance().add(chatEvent);
        
        ChatEvent usersChangedEvent = null;
        if (chatEvent.getType().equals("enter") || chatEvent.getType().equals("leave")) {
            String currentUsers = ChatEventDatabase.getInstance().getCurrentUsers();
            usersChangedEvent = new ChatEvent(currentUsers, "" ,"usersChanged");
        }
        
        for (Session peer : client.getOpenSessions()) {
            RemoteEndpoint.Basic basicRemote = peer.getBasicRemote(); 
            basicRemote.sendObject(chatEvent);
            if (usersChangedEvent != null) {
                basicRemote.sendObject(usersChangedEvent);
            }
        }
    }

    @OnOpen
    public void onOpen() {
        log.info("in onOpen()");
    }

    @OnClose
    public void onClose() {
        log.info("in onClose()");
    }
}
