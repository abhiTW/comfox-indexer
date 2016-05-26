package titan.service.slack;


import com.thinkaurelius.titan.core.TitanVertex;
import model.slack.SlackMessage;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.service.GraphService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlackMessageService extends GraphService {

    public Vertex createSlackMessageNode(SlackMessage slackMessage) {
        TitanVertex titanVertex = titanGraph.addVertex(T.label, "message", "username", slackMessage.getUsername(), "content", slackMessage.getMessage(), "timestamp", String.valueOf(LocalDateTime.now()));
        titanGraph.tx().commit();

        return titanVertex;
    }

    public void interpretSlackMessage(SlackMessage slackMessage) {
        String specialCharacter = "#";
        Matcher matcher = Pattern.compile(specialCharacter + "\\s*(\\w+)").matcher(slackMessage.getMessage());
        while (matcher.find()) {
            String word = matcher.group(1);
            String trimmedWord = word.replaceAll("(.)([A-Z])", "$1 $2");
            addListOfRelatedToEdges(trimmedWord, slackMessage);
        }

    }

    public void  addListOfRelatedToEdges(String vertexIdentifier, SlackMessage slackMessage) {
        Vertex slackMessageNode = createSlackMessageNode(slackMessage);
        Optional<Vertex> vertexByName = findVertexByName(vertexIdentifier);
        Vertex adjacentNode = vertexByName.isPresent() ? vertexByName.get() : createNode(vertexIdentifier);
        adjacentNode.addEdge("related_to", slackMessageNode);
    }

    // assuming the non-existing node to be a skill need to modify it
    private Vertex createNode(String vertexIdentifier) {
        return titanGraph.addVertex(T.label, "skill", "name", vertexIdentifier);
    }

    // need to check the failure case
    private Optional<Vertex> findVertexByName(String vertexIdentifier) {
        GraphTraversal<Vertex, Vertex> vertex = titanGraph.traversal().V().has("name", vertexIdentifier);
        return Optional.ofNullable(vertex.hasNext() ? vertex.next() : null);
    }
}
