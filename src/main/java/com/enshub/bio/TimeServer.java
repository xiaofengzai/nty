package com.enshub.bio;

import com.enshub.IoUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        ServerSocket server=null;
        try {
            server=new ServerSocket(port);
            System.out.println("The time server is started at port:"+port);
            Socket socket=null;
            while (true){
                socket=server.accept();
                new Thread(new TimeHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server!=null){
                System.out.println("The time server close");
                IoUtils.closeIoResource(server);
            }
        }
    }
}
