package ds.assign.tom.peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class InPipe implements Runnable {
    private final Socket client;
    private AtomicInteger lamportClock;
    private PriorityBlockingQueue<Message> messageQueue;
    private MessageListener listener;

    public InPipe(Socket client, AtomicInteger lamportClock, PriorityBlockingQueue<Message> messageQueue) throws IOException {
        this.client = client;
        this.lamportClock = lamportClock;
        this.messageQueue = messageQueue;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
            while (true) {
                String line = in.readLine();
                if (line == null) break;  // Exit loop if the client closes the connection

                String[] parts = line.split(":");
                if (parts.length < 2) continue;
                String word = parts[0];
                int clock = Integer.parseInt(parts[1]);

                Message m = new Message(word, clock);
                listener.onMessageReceived(m);
    //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MessageListener {
        void onMessageReceived(Message message);
    }
}