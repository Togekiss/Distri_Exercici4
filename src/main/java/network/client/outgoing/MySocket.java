package network.client.outgoing;

import controller.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



/**
 * Class that allows for the communication with the server
 */
public class MySocket extends Thread{
    private Socket socketServer;
    private DataOutputStream doStream;
    private ObjectOutputStream ooStream;
    private DataInputStream diStream;


    public MySocket(int port) {
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            //creem el nostre socket
            iAddress = InetAddress.getLocalHost();
            String ip = iAddress.getHostAddress();

            socketServer = new Socket(ip, port);
            doStream = new DataOutputStream(socketServer.getOutputStream());
            diStream = new DataInputStream(socketServer.getInputStream());
            ooStream = new ObjectOutputStream(socketServer.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyData(String operation, boolean update) {
        try {
            if (update){
                doStream.writeUTF("UPDATE");
            }else{
                doStream.writeUTF("OPERATION");
            }
            doStream.writeUTF(operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int[] status) {
        try {
            System.out.println("Writing a STATUS_UPDATE");
            doStream.writeUTF("STATUS_UPDATE");
            doStream.writeUTF(Node.name);
            ooStream.writeObject(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}