import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester class that verifies the functionality of the Airport, Route, and RouteReader classes
 */
public class DataWranglerTests {

  /**
   * This tester method verifies the functionality of the construction and getter methods
   * of the Airport class.
   */
  @Test
  public void test1() {
    try {
      AirportInterface airport = new AirportDW("O'Hare", "Chicago", "USA");
      // check that the name returns correctly and that this airport is initialized
      // with 0 total routes
      assertEquals(airport.getName(), "O'Hare");
      assertEquals(airport.getRoutes().size(), 0);
    } catch (Exception e) {
      fail("An exception was incorrectly thrown");
    }
  }

  /**
   * This tester method verifies the functionality of the construction and getter methods
   * of the Route class.
   */
  @Test
  public void test2() {
    try {
      // create tester starting/ending airports and airline list
      AirportInterface airportStart = new AirportDW("O'Hare", "Chicago", "USA");
      AirportInterface airportEnd = new AirportDW("JFK", "New York", "USA");
      String airlines = "United";

      // try creating route object
      RouteInterface route = new RouteDW(airportStart, airportEnd, airlines, 300);

      // Check that the starting/ending airports, the airline list, and the distance are
      // all correctly initialized and returned in this Route instance
      assertEquals(route.getStartingLocation().getName(), "O'Hare");
      assertEquals(route.getEndingLocation().getName(), "JFK");
      assertEquals(route.getAirline(), "United");
      assertEquals(route.getDistance(), 300);

    } catch (Exception e) {
      fail("An exception was incorrectly thrown");
    }
  }

  /**
   * This tester method checks the RouteReader class and verifies that the correct
   * exception is thrown when trying to parse an illegal file
   */
  @Test
  public void test3() {
    RouteReaderInterface reader = new RouteReaderDW();

    // check that an exception is thrown for a file that does not exist
    Exception exception = assertThrows(FileNotFoundException.class, () -> {
      reader.readFromFile("abcd.gv");
    });

    // check that an exception is thrown for an empty file name
    exception = assertThrows(FileNotFoundException.class, () -> {
      reader.readFromFile("");
    });

    // check that an exception is thrown for a null file name
    exception = assertThrows(FileNotFoundException.class, () -> {
      reader.readFromFile(null);
    });
  }

  /**
   * This tester method checks the functionality of the RouteReader class and verifies that
   * the correct list of routes is returned from the valid file
   */
  @Test
  public void test4() {
    RouteReaderInterface reader = new RouteReaderDW();
    try {
      List<RouteInterface> list = reader.readFromFile("flights.gv");
      // check that the correct number of routes are added
      assertEquals(list.size(), 66934);

      // check that the first route in this list contains the proper references
      RouteInterface route = list.get(0);
      AirportInterface airportStart = new AirportDW("Rabah Bitat Airport", "Annaba", "Algeria");
      AirportInterface airportEnd = new AirportDW("Houari Boumediene Airport", "Algier", "Algeria");
      String airlines = "AH";

      // check that the correct starting/ending airport, airlines list, and distance are returned
      assertEquals(route.getStartingLocation().getName(), airportStart.getName());
      assertEquals(route.getEndingLocation().getName(), airportEnd.getName());
      assertEquals(route.getAirline(), airlines);
      assertEquals(route.getDistance(), 255.02129846932706);
    } catch (FileNotFoundException e) {
      fail("An exception was incorrectly thrown");
    }
  }

  /**
   * This tester method checks the functionality of the RouteReader class and verifies that
   * the airport references in the main route list are correctly initialized and contain their own
   * correct route lists
   */
  @Test
  public void test5() {
    RouteReaderInterface reader = new RouteReaderDW();
    try {
      List<RouteInterface> list = reader.readFromFile("flights.gv");

      // find the reference to Guangzhou Baiyun International Airport
      AirportInterface airport = list.get(10000).getStartingLocation();

      // check that this airport reference was initialized properly and returns the correct
      // routes list
      assertEquals(airport.getName(), "Guangzhou Baiyun International Airport");
      assertEquals(airport.getRoutes().size(), 335);

      // checks through every route in this route list and verifies that the starting location
      // is in fact this airport
      for (RouteInterface route : airport.getRoutes()) {
        assertEquals(route.getStartingLocation(), airport);
      }

    } catch (FileNotFoundException e) {
      fail("An exception was incorrectly thrown");
    }
  }

  /**
   * This tester method tests the fully integrated project and verifies that the correct route
   * is found and displayed when a departure and destination airport are inputted
   */
  @Test
  public void integrationTest1() {
    try {
      // initialize tester graph, reader, and backend
      String[] arguments = {"assets=/path/to/assets&departure=Middle Georgia Regional Airport&destination=Zagreb Airport"};
      FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
      RouteReaderInterface reader = new RouteReaderDW();
      FlightBackendInterface backend = new FlightBackend(graph, reader);
      TextUITester tester = new TextUITester("");

      // check that the route is correctly found and displayed
      backend.loadData("flights.gv");
      FlightFrontend frontend = new FlightFrontend(backend, arguments);
      frontend.search();
      String output = tester.checkOutput();
      //  System.out.println(output);
      assertTrue(output.contains("{\"route\" : \"Middle Georgia Regional Airport,Hartsfield Jackson Atlanta International Airport,ZÃ¼rich Airport,Zagreb Airport\"}"));
    } catch(Exception e) {
      fail("An exception was incorrectly thrown: " + e.getMessage());
    }
  }

