package com.enshub.bio;

import com.enshub.IoUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {
    public static void main(String[] args) {
        int port=8888;
        if(args!=null && args.length>0){
            port=Integer.valueOf(args[0]);
        }
        Socket socket=null;
        BufferedReader in =null;
        PrintWriter out=null;
        try {
            socket=new Socket("127.0.0.1",port);
            in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("send order 2 server succeed");
            String resp=in.readLine();
            System.out.println("Now is :"+resp);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IoUtils.closeIoResource(out);
            IoUtils.closeIoResource(in);
            IoUtils.closeIoResource(socket);
        }
    }
}
