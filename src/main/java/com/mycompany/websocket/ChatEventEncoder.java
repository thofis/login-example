/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.websocket;

import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatEventEncoder implements Encoder.Text<ChatEvent> {

    private static final Logger log = Logger.getLogger(ChatEventEncoder.class.getName());
    
    @Override
    public String encode(ChatEvent chatEvent) throws EncodeException {
        JsonObject chatEventJSON = Json.createObjectBuilder()
                .add(ChatEvent.MESSAGE_JSON, chatEvent.getMessage())
                .add(ChatEvent.USERNAME_JSON, chatEvent.getUsername())
                .add(ChatEvent.TYPE_JSON, chatEvent.getType())
                .add(ChatEvent.CREATED_AT_JSON, chatEvent.getCreatedAt()).build();
        log.info("encoded chatEvent to JSON: "+chatEventJSON.toString());
        return chatEventJSON.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
