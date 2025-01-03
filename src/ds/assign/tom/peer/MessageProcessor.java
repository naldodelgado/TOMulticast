package ds.assign.tom.peer;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageProcessor implements InPipe.MessageListener {
    private static MessageProcessor instance;
    private final PriorityBlockingQueue<Message> messageQueue;
    private final AtomicInteger lamportClock;

    private MessageProcessor(PriorityBlockingQueue<Message> messageQueue, AtomicInteger lamportClock){
        this.messageQueue = messageQueue;
        this.lamportClock = lamportClock;
        new Thread(this::processMessages).start();
    }

    public static synchronized MessageProcessor getInstance(PriorityBlockingQueue<Message> messageQueue, AtomicInteger lamportClock){
        if(instance == null){
            instance = new MessageProcessor(messageQueue,lamportClock);
        }
        return instance;
    }

    @Override
    public void onMessageReceived(Message message) {
        synchronized (lamportClock) {
            lamportClock.set(Math.max(lamportClock.get(), message.getClock()) + 1);
            messageQueue.add(message);
        }
    }

    private void processMessages(){
        try{
            Thread.sleep(2000); // a cold start so that OutPipe and InPipe can start generating words and queue them on the priority list
            while(true){
                synchronized (lamportClock){
                    if(!messageQueue.isEmpty() && messageQueue.peek().getClock() <= lamportClock.get()){
                        Message message = messageQueue.poll();
                        System.out.println(message);
                    }
                }
                Thread.sleep(1000); // to ensure messages are processed in the same pace as they are generated
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
