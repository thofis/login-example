/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.websocket;

import java.io.StringReader;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatEventDecoder implements Decoder.Text<ChatEvent> {

    private static final Logger log = Logger.getLogger(ChatEventDecoder.class.getName());

    @Override
    public ChatEvent decode(String chatEventString) throws DecodeException {
        JsonObject chatEventJSON = Json.createReader(new StringReader(chatEventString)).readObject();
        ChatEvent chatEvent = new ChatEvent(
                chatEventJSON.getString(ChatEvent.MESSAGE_JSON),
                chatEventJSON.getString(ChatEvent.USERNAME_JSON),
                chatEventJSON.getString(ChatEvent.TYPE_JSON)
        );
        log.info("decoded chatEvent from JSON: "+chatEvent.toString());
        return chatEvent;
    }

    @Override
    public boolean willDecode(String chatEventString) {
        try {
            Json.createReader(new StringReader(chatEventString)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
