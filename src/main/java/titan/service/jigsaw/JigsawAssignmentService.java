package titan.service.jigsaw;


import model.jigsaw.Assignment;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.service.GraphService;

import java.util.List;

public class JigsawAssignmentService extends GraphService {
    private JigsawPersonService jigsawPersonService;
    private JigsawProjectService jigsawProjectService;

    public JigsawAssignmentService(JigsawPersonService jigsawPersonService, JigsawProjectService jigsawProjectService) {
        this.jigsawPersonService = jigsawPersonService;
        this.jigsawProjectService = jigsawProjectService;
    }

    public void addListOfAssignedToEdges(List<Assignment> assignments){
        Vertex personNode, projectNode;
        for(Assignment assignment: assignments){
            personNode = jigsawPersonService.getPersonNodeById(assignment.getConsultant()).
                    orElse(jigsawPersonService.createConsultantNode(assignment.getConsultant()));

            projectNode = jigsawProjectService.getProjectNodeById(assignment.getProject()).
                    orElse(jigsawProjectService.createProjectNode(assignment.getProject()));

            addConsultantAssignedToProjectEdge(personNode, projectNode, assignment);
            titanGraph.tx().commit();
        }
    }

    private void addConsultantAssignedToProjectEdge(Vertex consultantNode, Vertex projectNode, Assignment assignment) {
        consultantNode.addEdge("assigned_to", projectNode, "assignmentId", assignment.getId(),
                "staffingRequest", assignment.getStaffingRequest(), "effort", assignment.getEffort(),
                "shadow", assignment.getShadow(), "accountName", assignment.getAccount(),
                "startsOn", assignment.getDuration().getStartsOn(), "endsOn", assignment.getDuration().getEndsOn());
        titanGraph.tx().commit();
    }
}
