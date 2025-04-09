public class Main {
    public static void main(String[] args) {
        TopicManager topicManager = new TopicManager();

        // Create a subscriber
        Subscriber newsSubscriber = new Subscriber() {
            @Override
            public void onMessage(Message message) {
                System.out.println("Received message on topic " + message.getTopic() + ": " + message.getContent());
            }
        };

    
        Subscriber motionSubscriber = new Subscriber() {
            @Override
            public void onMessage(Message message) {
                System.out.println("The robot moved to " + message.getContent());
            }
        };

   
        topicManager.subscribe(topic, newsSubscriber);

        // Publish a message to the topic
        Message message = new Message(topic, "Breaking news!");
        topicManager.publish( message);

        // Unsubscribe from the topic
        topicManager.unsubscribe(topic, newsSubscriber);

        topicManager.subscribe("motion", motionSubscriber);

        topicManager.publish(new Message("motion", "the left"));

    }
}