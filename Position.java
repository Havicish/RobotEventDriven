public class Position {
    topicManager.subscribe("motion", new Subscriber() {
        @Override
        public void onMessage(Message message) {
            // store the robot's position in a variable. The message is an x, y coordinate
            String[] coordinates = message.getContent().split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            // Assuming you have a Robot class with setPosition method
            Robot robot = new Robot();
            robot.setPosition(x, y);
            System.out.println("The robot moved to " + message.getContent());   
        }
    });
}
