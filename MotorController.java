public class MotorController {
    private float flPower = 0;
    private float frPower = 0;
    private float blPower = 0;
    private float brPower = 0;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaTheta = 0;

    Subscriber moveRobotSubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            String[] parts = content.split(",");
            Float deltaX = Float.parseFloat(parts[0]);
            Float deltaY = Float.parseFloat(parts[1]);
            Float deltaTheta = Float.parseFloat(parts[2]);

            MotorController.this.deltaX += deltaX;
            MotorController.this.deltaY += deltaY;
            MotorController.this.deltaTheta += deltaTheta;
        }
    };

    Subscriber stopRobotSubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            deltaX = 0;
            deltaY = 0;
            deltaTheta = 0;
        }
    };

    Subscriber odometrySubscriber = new Subscriber() {
        @Override
        public void onMessage(String topic, String content) {
            String[] parts = content.split(",");
            deltaX = Float.parseFloat(parts[0]);
            deltaY = Float.parseFloat(parts[1]);
            deltaTheta = Float.parseFloat(parts[2]);

            // Update motor powers based on odometry data
            this.deltaX -= deltaX;
            this.deltaY -= deltaY;
            this.deltaTheta -= deltaTheta;

            flPower = deltaX + deltaY + deltaTheta;
            frPower = deltaX - deltaY - deltaTheta;
            blPower = deltaX - deltaY + deltaTheta;
            brPower = deltaX + deltaY - deltaTheta;
        }
    };

    public MotorController(TopicManager topicManager) {
        topicManager.subscribe("moveRobot", moveRobotSubscriber);
        topicManager.subscribe("stopRobot", stopRobotSubscriber);
        topicManager.subscribe("odometry", odometrySubscriber);

        // Initialize motor powers
        flPower = 0;
        frPower = 0;
        blPower = 0;
        brPower = 0;

        topicManager.publish("motorController", "MotorController initialized");
    }
}
