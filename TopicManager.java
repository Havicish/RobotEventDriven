import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class TopicManager {
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
    public void publish(String topic, String content) {
        List<Subscriber> topicSubscribers = subscribers.get(topic);
        if (topicSubscribers != null) {
            for (Subscriber subscriber : topicSubscribers) {
                subscriber.onMessage(topic, content);
            }
        }
    }
}

interface Subscriber {
    void onMessage(String topic, String content);
}