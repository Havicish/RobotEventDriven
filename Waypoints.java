import java.util.ArrayList;
import java.util.List;

public class Waypoints {
    private List<Waypoint> sequence = new ArrayList<>();
    private int sequenceIndex = 0;
    private TopicManager topicManager;

    Subscriber robotPosSubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            String[] parts = content.split(",");
            String x = parts[0];
            String y = parts[1];
            String theta = parts[2];

            if (Float.parseFloat(x) == sequence.get(sequenceIndex).x &&
                Float.parseFloat(y) == sequence.get(sequenceIndex).y &&
                Float.parseFloat(theta) == sequence.get(sequenceIndex).theta) {
                System.out.println(String.format("Got position: %s, %s, %s. It is a waypoint", x, y, theta));
                sequenceIndex++;
                if (sequenceIndex >= sequence.size()) {
                    sequenceIndex = 0;
                }
                topicManager.publish("targetPos", String.format("%s,%s,%s", sequence.get(sequenceIndex).x, sequence.get(sequenceIndex).y, sequence.get(sequenceIndex).theta));
            } else {
                System.out.println(String.format("Got position: %s, %s, %s. It is not a waypoint. We want %s, %s, %s", x, y, theta, sequence.get(sequenceIndex).x, sequence.get(sequenceIndex).y, sequence.get(sequenceIndex).theta));
            }
        }  
    };

    public Waypoints(TopicManager topicManager) {
        this.topicManager = topicManager;

        this.sequence.add(new Waypoint(0, 0, 0));
        this.sequence.add(new Waypoint(1, 1, 1));
        this.sequence.add(new Waypoint(2, 2, 2));

        topicManager.subscribe("pos", robotPosSubscriber);
    }
}