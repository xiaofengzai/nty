package com.enshub.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimerServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimerServer(int port) {
        try {
            selector=Selector.open();
            serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println("Time server is start in port:"+port);
        }catch (Exception e){
            System.exit(1);
        }
    }

    public void stop(){
        this.stop=true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey>  selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while(it.hasNext()){
                    key=it.next();
                    // ddd
                    it.remove();
                    handInput(key);
                    if(key!=null){
                        key.cancel();
                        if(key.channel()!=null){
                            key.channel().close();
                        }
                    }
                }
            }catch (IOException e){

            }
        }
        if(selector!=null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}

    private void handInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            if(key.isAcceptable()){
                ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
                SocketChannel sc=ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                SocketChannel sc=(SocketChannel) key.channel();
                ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                int readBytes=sc.read(readBuffer);
                if(readBytes>0){
                    readBuffer.flip();
                    byte[] bytes=new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("The time server receive order:"+body);
                    String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    doWrite(sc,currentTime);
                }else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }else{

                }
            }
        }

    }
    private void doWrite(SocketChannel channel,String resp) throws IOException{
        if(resp!=null && resp.trim().length()>0){
            byte[] bytes=resp.getBytes();
            ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);

        }
    }
}
