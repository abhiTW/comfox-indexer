package titan.service.slack;


import model.slack.SlackMessage;
import org.apache.tinkerpop.gremlin.structure.T;
import titan.service.GraphService;

import java.time.LocalDateTime;

public class SlackMessageService extends GraphService {

    public void createSlackMessageNode(SlackMessage slackMessage) {
        titanGraph.addVertex(T.label,"message","username",slackMessage.getUsername(),"content",slackMessage.getMessage(),"timestamp", String.valueOf(LocalDateTime.now()));
        titanGraph.tx().commit();
    }

}
