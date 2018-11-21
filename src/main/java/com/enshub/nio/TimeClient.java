package com.enshub.nio;

public class TimeClient {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        TimeClientHandle timeClientHandle=new TimeClientHandle("127.0.0.1",port);
        new Thread(timeClientHandle,"client-001").start();
    }
}
