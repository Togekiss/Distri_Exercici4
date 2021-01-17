package network.client;

import controller.Node;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class ClientWebSocket {
    private Session session;
    private Node node;

    public ClientWebSocket(Node node){
        this.node = node;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
    }

    @OnMessage
    public void handleMessage(String message){
        System.out.println("The server sent me this: " + message);

    }

    public Session getSession(){
        return session;
    }

    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
