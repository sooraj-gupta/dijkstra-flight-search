package server;
import java.util.List;

public class FlightGraphFD<NodeType, EdgeType extends Number> implements FlightGraphInterface<NodeType, EdgeType>
{

  @Override
  public boolean insertNode(NodeType data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertNode'");
  }

  @Override
  public boolean removeNode(NodeType data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeNode'");
  }

  @Override
  public boolean containsNode(NodeType data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsNode'");
  }

  @Override
  public int getNodeCount() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getNodeCount'");
  }

  @Override
  public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertEdge'");
  }

  @Override
  public boolean removeEdge(NodeType pred, NodeType succ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeEdge'");
  }

  @Override
  public boolean containsEdge(NodeType pred, NodeType succ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsEdge'");
  }

  @Override
  public EdgeType getEdge(NodeType pred, NodeType succ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEdge'");
  }

  @Override
  public int getEdgeCount() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEdgeCount'");
  }

  @Override
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'shortestPathData'");
  }

  @Override
  public double shortestPathCost(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'shortestPathCost'");
  }

  @Override
  public List<NodeType> getAirports() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAirports'");
  }

  @Override
  public List<EdgeType> getRoutes(NodeType origin,
  NodeType destination) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRoutes'");
  }

  @Override
  public List<NodeType> filterWithoutAirport(NodeType origin,
  NodeType destination, NodeType toFilter) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'filterWithoutAirport'");
  }

  @Override
  public List<NodeType> filterWithAirport(NodeType origin,
  NodeType destination, NodeType toFilter) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'filterWithAirport'");
  }
  
}
