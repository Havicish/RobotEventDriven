public class Motion {
    // Publish a message to the topic
    Message message = new Message("motion", "4,5");
    topicManager.publish(message);

}
