package network;

import java.util.ArrayList;

public class NodeData {
    public ArrayList<String> status;
    public String statusA1;
    public String statusA2;
    public String statusA3;
    public String statusB1;
    public String statusB2;
    public String statusC1;
    public String statusC2;

    private static NodeData single_instance = null;

    // private constructor restricted to this class itself
    private NodeData() {
        status = new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static NodeData getInstance() {
        if (single_instance == null)
            single_instance = new NodeData();

        return single_instance;
    }
}
