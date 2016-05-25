package titan.service;


import com.thinkaurelius.titan.core.TitanGraph;
import titan.util.TitanGraphFactory;

public class GraphService {
    protected TitanGraph titanGraph;

    public GraphService() {
        this.titanGraph = TitanGraphFactory.getTitanFactoryInstance();
    }

    public void closeDb() {
        TitanGraphFactory.closeTitanGraph();
    }
}
