package ds.assign.tom.peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Peer {
    private static List<InetAddress> peers;
    public static AtomicInteger lamportClock;
    private static ServerSocket socket;
    private static PriorityBlockingQueue<Message> messageQueue;
    private static Set<InetAddress> activeConnections;


    public static void main(String[] args) throws IOException {
        lamportClock = new AtomicInteger(0);
        socket = new ServerSocket(5000,0, InetAddress.getByName("0.0.0.0"));
        peers = new ArrayList<>();
        messageQueue = new PriorityBlockingQueue<>();
        activeConnections = new HashSet<>();

        for (String arg : args) {
            peers.add(InetAddress.getByName(arg));
        }

        MessageProcessor messageProcessor = MessageProcessor.getInstance(messageQueue, lamportClock);

        OutPipe outPipe = new OutPipe(peers,lamportClock,messageQueue);
        outPipe.setListener(messageProcessor);
        new Thread(outPipe).start();

        try{
            while(true){
                Socket client = socket.accept();
                InetAddress clientAddress = client.getInetAddress();

                synchronized (activeConnections){
                    if(!activeConnections.contains(clientAddress)){
                        activeConnections.add(clientAddress);
                        InPipe pipe = (new InPipe(client,lamportClock,messageQueue));
                        pipe.setListener(messageProcessor);
                        new Thread(pipe).start();
                    } else {
                        client.close();
                    }
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
