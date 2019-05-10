/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nackademin.systemintegration.websocketchatdemo.websocket;

/**
 *
 * @author gusta
 */
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.nackademin.systemintegration.websocketchatdemo.model.Message;
import java.io.IOException;
import java.util.Set;

import javax.websocket.EncodeException;
public class mqtttest implements MqttCallback{
     private final int qos = 1;
    private String topic = "test";
    private MqttClient client;
    private String clientId;
    private Message message;
    Set<ChatEndpoint> chatEndpoints;
    SQLDao db;
    public mqtttest(String adress, String port, String username, String password, Set<ChatEndpoint> chatEndpoints) {
        String host;
        db = new SQLDao();
        host = "tcp://" + adress + ":" + port;
        this.clientId = "";
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());
        try{
        this.chatEndpoints = chatEndpoints;
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);
        this.message = null;
        this.client.subscribe(this.topic, qos);
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }

    public void addEndpoint(ChatEndpoint enp){
        chatEndpoints.add(enp);
    }
    public void removeEndpoint(ChatEndpoint enp){
        chatEndpoints.remove(enp);
    }
    public void broadcast(Message message) 
            throws IOException, EncodeException 
    {
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
    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException, IOException, EncodeException {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
        
        String jsonstring = new String(message.getPayload());
       // ret.setContent(new String(message.getPayload()));
       // ret.setFrom(topic);
        //broadcast(ret);
        Gson gson = new Gson();
        Message tempdata = gson.fromJson(jsonstring, Message.class);
        broadcast(tempdata);
        //Message result = new Message();
       // System.out.println("test before mysql query update xxxxxxxx");
//       Set<Thread> threads = Thread.getAllStackTraces().keySet();
// 
//        for (Thread t : threads) {
//            String name = t.getName();
//            Thread.State state = t.getState();
//            int priority = t.getPriority();
//            String type = t.isDaemon() ? "Daemon" : "Normal";
//            System.out.printf("%-20s \t %s \t %d \t %s\n", name, state, priority, type);
//        }
        String query = "INSERT INTO DHT11sensor (temperature, humidity, deviceid) VALUES ('"+tempdata.getTemperature()+"', '"+tempdata.getHumidity()+"', '"+tempdata.getDeviceid()+"');";
        Float res = (float) db.executeSQLUpdate(query);
        System.out.println("sql result: " + res);
        //result.setTemperature(res);
       // ret.setContent(result.toString());
        //broadcast(result);
    }
    public void setMsgNull(){
        this.message = null;
    }
    
    public boolean checkMsg(){
        if(this.message != null){
            return true;
        }else{
            return false;
        }
    }
/*
    public static void main(String[] args) throws MqttException, URISyntaxException {
        mqtttest s = new mqtttest("m24.cloudmqtt.com", "12530","ofqhaueq", "c6lwyXwW_8AQ");
        s.sendMessage("Hello");
        s.sendMessage("Hello 2");
    }
*/
}
