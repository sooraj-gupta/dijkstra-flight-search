package server;
// --== CS400 File Header Information ==--
// Name: Vishal Singh Downdiyakhed
// Email: vsingh48@wisc.edu 
// Group and Team: BI Blue
// Group TA: Naman Gupta
// Lecturer: Dahl
// Notes to Grader: N/A
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AlgorithmEngineerTests {
  
  @Test
  /**
   * This method tests the FlightGraphAE class by creating a graph with 10 nodes and 14 edges,
   * then finds all routes from node "H" to node "E" using the getRoutes method and asserts that
   * the expected routes are returned.
   */
  void test1()
  {
    // Create a new instance of the FlightGraphAE class
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    // Add 10 nodes to the graph
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("G");
    graph.insertNode("D");
    graph.insertNode("M");
    graph.insertNode("E");
    graph.insertNode("F");
    
    // Add 14 edges to the graph
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "M", 5);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("M", "E", 3);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    
    // Get all routes from node "H" to node "E"
    List<List<String>> allRoutes = graph.getAllPaths("H", "E");
    String output = "";
    for(List<String> route: allRoutes) 
    {
      for(String node: route)
      {
        output += node;
      }
      output += "\n";
    }
    // Assert that the output string matches the expected output
    assertEquals(output, "HBME\n"
        + "HIDABME\n"
        + "HIDAME\n");
  }
  
  @Test
  /**
   * This method tests the filterWithAirport method of the FlightGraphAE class.
   */
  void test2()
  {
    // Create a new FlightGraphAE object
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("G");
    graph.insertNode("D");
    graph.insertNode("M");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "M", 5);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("M", "E", 3);
    graph.insertEdge("A", "E", 100);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    // Call the filterWithAirport method to get shortest route from H to E that pass through B
    List<String> withFilterList = graph.filterWithAirport("A", "E", "M");
    String output = "";
    for(String node: withFilterList)
    {
      output += node;
    }
    assertEquals(output, "ABME");
  }
  @Test
  /**
   * This method tests the filterWithoutAirport method of the FlightGraphAE class.
  */
  void test3()
  {
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("G");
    graph.insertNode("D");
    graph.insertNode("M");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "M", 5);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("M", "E", 3);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    // Call the filterWithoutAirport method to get shortest route from H to E that does not pass through B
    List<String> withoutFilterList = graph.filterWithoutAirport("A", "L", "H");
    String output = "";
    for(String node: withoutFilterList)
    {
      output += node;
    }
    assertEquals(output, "ABMFGL");
  }
  @Test
  /**
   * This method tests the getAirports method of the FlightGraphAE class.
   */
  void test4()
  {
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("G");
    graph.insertNode("D");
    graph.insertNode("M");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "M", 5);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("M", "E", 3);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    List<String> allNodes = graph.getAirports();
    String output = "";
    for(String node: allNodes)
    {
      output += node;
    }
    assertTrue(output.contains("A"));
    assertTrue(output.contains("B"));
    assertTrue(output.contains("H"));
    assertTrue(output.contains("I"));
    assertTrue(output.contains("L"));
    assertTrue(output.contains("G"));
    assertTrue(output.contains("D"));
    assertTrue(output.contains("M"));
    assertTrue(output.contains("E"));
    assertTrue(output.contains("F"));
  }
  @Test
  /**
   * This method tests if filterWithoutAirport throws a NoSuchElementExcpetion
  */
  void test5()
  {
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("G");
    graph.insertNode("D");
    graph.insertNode("M");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "M", 5);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("M", "E", 3);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    try {
      graph.filterWithoutAirport("E", "B", "M");
    }
    catch(Exception e)
    {
      boolean ifNoSuchElement = false;
      if(e instanceof NoSuchElementException)
      {
        ifNoSuchElement = true;
      }
      assertEquals(ifNoSuchElement, true);
    }
  }
  @Test
  /**
   * Performs an integration test for finding the shortest route between two airports by loading data from the file "flights.gv".
   */
  void integrationTest6()
  {
	  try {
      // initialize tester graph, reader, and backend
      String[] arguments = {"load=true&departure=Goroka Airport&destination=Copenhagen Kastrup Airport"};
      FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
      RouteReaderInterface reader = new RouteReaderDW();
      FlightBackendInterface backend = new FlightBackend(graph, reader);
      TextUITester tester = new TextUITester("");

      // check that the route is correctly found and displayed
      backend.loadData("flights.gv");
      FlightFrontend frontend = new FlightFrontend(backend, arguments);
      frontend.search();
      String output = tester.checkOutput();
      System.out.println(output);
      assertTrue(output.contains("{\"route\" : \"Goroka Airport,Port Moresby Jacksons International Airport,Singapore Changi Airport,Copenhagen Kastrup Airport\"}"));
    } catch(Exception e) {

    }
  } 
  @Test
  /**
   * Integration test to check the functionality of searching for the shortest route with a stopover at a specific airport.
   * Loads data from the flights.gv file and tests the searchWithAirport() method of the FlightFrontend class.
   */
  void integrationTest7()
  {
	try
    	{
    		String[] arr = {"load=true&departure=Goroka Airport&destination=Copenhagen Kastrup Airport&include=Nadzab Airport"};
    		FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    		RouteReaderInterface reader = new RouteReaderDW();
   		 FlightBackendInterface backend = new FlightBackend(graph, reader);
    		TextUITester tester = new TextUITester("");
    		backend.loadData("flights.gv");
    		FlightFrontend frontend = new FlightFrontend(backend, arr);
    		frontend.searchWithAirport("Nadzab Airport");
    		String output = tester.checkOutput();
    		System.out.println(output);
    		assertTrue(output.contains("{\"route\" : \"Goroka Airport,Nadzab Airport,Tokua Airport,Cairns International Airport,Sydney Kingsford Smith International Airport,Vancouver International Airport,Lester B. Pearson International Airport,Copenhagen Kastrup Airport\"}"));
    	}
    	catch(Exception e)
    	{
      
    	}
  }	
  @Test
  /**
   * Tests the functionality of reading a file using the RouteReaderDW class.
   * Throws a FileNotFoundException if the specified file is not found.
   */
  void codeReviewOfDataWrangler1()
  {
    RouteReaderInterface reader = new RouteReaderDW();
    boolean isFileNotFound = false;
    try {
      reader.readFromFile("notafile.txt");
    } catch (FileNotFoundException e) {
      if(e instanceof FileNotFoundException)
      {
        isFileNotFound = true;
      }
    }
    assertTrue(isFileNotFound);
  }
  @Test
  /**
   * This method tests the functionality of the RouteReaderDW class by attempting to read data from the "flights.gv" file.
   * It then checks that the number of routes returned is equal to the expected number of routes.
   */
  void codeReviewOfDataWrangler2()
  {
    RouteReaderInterface reader = new RouteReaderDW();
    List<RouteInterface> routes = new ArrayList<RouteInterface>();
    try {
      routes = reader.readFromFile("flights.gv");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(routes.size(), 66934);
  }
}

