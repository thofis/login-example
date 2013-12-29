/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author tom
 */
public class ChatEvent {
    
    public static final String MESSAGE_JSON = "msg";
    public static final String USERNAME_JSON = "user";
    public static final String TYPE_JSON = "type";
    public static final String CREATED_AT_JSON = "createdAt";
    
    private static final String DATE_FORMAT = "HH:mm:ss";
    
    private String message;
    private String username;
    private String type;
    private String createdAt;

    
    public ChatEvent() {
        
    }
    
    public ChatEvent(String message, String username, String type) {
        this.message = message;
        this.username = username;
        this.type = type;
        this.createdAt = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
        
    @Override
    public String toString() {
        return "ChatEvent{" + "message=" + message + ", username=" + username + ", type=" + type + ", createdAt=" + createdAt + '}';
    }
}
