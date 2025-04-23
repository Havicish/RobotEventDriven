import java.lang.Thread;

public class Odometry extends Thread {
    public Odometry(TopicManager topicManager) {
        /*new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(100);
                    topicManager.publish("odometry.x", Math.random() * 1 - 0.5 + "");
                    topicManager.publish("odometry.y", Math.random() * 1 - 0.5 + "");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                topicManager.publish("odometry", "Thread interrupted");
            }
        }).start();*/

        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    topicManager.publish("odometry", "1.0,1.0,1.0");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}