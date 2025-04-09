import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private final String topic;
    private final String content;

    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }
    public String getTopic() {
        return topic;
    }
    public String getContent() {
        return content;
    }
}

interface Subscriber {
    void onMessage(Message message);
}

class TopicManager {
    private final Map<String, List<Subscriber>> subscribers = new HashMap<>();
    public void subscribe(String topic, Subscriber subscriber) {
        subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(subscriber);
    }
    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> topicSubscribers = subscribers.get(topic);
        if (topicSubscribers != null) {
            topicSubscribers.remove(subscriber);
            if (topicSubscribers.isEmpty()) {
                subscribers.remove(topic);
            }
        }
    }
    public void publish(Message message) {
        String topic = message.getTopic();
        List<Subscriber> topicSubscribers = subscribers.get(topic);
        if (topicSubscribers != null) {
            for (Subscriber subscriber : topicSubscribers) {
                subscriber.onMessage(message);
            }
        }
    }
}
