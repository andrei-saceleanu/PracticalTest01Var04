package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class ProcessingThread extends Thread{

    private Context context = null;
    private boolean isRunning = true;
    private String name, group;

    private int idx = 0;

    public ProcessingThread(Context context, String name, String group){
        this.context = context;
        this.name = name;
        this.group = group;
    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[idx]);
        if(idx == 0) {
            intent.putExtra("[broad]", name);
        }else{
            intent.putExtra("[broad]", group);
        }
        context.sendBroadcast(intent);
        idx = 1 - idx;
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
