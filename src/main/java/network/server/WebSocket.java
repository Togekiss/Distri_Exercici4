package network.server;

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
    private Session session;

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Session opened in WebSocket ");
        this.session = session;
        sessions.add(session);
    }

    @OnMessage
    public void handleMessage(String message) {
        if (session.isOpen() && session != null){
            try {
                System.out.println("Received this message in the server: " + message);

                //TODO edit html??
                session.getBasicRemote().sendText("This is a totally unnecessary answer from the server.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Session is not opened or null");
        }
    }



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
}
