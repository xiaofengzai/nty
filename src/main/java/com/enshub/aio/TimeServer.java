package com.enshub.aio;

public class TimeServer {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        AsyncTimeServerHandler timerServer=new AsyncTimeServerHandler(port);
        new Thread(timerServer,"server-001").start();
    }
}
