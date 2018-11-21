package com.enshub.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {
    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {

            serverSocketChannel=AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("THe time server is started at :"+port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch=new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void  doAccept(){
        serverSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>() {
            @Override
            public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
                attachment.serverSocketChannel.accept(attachment,this);
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                result.read(buffer,buffer,new ReadCompletionHandler(result));
            }

            @Override
            public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
                exc.printStackTrace();
                attachment.latch.countDown();
            }
        });
    }
}
