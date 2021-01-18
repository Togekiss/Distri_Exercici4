package controller;

import java.util.Timer;
import java.util.TimerTask;

public class TimerThread extends Thread {
    private final Node node;
    public TimerThread(Node node) {
        this.node = node;
    }

    @Override
    public void run(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("10 seconds passed");
                node.updateC();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 10000, 10000);
    }
}
