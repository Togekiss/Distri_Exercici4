package network.client.incoming;

import controller.Node;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class DedicatedNodeSocket extends Thread {
    private final Node node;
    private final Socket sClient;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;

    public DedicatedNodeSocket(Socket sClient, Node node) {
        this.sClient = sClient;
        this.node = node;
    }

    @Override
    public void run() {
        try {
            // create the instance to receive and send the info
            diStream = new DataInputStream(sClient.getInputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());

            while (true){
                String request = diStream.readUTF();
                readRequest(request);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) throws IOException, ClassNotFoundException {
        //System.out.println("Got this message: " + request);
        String operation;

        // look if the request is reservation
        switch (request){
            case "OPERATION":
                operation = diStream.readUTF();
                System.out.println("OPERATION " + operation);
                //System.out.println("Got the following operation: " + operation);
                node.doOperation(operation);
                if (Node.name.startsWith("A")){
                    System.out.println("I'm an A so calling eager update to all As");
                    node.updateA(operation);
                    if (operation.startsWith("w")){
                        node.increaseNumUpdates();
                    }
                }
                break;
            case "UPDATE":
                operation = diStream.readUTF();
                System.out.println("UPDATE " + operation);
                node.doOperation(operation);
                if (operation.startsWith("w")){
                    node.increaseNumUpdates();
                }
                break;
            case "STATUS_UPDATE":
                System.out.println("Got STATUS_UPDATE");
                String aux = diStream.readUTF();
                System.out.println("Sender: " + aux);
                int [] status = (int[]) oiStream.readObject();
                node.setStatus(status);
                break;
            case "END":
                node.closeFile();
               // diStream.close();
               // oiStream.close();
                break;
        }
        if (node.getNumUpdates() == 10){
            System.out.println("I'm an A and 10 transactions have occurred. Updating Bs");
            node.updateB();
            node.setNumUpdates(0);
        }
    }
}