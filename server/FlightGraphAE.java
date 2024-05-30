package server;
// --== CS400 File Header Information ==--
// Name: Vishal Singh Downdiyakhed
// Email: vsingh48@wisc.edu 
// Group and Team: BI Blue
// Group TA: Naman Gupta
// Lecturer: Dahl
// Notes to Grader: N/A
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

public class FlightGraphAE<NodeType, EdgeType extends Number> implements FlightGraphInterface<NodeType, EdgeType>{
//Each node contains unique data along with two lists of directed edges
  protected class Node {
      public NodeType data;
      public List<Edge> edgesLeaving = new LinkedList<>();
      public List<Edge> edgesEntering = new LinkedList<>();
      public Node(NodeType data) { this.data = data; }
  }
  // Nodes can be retrieved from this hashtable by their unique data
  protected Hashtable<NodeType,Node> nodes = new Hashtable();

  // Each edge contains data/weight, and two nodes that it connects
  protected class Edge {
      public EdgeType data; // the weight or cost of this edge
      public Node predecessor;
      public Node successor;
      public Edge(EdgeType data, Node pred, Node succ) {
          this.data = data;
          this.predecessor = pred;
          this.successor = succ;
      }
  }
  protected int edgeCount = 0;
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;
    public SearchNode(Node node, double cost, SearchNode predecessor) {
        this.node = node;
        this.cost = cost;
        this.predecessor = predecessor;
    }
    public int compareTo(SearchNode other) {
        if( cost > other.cost ) return +1;
        if( cost < other.cost ) return -1;
        return 0;
    }
}
  
  @Override
  /**
   * Insert a new node into the graph.
   * 
   * @param data is the data item stored in the new node
   * @return true if the data is unique and can be inserted into a new node,
   *         or false if this data is already in the graph
   * @throws NullPointerException if data is null
   */
  public boolean insertNode(NodeType data) {
    // TODO Auto-generated method stub
    if(nodes.containsKey(data)) return false; // throws NPE when data's null
    nodes.put(data,new Node(data));
    return true;
  }

  @Override
  /**
   * Remove a node from the graph.
   * And also remove all edges adjacent to that node.
   * 
   * @param data is the data item stored in the node to be removed
   * @return true if a vertex with data is found and removed, or
   *         false if that data value is not found in the graph
   * @throws NullPointerException if data is null
   */
  public boolean removeNode(NodeType data) {
    // remove this node from nodes collection
    if(!nodes.containsKey(data)) return false; // throws NPE when data==null
    Node oldNode = nodes.remove(data);
    // remove all edges entering neighboring nodes from this one
    for(Edge edge : oldNode.edgesLeaving)
        edge.successor.edgesEntering.remove(edge);
    // remove all edges leaving neighboring nodes toward this one
    for(Edge edge : oldNode.edgesEntering)
        edge.predecessor.edgesLeaving.remove(edge);
    return true;
  }

 @Override
  /**
   * Check whether the graph contains a node with the provided data.
   *
   * @param data the node contents to check for
   * @return true if data item is stored in a node within the graph, or
             false otherwise
   */
  public boolean containsNode(NodeType data) {
    // TODO Auto-generated method stub
    return nodes.containsKey(data);
  }

  @Override
  /**
   * Return the number of nodes in the graph
   *
   * @return the number of nodes in the graph
   */
  public int getNodeCount() {
    // TODO Auto-generated method stub
    return nodes.size();
  }

  @Override
  /**
   * Insert a new directed edge with positive edges weight into the graph.
   * Or if an edge between pred and succ already exists, update the data
   * stored in hat edge to be weight.
   *
   * @param pred is the data item contained in the new edge's predecesor node
   * @param succ is the data item contained in the new edge's successor node
   * @param weight is the non-negative data item stored in the new edge
   * @return true if the edge could be inserted or updated, or
   *         false if the pred or succ data are not found in any graph nodes
   */
  public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
    // find nodes associated with node data, and return false when not found
    Node predNode = nodes.get(pred);
    Node succNode = nodes.get(succ);
    if(predNode == null || succNode == null) return false;
    try {
        // when an edge alread exists within the graph, update its weight
        Edge existingEdge = getEdgeHelper(pred,succ);
        existingEdge.data = weight;
    } catch(NoSuchElementException e) {
        // otherwise create a new edges
        Edge newEdge = new Edge(weight,predNode,succNode);
        this.edgeCount++;
        // and insert it into each of its adjacent nodes' respective lists
        predNode.edgesLeaving.add(newEdge);
        succNode.edgesEntering.add(newEdge);
    }
    return true;
  }
  protected Edge getEdgeHelper(NodeType pred, NodeType succ) {
    Node predNode = nodes.get(pred);
    // search for edge through the predecessor's list of leaving edges
    for(Edge edge : predNode.edgesLeaving)
        // compare succ to the data in each leaving edge's successor
        if(edge.successor.data.equals(succ))
            return edge;
    // when no such edge can be found, throw NSE
    throw new NoSuchElementException("No edge from "+pred.toString()+" to "+
                                     succ.toString());
  }

  @Override
  /**
   * Remove an edge from the graph.
   *
   * @param pred the data item contained in the source node for the edge
   * @param succ the data item contained in the target node for the edge
   * @return true if the edge could be removed, or
   *         false if such an edge is not found in the graph
   */
  public boolean removeEdge(NodeType pred, NodeType succ) {
    // TODO Auto-generated method stub
      try {
        // when an edge exists
        Edge oldEdge = getEdgeHelper(pred,succ);
        // remove it from the edge lists of each adjacent node
        oldEdge.predecessor.edgesLeaving.remove(oldEdge);
        oldEdge.successor.edgesEntering.remove(oldEdge);
        // and decrement the edge count before removing
        this.edgeCount--;
        return true;
      } catch(NoSuchElementException e) {
        // when no such edge exists, return false instead
        return false;
      }
  }

  @Override
  /**
   * Check if edge is in the graph.
   *
   * @param pred the data item contained in the source node for the edge
   * @param succ the data item contained in the target node for the edge
   * @return true if the edge is found in the graph, or false other
   */
  public boolean containsEdge(NodeType pred, NodeType succ) {
    try { getEdgeHelper(pred,succ); return true; }
    catch(NoSuchElementException e) { return false; }
  }

