/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.websocket;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ChatEventDatabase {

    private final List<ChatEvent> chatEvents;
    
    private final Set<String> currentUsers;
    
    private static final ChatEventDatabase theInstance = new ChatEventDatabase();
    
    private ChatEventDatabase() {
        chatEvents = new ArrayList<>();
        currentUsers = new LinkedHashSet<>();
    }
    
    public static ChatEventDatabase getInstance() {
        return theInstance;
    }

    public void add(ChatEvent chatEvent) {
        chatEvents.add(chatEvent);
        switch (chatEvent.getType()) {
            case "enter":
                currentUsers.add(chatEvent.getUsername());
                break;
            case "leave":
                currentUsers.remove(chatEvent.getUsername());
                break;
        }
    }
    
    public String getCurrentUsers() {
        return currentUsers.toString().replace("[", "").replace("]", "");
    }
    
}
