 package com.nackademin.systemintegration.websocketchatdemo.websocket;


import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.nackademin.systemintegration.websocketchatdemo.model.Message;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
    public Session session;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
    private static mqtttest subscriber = new mqtttest("postman.cloudmqtt.com", "18986","wbxcsrlx", "1gbNtqwbGAbj", chatEndpoints);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) 
            throws IOException, EncodeException {
        this.session = session;
        chatEndpoints.add(this);
        subscriber.addEndpoint(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        broadcast(message);
       
        
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        if (chatEndpoints.contains(this)){
            message.setFrom(users.get(session.getId()));
            broadcast(message);
        }
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        subscriber.removeEndpoint(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
        //subscriber.broadcast(message);
        
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    static void broadcast(Message message) 
            throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                        .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