@Override
  /**
   * Return the data associated with a specific edge.
   *
   * @param pred the data item contained in the source node for the edge
   * @param succ the data item contained in the target node for the edge
   * @return the non-negative data from the edge between those nodes
   * @throws NoSuchElementException if either node or the edge between them
   *         are not found within this graph
   */
  public EdgeType getEdge(NodeType pred, NodeType succ) {
    // TODO Auto-generated method stub
    return getEdgeHelper(pred,succ).data;
  }

  @Override
  /**
   * Return the number of edges in the graph.
   *
   * @return the number of edges in the graph
   */
  public int getEdgeCount() {
    // TODO Auto-generated method stub
    return this.edgeCount;
  }
  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest path between the provided start and end locations.  The
   * SearchNode that is returned by this method is represents the end of the
   * shortest path that is found: it's cost is the cost of that shortest path,
   * and the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // TODO: implement in step 6
    // create new start and end nodes
    Node startNode = new Node(start);
    Node endNode = new Node(end);
    // check if start and end nodes exist in the graph
    if (startNode == null || endNode == null || containsNode(start) == false || containsNode(end) == false) {
        throw new NoSuchElementException("Start or end node not found in graph.");
    }
    // create a hashtable to store search nodes for each node in the graph
    Hashtable<NodeType, SearchNode> searchNodes = new Hashtable<>();
    // initialize search nodes for each node in the graph with infinite cost and no predecessor
    for(Node node: nodes.values())
    {
        searchNodes.put(node.data, new SearchNode(node, Double.POSITIVE_INFINITY, null));
    }
    // initialize start search node with cost of 0
    SearchNode startSearchNode = searchNodes.get(start);
    if(startSearchNode != null)
    {
      startSearchNode.cost = 0;
    }
    // create a priority queue to store search nodes that have not yet been processed
    PriorityQueue<SearchNode> queue = new PriorityQueue();
    queue.add(startSearchNode);
    // loop until all reachable nodes have been processed
    while (!queue.isEmpty()) {
      SearchNode current = queue.poll();

      if (current.node.data.equals(end)) {
        // Found the end node
        return current;
      }
      // iterate over all edges leaving the current node and update the costs and predecessors of neighboring search nodes
      for (Edge edge : current.node.edgesLeaving) {
        Node neighborNode = edge.successor;
        SearchNode neighbor = searchNodes.get(neighborNode.data);

        double cost = current.cost + edge.data.doubleValue();
        if (cost < neighbor.cost) {
          neighbor.cost = cost;
          neighbor.predecessor = current;
          if (queue.contains(neighbor)) {
            queue.remove(neighbor);
          }
          queue.add(neighbor);
        }
      }
    }
    throw new NoSuchElementException("No path from start to end found.");
  }

  @Override
  /**
   * Returns the list of data values from nodes along the shortest path
   * from the node with the provided start value through the node with the
   * provided end value.  This list of data values starts with the start
   * value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path.  This
   * method uses Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the data item in the startingnode for the path
   * @param end the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    // Call computeShortestPath() to get the SearchNode that represents the shortest path from start to end
    SearchNode shortestPathNode = computeShortestPath(start, end);

    // Create a new linked list to hold the shortest path data
    List<NodeType> shortestPathData = new LinkedList<>();

    // Traverse the shortest path from end to start using the predecessors of each node
    while (shortestPathNode != null) {
      // Add each node's data to the beginning of the linked list
      shortestPathData.add(0, shortestPathNode.node.data);
      // Move to the predecessor of the current node
      shortestPathNode = shortestPathNode.predecessor;
    }

    // Return the linked list of shortest path data
    return shortestPathData;
  }

  @Override
  /**
   * Returns the cost of the path (sum over edge weights) of the shortest
   * path freom the node containing the start data to the node containing the
   * end data.  This method uses Dijkstra's shortest path algorithm to find
   * this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    SearchNode endSearchNode = computeShortestPath(start, end);
    return endSearchNode.cost;
  }

@Override
  /**
   * Returns a list of all airports in the flight graph.
   * @return A list of all airports in the flight graph.
   */
  public List<NodeType> getAirports() {
    // TODO Auto-generated method stub
    List<NodeType> airports = new ArrayList<>();
    // Traverse through all nodes in the graph and add the airport to the list
    for(FlightGraphAE<NodeType, EdgeType>.Node node: nodes.values())
    {
      airports.add(node.data);
    }
    return airports;
  }
  /**
   * Finds all possible routes between two nodes in the graph using depth-first search.
   * @param start the starting node
   * @param end the ending node
   * @return a list of all possible routes between the starting and ending nodes
   * @throws NoSuchElementException if no path from start to end is found
   */
  public List<List<NodeType>> getAllPaths(NodeType start, NodeType end) {
    // initialize an empty list to store all paths found
    List<List<NodeType>> allPaths = new ArrayList<>();
    // initialize an empty list to store the current path being explored
    List<NodeType> currentPath = new ArrayList<>();
    // add the starting node to the current path
    currentPath.add(start);
    // call the helper method to find all paths
    getAllPathsHelper(start, end, currentPath, allPaths);
    // if no path is found, throw a NoSuchElementException
    if (allPaths.isEmpty()) {
        throw new NoSuchElementException("No path from start to end found.");
    }
    return allPaths;
  }
  public List<List<NodeType>> getRoutes2(NodeType start, NodeType end) {
    // create a stack to keep track of the nodes to visit
    Deque<NodeType> stack = new ArrayDeque<>();
    // create a stack to keep track of the paths
    Deque<List<NodeType>> paths = new ArrayDeque<>();
    // create a set to keep track of visited nodes
    Set<NodeType> visited = new HashSet<>();
    // add the start node to the stack
    stack.push(start);
    // add the start node to the path
    List<NodeType> initialPath = new ArrayList<>();
    initialPath.add(start);
    paths.push(initialPath);
    // create a list to store all the paths found
    List<List<NodeType>> allPaths = new ArrayList<>();
    // iterate until the stack is empty
    while (!stack.isEmpty()) {
        // get the top node from the stack
        NodeType current = stack.pop();
        // get the top path from the path stack
        List<NodeType> currentPath = paths.pop();
        // mark the current node as visited
        visited.add(current);
        // if the current node is the end node, add the current path to the list of all paths
        if (current.equals(end)) {
            allPaths.add(currentPath);
        } else {
            // iterate over all the neighbors of the current node
            for (Edge edge : nodes.get(current).edgesLeaving) {
                NodeType neighbor = edge.successor.data;
                // check if the neighbor has not been visited
                if (!visited.contains(neighbor)) {
                    // add the neighbor to the stack
                    stack.push(neighbor);
                    // create a new path with the neighbor added to the end
                    List<NodeType> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    // add the new path to the path stack
                    paths.push(newPath);
                }
            }
        }
    }
    return allPaths;
}
  
  /**
   * A helper method to recursively find all possible paths between two nodes in the graph.
   * @param current the current node being explored
   * @param end the ending node
   * @param currentPath the current path being explored
   * @param allPaths a list to store all possible paths
   */
  private void getAllPathsHelper(NodeType current, NodeType destination, List<NodeType> currentPath, List<List<NodeType>> allPaths) {
    // if the current node is the ending node, add the current path to the list of all paths
    if (current.equals(destination)) {
        allPaths.add(new ArrayList<>(currentPath));
    } else {
        for (Edge edge : nodes.get(current).edgesLeaving) {
            // get the neighbor node
            NodeType neighbor = edge.successor.data;
            // check if the neighbor has already been explored
            if (!currentPath.contains(neighbor)) {
                // add the neighbor to the current path
                currentPath.add(neighbor);
                // recursively explore all possible paths from the neighbor
                getAllPathsHelper(neighbor, destination, currentPath, allPaths);
                // remove the neighbor from the current path once all possible paths from it have been explored
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }
  
  }
  /**
   * Finds all routes between a start node and an end node and returns a list of edges that comprise those routes.
   * @param start the starting node of the route
   * @param end the ending node of the route
   * @return a list of edges that comprise all routes between the start and end nodes
   */
  public List<EdgeType> getRoutes(NodeType start, NodeType end){
    List<List<NodeType>> allPaths = getRoutes2(start, end);
    // initialize an empty set to store all unique edges in all paths
    ArrayList<EdgeType> allEdges = new ArrayList<>();
    // iterate over each path found
    for (List<NodeType> path : allPaths) {
        // iterate over each node in the path
        for (int i = 0; i < path.size() - 1; i++) {
            // get the current node and the next node
            NodeType currentNode = path.get(i);
            NodeType nextNode = path.get(i + 1);
            // iterate over each edge leaving the current node
            for (Edge edge : nodes.get(currentNode).edgesLeaving) {
                // check if the edge leads to the next node
                if (edge.successor.data.equals(nextNode)) {
                    // add the edge to the set of all unique edges
                    allEdges.add(edge.data);
                    // break out of the inner loop since we've found the matching edge
                    break;
                }
            }
        }
    }
    // if no path is found, throw a NoSuchElementException
    if (allEdges.isEmpty()) {
        throw new NoSuchElementException("No path from start to end found.");
    }
    // return the set of all unique edges as a list
    return allEdges;
    
  }
 
  @Override
  /**
   * Returns the smallest list of nodes between the given origin and destination, after filtering out
   * any paths that contain the node toFilter.
   * @param origin the starting node
   * @param destination the ending node
   * @param toFilter the node to be filtered out from the paths
   * @return the smallest list of nodes between origin and destination that do not contain toFilter
   */
  public List<NodeType> filterWithoutAirport(NodeType origin, NodeType destination, NodeType toFilter) {
    // TODO Auto-generated method stub
    SearchNode shortestPathNode = filterWithoutAirportHelper(origin, destination, toFilter);

    // Create a new linked list to hold the shortest path data
    List<NodeType> shortestPathData = new LinkedList<>();

    // Traverse the shortest path from end to start using the predecessors of each node
    while (shortestPathNode != null) {
      // Add each node's data to the beginning of the linked list
      shortestPathData.add(0, shortestPathNode.node.data);
      // Move to the predecessor of the current node
      shortestPathNode = shortestPathNode.predecessor;
    }

    // Return the linked list of shortest path data
    return shortestPathData;
  }
  
  private SearchNode filterWithoutAirportHelper(NodeType origin, NodeType destination, NodeType toFilter) {
    Node startNode = new Node(origin);
    Node endNode = new Node(destination);
    // check if start and end nodes exist in the graph
    if (startNode == null || endNode == null || containsNode(origin) == false || containsNode(destination) == false) {
        throw new NoSuchElementException("Start or end node not found in graph.");
    }
    // create a hashtable to store search nodes for each node in the graph
    Hashtable<NodeType, SearchNode> searchNodes = new Hashtable<>();
    // initialize search nodes for each node in the graph with infinite cost and no predecessor
    for(Node node: nodes.values())
    {
      if(!node.data.equals(toFilter))
      {
        searchNodes.put(node.data, new SearchNode(node, Double.POSITIVE_INFINITY, null));
      }
    }
    // initialize start search node with cost of 0
    SearchNode startSearchNode = searchNodes.get(origin);
    if(startSearchNode != null)
    {
      startSearchNode.cost = 0;
    }
    // create a priority queue to store search nodes that have not yet been processed
    PriorityQueue<SearchNode> queue = new PriorityQueue();
    queue.add(startSearchNode);
    // loop until all reachable nodes have been processed
    while (!queue.isEmpty()) {
      SearchNode current = queue.poll();
  
      if (current.node.data.equals(destination)) {
        // Found the end node
        return current;
      }
      // iterate over all edges leaving the current node and update the costs and predecessors of neighboring search nodes
      for (Edge edge : current.node.edgesLeaving) {
        Node neighborNode = edge.successor;
        SearchNode neighbor = searchNodes.get(neighborNode.data);
        if(neighborNode.data.equals(toFilter))
        {
          continue;
        }
        double cost = current.cost + edge.data.doubleValue();
        if (cost < neighbor.cost) {
          neighbor.cost = cost;
          neighbor.predecessor = current;
          if (queue.contains(neighbor)) {
            queue.remove(neighbor);
          }
          queue.add(neighbor);
        }
      }
    }
    throw new NoSuchElementException("No path from start to end found.");
  }
  @Override
  /**
   * Returns the shortest route between origin and destination that includes
   * the given airport toFilter.
   *
   * @param origin the starting airport
   * @param destination the destination airport
   * @param toFilter the airport that must be included in the route
   * @return the shortest route between origin and destination that includes toFilter
   * @throws NoSuchElementException if there is no route that includes toFilter
   */
  public List<NodeType> filterWithAirport(NodeType origin, NodeType destination, NodeType toFilter) {
    // TODO Auto-generated method stub
    List<NodeType> startToFilter = new ArrayList<NodeType>();
    startToFilter = shortestPathData(origin, toFilter);
    
    List<NodeType> filterToEnd = new ArrayList<NodeType>();
    filterToEnd = shortestPathData(toFilter, destination);
    filterToEnd.remove(0);
    
    List<NodeType> shortestPath = new ArrayList<NodeType>();
    shortestPath.addAll(startToFilter);
    shortestPath.addAll(filterToEnd);
    
    return shortestPath;
  }

  public static void main(String[] args)
  {

  }
}

