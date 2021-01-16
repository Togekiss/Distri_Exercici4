package controller;

import network.client.ContainerTest;
import network.client.incoming.NodeSocket;
import network.client.outgoing.MySocket;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.xml.ws.WebServiceClient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Node {
    public static String name;
    private final ArrayList<String> opsA;
    private final ArrayList<String> opsB;
    private final ArrayList<String> opsC;
    private final ArrayList<MySocket> socketListA;
    private final ArrayList<MySocket> socketListB;
    private final ArrayList<MySocket> socketListC;
    private final String[] lastOperations;
    private int[] status;
    private Session session ;
    private ContainerTest containerTest;
    private WebSocketContainer webSocketContainer;
    private ClientManager clientManager;


    public Node(String name, int myPort, int numPorts, String[] arguments){
        Node.name = name;
        opsB = new ArrayList<>();
        opsA = new ArrayList<>();
        opsC = new ArrayList<>();
        socketListA = new ArrayList<>();
        socketListB = new ArrayList<>();
        socketListC = new ArrayList<>();
        lastOperations = new String[10];
        status = new int[100];
        clientManager = ClientManager.createClient();
        //this.webSocketContainer = ContainerProvider.getWebSocketContainer();
        containerTest = new ContainerTest();
        int[] ports = new int[numPorts];

        for (int i = 0; i < numPorts; i++){
            ports[i] = Integer.parseInt(arguments[i + 3]);
        }
        //readOps();
        System.out.println("Sending hello to server");
        //sendToServer();
        sendToServer2();
        sendToServer3();
        System.out.println("Waiting...");

        setSockets(ports, myPort);
        System.out.println("Socket list A: " + socketListA.size());
        System.out.println("Socket list B: " + socketListB.size());
        System.out.println("Socket list C: " + socketListC.size());
        if (name.equals("Client")){
            //readOps();

            startWorking();
        }else if (name.equals("B2")){
            new TimerThread(this).start();
        }
    }

    public void sendToServer(){
        try {
            Session session = webSocketContainer.connectToServer(containerTest, URI.create("ws://localhost:8080/test_war_exploded/status"));
            containerTest.sendMessage("Hello from client");
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }
    private void sendToServer2(){
        ClientManager clientManager = ClientManager.createClient();
        jakarta.websocket.Session session = null;
        try {
            session = clientManager.connectToServer(containerTest, URI.create("ws://localhost:8080/test_war_exploded/status"));
            session.getBasicRemote().sendText("Hello from client 2");
        } catch (jakarta.websocket.DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }
    private void sendToServer3(){
        ClientManager clientManager = ClientManager.createClient();
        Session session = null;
        try {
            session = (Session) clientManager.connectToServer(containerTest, URI.create("ws://localhost:8080/test_war_exploded/status"));
            session.getBasicRemote().sendText("Hello from client 3");
        } catch (jakarta.websocket.DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }


    private void startWorking() {
        int index = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/java/data/operations.txt"));
            String line = reader.readLine();
            Random random = new Random();
            while (line != null) {
                if (line.startsWith("0")){
                    line = line.replaceFirst("0 ", "");
                    int rand = random.nextInt(socketListA.size());
                    System.out.println("accessing this random number in arrayA: " + rand);
                    socketListA.get(rand).notifyData(line, false);
                    //opsA.add(line);
                }else if(line.startsWith("1")){
                    line = line.replaceFirst("1 ", "");
                    int rand = random.nextInt(socketListB.size());
                    System.out.println("accessing this random number in arrayB: " + rand);
                    socketListB.get(rand).notifyData(line, false);
                    //opsB.add(line);
                }else if(line.startsWith("2")){
                    line = line.replaceFirst("2 ", "");
                    int rand = random.nextInt(socketListC.size());
                    System.out.println("accessing this random number in arrayC: " + rand);
                    socketListC.get(rand).notifyData(line, false);
                    //opsC.add(line);
                }
                index = updateLastOperations(line, index);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int updateLastOperations(String line, int index) {
        if (index < 10){
            lastOperations[index] = line;
            index++;
        }else{
            System.arraycopy(lastOperations, 1, lastOperations, 0, lastOperations.length - 1);
        }
        return index;
    }

    private void readOps() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/java/data/operations.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("0")){
                    line = line.replaceFirst("0 ", "");
                    opsA.add(line);
                }else if(line.startsWith("1")){
                    line = line.replaceFirst("1 ", "");
                    opsB.add(line);
                }else if(line.startsWith("2")){
                    line = line.replaceFirst("2 ", "");
                    opsC.add(line);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("A size:" + opsA.size());
        for (String s : opsA){
            System.out.println("A: " + s);
        }
        System.out.println("B size:" + opsB.size());
        for (String s : opsB){
            System.out.println("B: " + s);
        }
        System.out.println("C size:" + opsC.size());
        for (String s : opsC){
            System.out.println("C: " + s);
        }
    }

    private void setSockets(int[] ports, int myPort) {
        // Await socket connections
        new NodeSocket(myPort, this).start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Create as many outgoing sockets as ports the node must connect to
        for (int port : ports) {
            MySocket mySocket = new MySocket(port);
            if (port == 44444 | port == 44445 | port == 44446){
                socketListA.add(mySocket);
            }else if(port == 44447 | port == 44448){
                socketListB.add(mySocket);
            }else if (port == 44449 | port == 44450){
                socketListC.add(mySocket);
            }
        }
    }

    public void updateC() {
        socketListC.forEach(mySocket -> mySocket.updateStatus(status));
    }

    public void updateB() {
        System.out.println("Updating all Bs");
        socketListB.forEach(mySocket -> mySocket.updateStatus(status));
    }

    public void updateA(String operation) {
        socketListA.forEach(mySocket -> mySocket.notifyData(operation, true));
    }

    public synchronized void doOperation(String operation) {
        if (operation.startsWith("r")){
            operation = operation.replaceAll("[^\\d]", "");
            System.out.println("\tReading index " + operation +  ": " + status[Integer.parseInt(operation)]);
        }else if(operation.startsWith("w")){
            operation = operation.replaceAll("[^\\d,]", "");
            String[] ops = operation.split(",");
            status[Integer.parseInt(ops[0])] = Integer.parseInt(ops[1]);
            System.out.println("\tWriting " + Integer.parseInt(ops[1]) + " in index " + Integer.parseInt(ops[0]));
        }
        System.out.println(Arrays.toString(status));
        System.out.println("---- Operation completed ----");
    }


    public void setStatus(int[] status) {
        // TODO: notify websocket of status change
        System.out.println("Updating status");
        System.out.println(Arrays.toString(status));
        this.status = status;
    }

    public String getName() {
        return name;
    }
}
