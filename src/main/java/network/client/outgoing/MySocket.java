package network.client.outgoing;

import controller.Node;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class MySocket extends Thread{
    private DataOutputStream doStream;
    private ObjectOutputStream ooStream;

    public MySocket(int port) {
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            //creem el nostre socket
            iAddress = InetAddress.getLocalHost();
            String ip = iAddress.getHostAddress();

            Socket socketServer = new Socket(ip, port);
            doStream = new DataOutputStream(socketServer.getOutputStream());
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
            doStream.writeUTF("STATUS_UPDATE");
            doStream.writeUTF(Node.name);
            ooStream.reset();
            ooStream.writeObject(status);

            //ooStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyEnd() {
        try {
            doStream.writeUTF("END");
            //doStream.close();
            //ooStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}