  /**
   * This tester method tests the fully integrated project and verifies that the correct route
   * is found and displayed when a departure and destination airport are inputted and a filter
   * airport is also set
   */
  @Test
  public void integrationTest2() {
    try {
      // initialize tester graph, reader, and backend
      String[] arguments = {"assets=/path/to/assests&departure=Chicago Midway International Airport&destination=Zagreb Airport&include=Hartsfield Jackson Atlanta International Airport"};
      FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
      RouteReaderInterface reader = new RouteReaderDW();
      FlightBackendInterface backend = new FlightBackend(graph, reader);
      TextUITester tester = new TextUITester("");

      // check that the route is correctly found and displayed
      backend.loadData("flights.gv");
      FlightFrontend frontend = new FlightFrontend(backend, arguments);
      frontend.searchWithAirport("Hartsfield Jackson Atlanta International Airport");
      String output = tester.checkOutput();
      // System.out.println(output);
      assertTrue(output.contains("{\"route\" : \"Chicago Midway International Airport,Hartsfield Jackson Atlanta International Airport,ZÃ¼rich Airport,Zagreb Airport\"}"), output);
    } catch(Exception e) {
      fail("An exception was incorrectly thrown");
    }
  }

  /**
   * This tester method reviews the Algorithm Engineer's FlighGraph class and verifies that
   * the getAllPaths() method correctly retrieves all paths from two given nodes.
   */
  @Test
  public void codeReviewOfAlgorithmEngineer1() {
    // Create a tester FlightGraphAE instance
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    // Add test nodes to the graph
    graph.insertNode("Chicago");
    graph.insertNode("New York");
    graph.insertNode("Atlanta");
    graph.insertNode("Los Angeles");
    graph.insertNode("San Fransisco");
    graph.insertNode("Dallas");
    graph.insertNode("Philadelphia");
    graph.insertNode("Houston");
    graph.insertNode("Miami");
    graph.insertNode("Boston");

    // Add test edges to the graph
    graph.insertEdge("Atlanta", "Chicago", 7);
    graph.insertEdge("Chicago", "New York", 1);
    graph.insertEdge("Chicago", "Los Angeles", 5);
    graph.insertEdge("New York", "Los Angeles", 3);
    graph.insertEdge("Los Angeles", "Philadelphia", 3);
    graph.insertEdge("Los Angeles", "Dallas", 4);
    graph.insertEdge("Dallas", "San Fransisco", 9);
    graph.insertEdge("San Fransisco", "Boston", 7);
    graph.insertEdge("Atlanta", "Philadelphia", 2);
    graph.insertEdge("Chicago", "Boston", 8);
    graph.insertEdge("Miami", "New York", 6);
    graph.insertEdge("Miami", "Houston", 2);
    graph.insertEdge("Houston", "Atlanta", 1);
    graph.insertEdge("Houston", "Boston", 5);

    // Get all routes from node "Chicago" to node "Atlanta"
    List<List<String>> allRoutes = graph.getAllPaths("Chicago", "Los Angeles");
    String output = "";
    for(List<String> route: allRoutes)
    {
      for(String node: route)
      {
        output += node + ",";
      }
      output += "\n";
    }
    // Assert that the output string matches the expected output
    assertEquals(output, "Chicago,New York,Los Angeles,\n" + "Chicago,Los Angeles,\n");
  }

  /**
   *  This tester method reviews the Algorithm Engineer's FlighGraph class and verifies that
   *  the shortest path is correctly returned when a filter with airport is selected
   */
  @Test
  public void codeReviewOfAlgorithmEngineer2() {
    // Create a tester FlightGraphAE instance
    FlightGraphAE<String, Integer> graph = new FlightGraphAE<String, Integer>();
    // Add test nodes to the graph
    graph.insertNode("Chicago");
    graph.insertNode("New York");
    graph.insertNode("Atlanta");
    graph.insertNode("Los Angeles");
    graph.insertNode("San Fransisco");
    graph.insertNode("Dallas");
    graph.insertNode("Philadelphia");
    graph.insertNode("Houston");
    graph.insertNode("Miami");
    graph.insertNode("Boston");

    // Add test edges to the graph
    graph.insertEdge("Atlanta", "Chicago", 7);
    graph.insertEdge("Chicago", "New York", 1);
    graph.insertEdge("Chicago", "Los Angeles", 5);
    graph.insertEdge("New York", "Los Angeles", 3);
    graph.insertEdge("Los Angeles", "Philadelphia", 3);
    graph.insertEdge("Los Angeles", "Dallas", 4);
    graph.insertEdge("Dallas", "San Fransisco", 9);
    graph.insertEdge("San Fransisco", "Boston", 7);
    graph.insertEdge("Atlanta", "Philadelphia", 2);
    graph.insertEdge("Chicago", "Boston", 8);
    graph.insertEdge("Miami", "New York", 6);
    graph.insertEdge("Miami", "Houston", 2);
    graph.insertEdge("Houston", "Atlanta", 1);
    graph.insertEdge("Houston", "Boston", 5);

    // find the shortest path from Miami to New York but it has to go through Houston
    List<String> withFilterList = graph.filterWithAirport("Miami", "New York", "Houston");
    String output = "";
    for(String node: withFilterList)
    {
      output += node + ",";
    }
    assertEquals(output, "Miami,Houston,Atlanta,Chicago,New York,");
  }
}


