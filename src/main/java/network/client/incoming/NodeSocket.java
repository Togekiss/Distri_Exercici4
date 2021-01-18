package network.client.incoming;

import controller.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NodeSocket extends Thread {

    private final Node node;
    private final int myPort;

    public NodeSocket(int myPort, Node node) {
        this.myPort = myPort;
        this.node = node;
    }


    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(myPort);
            while (true){
                Socket sClient = serverSocket.accept();
                if (sClient.isConnected()) {
                    generaNouServidorDedicat(sClient);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generaNouServidorDedicat(Socket sClient){
        DedicatedNodeSocket dedicatedNodeSocket = new DedicatedNodeSocket(sClient, node);
        dedicatedNodeSocket.start();
    }
}