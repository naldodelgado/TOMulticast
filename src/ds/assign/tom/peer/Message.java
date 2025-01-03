package ds.assign.tom.peer;

import java.util.Objects;

public class Message implements Comparable<Message> {
    private final String word;
    private final int clock;

    public Message(String word, int clock) {
        this.word = word;
        this.clock = clock;
    }

    public String getWord() {
        return word;
    }

    public int getClock() {
        return clock;
    }

    public String toString() {
        return word + ":  " + clock;
    }

    @Override
    public int compareTo(Message other) {
        int clockComparison = Integer.compare(this.clock, other.clock);
        if (clockComparison != 0) {
            return clockComparison;
        }
        return this.word.compareTo(other.word);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Message message = (Message) obj;
        return clock == message.clock && word.equals(message.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, clock);
    }
}
