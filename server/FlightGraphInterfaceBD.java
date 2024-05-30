// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.io.FileNotFoundException;
import java.util.List;

public interface FlightGraphInterfaceBD<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {
    public List<NodeType> getAirports();
    public List<EdgeType> getRoutes(NodeType origin, NodeType destination);
    public List<NodeType> filterWithoutAirport(NodeType origin, NodeType destination, NodeType toFilter);
    public List<NodeType> filterWithAirport(NodeType origin, NodeType destination, NodeType toFilter);
}
