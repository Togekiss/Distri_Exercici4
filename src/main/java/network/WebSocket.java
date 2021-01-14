package network;

import controller.Node;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@ServerEndpoint("/status")
public class WebSocket {
    private Set<Session> sessions = new HashSet<>();
    private Node node;

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Session opened for node " + Node.name);
        sessions.add(session);
    }
/*
    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("new message: " + message);
        try {
            for (int c = 0; c < 100; c++) {
                for (Session s : sessions) {
                    s.getBasicRemote().sendText("{\"value\" : \"" + (c + 1) + "\"}");
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
 */


    @OnClose
    public void close(Session session) {
        System.out.println("Session closed ==>");
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @OnMessage
    public void getMessage(String message) {
        System.out.println("Got message: " + message);
        if (message.equals("C1")){
            new Node(message, 44449, 0, null);
        }else if (message.equals("C2")){
            new Node(message, 44450, 0, null);
        }

    }

    public void setMessage(String message) {
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
