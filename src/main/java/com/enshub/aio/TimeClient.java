package com.enshub.aio;

import com.enshub.nio.TimeClientHandle;

public class TimeClient {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        AsyncTimeClientHandler timeClientHandle=new AsyncTimeClientHandler("127.0.0.1",port);
        new Thread(timeClientHandle,"client-001").start();
    }
}
