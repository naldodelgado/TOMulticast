package ds.assign.tom.peer;

import ds.assign.tom.poisson.PoissonProcess;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class OutPipe implements Runnable {
    private List<InetAddress> peers;
    private AtomicInteger lamportClock;
    private List<String> words;
    private PriorityBlockingQueue<Message> messageQueue;
    private InPipe.MessageListener listener;

    public OutPipe(List<InetAddress> peers, AtomicInteger lamportClock, PriorityBlockingQueue<Message> messageQueue) throws IOException {
        this.peers = peers;
        this.lamportClock = lamportClock;
        this.messageQueue = messageQueue;
        this.words = Files.readAllLines(Paths.get("out/production/TOMulticast/ds/assign/tom/peer/resources/words.txt"));
    }

    public void setListener(InPipe.MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        Random random = new Random();
        PoissonProcess pp = new PoissonProcess(1, new Random((int) (Math.random() * 10000 * 60)));

        while (true) {
            try {
                Thread.sleep(1000);
                String word = words.get(random.nextInt(words.size()));
                Message m = new Message(word, lamportClock.incrementAndGet());
                sendWordToPeers(m);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendWordToPeers(Message message) throws IOException {
        if(message != null)
            listener.onMessageReceived(message);

        for (InetAddress peer : peers) {
            try (Socket socket = new Socket(peer, 5000)) {
                socket.getOutputStream().write((message.getWord()+":"+message.getClock()+ "\n").getBytes());
                socket.getOutputStream().flush();
            }
        }
    }
}