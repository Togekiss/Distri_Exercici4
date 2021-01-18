package network.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class ClientWebSocket {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void handleMessage(String message){
        System.out.println("The server sent me this: " + message);

    }

    public Session getSession(){
        return session;
    }
}
