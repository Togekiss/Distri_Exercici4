package network.server;


import javax.enterprise.context.ApplicationScoped;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                System.out.println(message);

/*
                try {
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("JavaScript");
                    // read script file
                    engine.eval(Files.newBufferedReader(Paths.get("C:\\Users\\angel\\Desktop\\4t\\Arquitectura Distribuida v2\\test\\test\\src\\main\\java\\web\\test.js"), StandardCharsets.UTF_8));

                    Invocable inv = (Invocable) engine;
                    // call function from script file
                    inv.invokeFunction("printRand", message);
                } catch (ScriptException | IOException e) {
                    e.printStackTrace();
                }
*/

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
