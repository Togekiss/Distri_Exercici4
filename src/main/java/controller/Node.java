package controller;

import network.client.ClientWebSocket;
import network.client.incoming.NodeSocket;
import network.client.outgoing.MySocket;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Node {
    public static String name;
    private final ArrayList<MySocket> socketListA;
    private final ArrayList<MySocket> socketListB;
    private final ArrayList<MySocket> socketListC;
    private final String[] lastOperations;
    private int[] status;
    private final ClientWebSocket clientWebSocket;
    private BufferedWriter writer;
    private TimerThread timerThread;

    public Node(String name, int myPort, int numPorts, String[] arguments){
        Node.name = name;
        socketListA = new ArrayList<>();
        socketListB = new ArrayList<>();
        socketListC = new ArrayList<>();
        lastOperations = new String[10];
        status = new int[30];
        clientWebSocket = new ClientWebSocket();
        File myObj = new File("./src/main/java/data/log" + name + ".txt");

        try {
            writer = new BufferedWriter(new FileWriter(myObj));
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                writer.write("");
            }
            ContainerProvider.getWebSocketContainer().connectToServer(clientWebSocket, URI.create("ws://localhost:8080/test_war_exploded/status"));
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }

        int[] ports = new int[numPorts];

        for (int i = 0; i < numPorts; i++){
            ports[i] = Integer.parseInt(arguments[i + 3]);
        }
        System.out.println("Waiting...");

        setSockets(ports, myPort);
        System.out.println("Socket list A: " + socketListA.size());
        System.out.println("Socket list B: " + socketListB.size());
        System.out.println("Socket list C: " + socketListC.size());
        if (name.equals("Client")){
            startWorking();
        }else if (name.equals("B2")){
            timerThread = new TimerThread(this);
            timerThread.start();
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

                Thread.sleep(500);
                // read next line
                line = reader.readLine();
            }
            reader.close();
            socketListA.forEach(MySocket::notifyEnd);
            socketListB.forEach(MySocket::notifyEnd);
            socketListC.forEach(MySocket::notifyEnd);
        } catch (IOException | InterruptedException e) {
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
        socketListB.forEach(mySocket -> mySocket.updateStatus(status));
    }

    public void updateA(String operation) {
        socketListA.forEach(mySocket -> mySocket.notifyData(operation, true));
    }

    public synchronized void doOperation(String operation) {
        String op = operation;
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
        notifyTomcat(name, op, status);
        System.out.println("---- Operation completed ----");
    }


    public void setStatus(int[] status) {
        System.out.println("Updating status");
        System.out.println(Arrays.toString(status));
        this.status = status;
        notifyTomcat(name, "Status update", status);
    }

    private void notifyTomcat(String name, String operation, int[] status) {
        String op;
        try {
            op = "Node " + name + " performed operation: " + operation + " resulting in this status:\n" + Arrays.toString(status);
            try {
                writer.append(op).append("\n");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            clientWebSocket.getSession().getBasicRemote().sendText(op);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        try {
            writer.close();
            if (name.equals("B2")){
                timerThread.join();
                clientWebSocket.getSession().close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
