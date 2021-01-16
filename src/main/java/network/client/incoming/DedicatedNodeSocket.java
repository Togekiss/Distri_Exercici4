// package where it belongs
package network.client.incoming;


// import java classes
import controller.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


/**
 * This class manages the network service of the reception program
 */
public class DedicatedNodeSocket extends Thread {

    private Node node;
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;
    private int numUpdates;


    /**
     * Constructor with parameters of the class
     * @param sClient Socket of the client
     * @param node MainController instance that manages the server
     */
    public DedicatedNodeSocket(Socket sClient, Node node) {
        this.sClient = sClient;
        this.node = node;
        numUpdates = 0;
    }

    /**
     * This method is triggered when start the tread
     */
    @Override
    public void run() {
        try {
            // create the instance to receive and send the info
            doStream = new DataOutputStream(sClient.getOutputStream());
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

    /**
     * This function manages the received request and sends the answer
     * @param request String with the request
     * @throws IOException Exception that's raised
     */
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
                    numUpdates++;
                }
                break;
            case "UPDATE":
                operation = diStream.readUTF();
                System.out.println("UPDATE " + operation);
                node.doOperation(operation);
                numUpdates++;
                //System.out.println("Got the following update: " + operation);
                break;
            case "STATUS_UPDATE":
                System.out.println("Got STATUS_UPDATE");
                String aux = diStream.readUTF();
                System.out.println("Sender: " + aux);
                int [] status = (int[]) oiStream.readObject();
                node.setStatus(status);
                break;
        }
        if (numUpdates == 10){
            System.out.println("I'm an A and 10 transactions have occurred. Updating Bs");
            node.updateB();
            numUpdates = 0;
        }
    }
}