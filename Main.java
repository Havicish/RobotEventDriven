public class Main {
    public static void main(String[] args) {
        TopicManager topicManager = new TopicManager();
    
        /*Subscriber motionSubscriber = new Subscriber() {
            @Override
            public void onMessage(String topic, String content) {
                System.out.println("The robot moved to " + content);
            }
        };

        topicManager.subscribe("motion", motionSubscriber);

        topicManager.publish("motion", "the left");*/

        Odometry odometry = new Odometry(topicManager);
        MotorController motorController = new MotorController(topicManager);
        Waypoints waypoints = new Waypoints(topicManager);
        Robot robot = new Robot(topicManager);

    }
}