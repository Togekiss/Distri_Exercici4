package network.server;


import controller.Node;
import network.NodeData;
import org.springframework.stereotype.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@Repository
@WebServlet("/servlet")
@ServerEndpoint("/status")
public class WebSocket extends HttpServlet {
    private Set<Session> sessions = new HashSet<>();
    private Session session;
    private NodeData nodeData = NodeData.getInstance();

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Session opened in WebSocket");
        this.session = session;
        sessions.add(session);
    }

    @OnMessage
    public void handleMessage(String message) {
        if (session.isOpen() && session != null){
            System.out.println(message); // data to show in html
            //System.out.println("NodeData status size is " + NodeData.getInstance().status.size());
            nodeData.status.add(message);
            if (message.startsWith("A1")){
                NodeData.getInstance().statusA1 = message;
            }else if (message.startsWith("A2")){
                NodeData.getInstance().statusA2 = message;
            }else if (message.startsWith("A3")){
                NodeData.getInstance().statusA3 = message;
            }else if (message.startsWith("B1")){
                NodeData.getInstance().statusB1 = message;
            }else if (message.startsWith("B2")){
                NodeData.getInstance().statusB2 = message;
            }else if (message.startsWith("C1")){
                NodeData.getInstance().statusC1 = message;
            }else if (message.startsWith("C2")){
                NodeData.getInstance().statusC2 = message;
            }
        }else{
            System.out.println("Session is not opened or null");
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Session closed");
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
/*
        PrintWriter writer = response.getWriter();
        System.out.println("Post done");
        writer.println("<p>Server time is: <%= new Date() %><%response.setIntHeader(\"Refresh\", 1);%></p>");
        int index = 0;
        while (true){
            System.out.println("status size: " + status.size() + " index: " + index);
            if (status.size() > index){
                for (int i = index; i < (status.size() - index); i++){
                    System.out.println("Printing: " + status.get(i));
                    writer.println("<p>" + status.get(i) + "</p>");
                }
            }
            writer.close();
        }
*/

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            //System.out.println("Setting status of size: " + NodeData.getInstance().status.size());
            request.setAttribute("status", NodeData.getInstance().status);
            request.setAttribute("statusA1", NodeData.getInstance().statusA1);
            request.setAttribute("statusA2", NodeData.getInstance().statusA2);
            request.setAttribute("statusA3", NodeData.getInstance().statusA3);
            request.setAttribute("statusB1", NodeData.getInstance().statusB1);
            request.setAttribute("statusB2", NodeData.getInstance().statusB2);
            request.setAttribute("statusC1", NodeData.getInstance().statusC1);
            request.setAttribute("statusC2", NodeData.getInstance().statusC2);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
