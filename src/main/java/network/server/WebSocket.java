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
                System.out.println("Sendin message from server: " + message);
                session.getBasicRemote().sendText("Message sent from server: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Session is not opened or null");
        }

        /*
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

         */
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
