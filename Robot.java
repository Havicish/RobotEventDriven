public class Robot {
    private final TopicManager topicManager;
    private float x;
    private float y;
    private float theta;

    Subscriber odometrySubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            String[] parts = content.split(",");
            System.out.println("Received parts: " + String.join(", ", parts));
            float deltaX = Float.parseFloat(parts[0]);
            float deltaY = Float.parseFloat(parts[1]);
            float deltaTheta = Float.parseFloat(parts[2]);

            // Update the robot's position
            Robot.this.x += deltaX;
            Robot.this.y += deltaY;
            Robot.this.theta += deltaTheta;

            // Publish the updated position
            topicManager.publish("pos", String.format("%s,%s,%s", Robot.this.x, Robot.this.y, Robot.this.theta));
            topicManager.publish("pos::x", String.valueOf(Robot.this.x));
            topicManager.publish("pos::y", String.valueOf(Robot.this.y));
            topicManager.publish("pos::theta", String.valueOf(Robot.this.theta));
        }
    };

    Subscriber targetPositionSubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            String[] parts = content.split(",");
            float targetX = Float.parseFloat(parts[0]);
            float targetY = Float.parseFloat(parts[1]);
            float targetTheta = Float.parseFloat(parts[2]);

            topicManager.publish("moveRobot", String.format("%s,%s,%s", targetX - Robot.this.x, targetY - Robot.this.y, targetTheta - Robot.this.theta));
        }
    };

    public Robot(TopicManager topicManager) {
        this.topicManager = topicManager;
        this.x = 0;
        this.y = 0;
        this.theta = 0;

        topicManager.subscribe("odometry", odometrySubscriber);

        topicManager.subscribe("targetPos", targetPositionSubscriber);

        topicManager.publish("pos", String.format("%s,%s,%s", this.x, this.y, this.theta));
        topicManager.publish("pos::x", String.valueOf(this.x));
        topicManager.publish("pos::y", String.valueOf(this.y));
        topicManager.publish("pos::theta", String.valueOf(this.theta));
    }
}
