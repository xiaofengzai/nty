package com.enshub.nio;

public class TimeServer {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        MultiplexerTimerServer timerServer=new MultiplexerTimerServer(port);
        new Thread(timerServer,"server-001").start();
    }
}
