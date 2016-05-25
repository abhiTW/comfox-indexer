package model.slack;

public class SlackMessage {
    String username;
    String message;
    String channel;

    public SlackMessage(String username, String message, String channel) {
        this.username = username;
        this.message = message;
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "SlackMessage{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}

