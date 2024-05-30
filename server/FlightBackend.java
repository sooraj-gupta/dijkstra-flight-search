// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None

// import necessary libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class FlightBackend implements FlightBackendInterface {

  private FlightGraphInterface<AirportInterface, Route> graph;
  private RouteReaderInterface routeReader;
  public Hashtable<String, AirportInterface> airports = new Hashtable<String, AirportInterface>();
  private Set<String> airlines = new HashSet<>();

  // Constructor
  public FlightBackend(FlightGraphInterface<AirportInterface, Route> graph,
      RouteReaderInterface routeReader) {
    this.graph = graph;
    this.routeReader = routeReader;
  }

  // This method loads data from the input file
  public void loadData(String filename) throws FileNotFoundException {
    // create a new instance of the RouteReader class
    routeReader = new RouteReaderDW();

    // read the routes from the file
    List<RouteInterface> routes = routeReader.readFromFile(filename);

    // create a new instance of the FlightGraph class
    graph = new FlightGraphAE<AirportInterface, Route>();

    // loop through each route and add nodes and edges to the graph
    for (RouteInterface route : routes) {
      // add airline to the airlines set

      airlines.add(route.getAirline());

      // check if starting and ending airports already exist in the hashtable
      if (!airports.containsKey(route.getStartingLocation().getName())) {
        // if not, insert a new node in the graph and add the airport to the hashtable
        graph.insertNode(route.getStartingLocation());
        airports.put(route.getStartingLocation().getName(), route.getStartingLocation());
      }
      if (!airports.containsKey(route.getEndingLocation().getName())) {
        // if not, insert a new node in the graph and add the airport to the hashtable
        graph.insertNode(route.getEndingLocation());
        airports.put(route.getEndingLocation().getName(), route.getEndingLocation());
      }

      // add the edge to the graph
      graph.insertEdge(route.getStartingLocation(), route.getEndingLocation(), (Route) route);
    }
  }

  // This method finds the shortest path between the departure and destination airports
  public List<String> findShortestRoute(String departure, String destination) {
    List<String> shortestPath = new ArrayList<String>();
    // check if the departure and destination airports exist in the hashtable
    if (!airports.containsKey(departure) || !airports.containsKey(destination)) {
      // if not, return an empty list
      return shortestPath;
    }
    AirportInterface start = airports.get(departure);
    AirportInterface end = airports.get(destination);

    // find the shortest path between the two airports using Dijkstra's algorithm
    try {
      List<AirportInterface> path = graph.shortestPathData(start, end);
      // convert the list of airports to a list of airport names
      if (path != null) {
        for (AirportInterface airport : path) {
          shortestPath.add(airport.getName());
        }
      }
      return shortestPath;
    } catch (NoSuchElementException e) {
      return shortestPath;
    }
  }

  public List<String> findShortestRouteForAirline(String departure, String destination,
      String airline) {
    List<String> shortestPath = new ArrayList<String>(); // create an empty list to store the
                                                         // shortest path

    // check if departure and destination airports exist in the graph
    if (!airports.containsKey(departure) || !airports.containsKey(destination)) {
      return shortestPath; // if either airport does not exist, return the empty list
    }

    // get the start and end airports from the graph
    AirportInterface start = airports.get(departure);
    AirportInterface end = airports.get(destination);

    // find the shortest path between the start and end airports in the graph
    List<AirportInterface> path = graph.shortestPathData(start, end);

    // if a path exists, check if the airline is present on all the routes in the path
    if (path != null) {
      for (int i = 0; i < path.size() - 1; i++) {
        AirportInterface from = path.get(i);
        AirportInterface to = path.get(i + 1);

        // get all the routes between the current two airports in the path
        List<Route> routes = graph.getRoutes(from, to);
        boolean airlineFound = false;

        // check if the desired airline is present on any of the routes between the current two
        // airports
        for (RouteInterface route : routes) {
          if (route.getAirline().equals(airline)) {
            airlineFound = true;
            break;
          }
        }

        // if the airline is not present on any of the routes between the two airports, return an
        // empty list
        if (!airlineFound) {
          return new ArrayList<String>();
        }
      }

      // if the airline is present on all routes in the path, add the name of each airport in the
      // path to the shortestPath list
      for (AirportInterface airport : path) {
        shortestPath.add(airport.getName());
      }
    }

    // return the shortestPath list
    return shortestPath;
  }

  public List<String> findShortestRouteWithAirport(String departure, String destination,
      String airport) {
    List<String> shortestPath = new ArrayList<String>();

    // check if departure and destination airports exist in the graph
    if (!airports.containsKey(departure) || !airports.containsKey(destination)) {
      return shortestPath; // if either airport does not exist, return the empty list
    }

    // get the start and end airports from the graph and the airport to filter by
    AirportInterface start = airports.get(departure);
    AirportInterface end = airports.get(destination);
    AirportInterface with = airports.get(airport);

    // find the shortest path between the start and end airports in the graph, filtering by the
    // desired airport
    List<AirportInterface> path = graph.filterWithAirport(start, end, with);

    // if a path exists, add the name of each airport in the path to the shortestPath list
    if (path != null) {
      for (AirportInterface port : path) {
        shortestPath.add(port.getName());
      }
    }

    // return the shortestPath list
    return shortestPath;
  }

  public List<String> findShortestRouteWithoutAirport(String departure, String destination,
      String airport) {
    // Initialize an empty list to store the shortest path
    List<String> shortestPath = new ArrayList<String>();

    // Check if the departure and destination airports exist in the graph
    if (!airports.containsKey(departure) || !airports.containsKey(destination)) {
      return shortestPath;
    }

    // Get the corresponding AirportInterfaceBD object for each airport
    AirportInterface start = airports.get(departure);
    AirportInterface end = airports.get(destination);
    AirportInterface without = airports.get(airport);

    // Find the shortest path between start and end airports, without using the specified airport
    List<AirportInterface> path = graph.filterWithoutAirport(start, end, without);

    // If a valid path is found, add the name of each airport in the path to the shortestPath list
    if (path != null) {
      for (AirportInterface port : path) {
        shortestPath.add(port.getName());
      }
    }

    // Return the list of airport names representing the shortest path without using the specified
    // airport
    return shortestPath;
  }

  public String getStatisticsString() {

    File airportsAndAirlinesJSON =
        new File((FlightFrontend.getAssetsPath().equals("") ? "." : FlightFrontend.getAssetsPath())
            + "/airports_airlines.json");


    try (FileWriter airportsAndAirlinesWriter = new FileWriter(airportsAndAirlinesJSON)) {
      airportsAndAirlinesWriter.write("{\"Airports\": [");

      // Loop through each airport in the airports map and append its name to the allairports
      // string
      int i = 0;
      int size = airports.size();
      for (String airport : airports.keySet()) {
        airportsAndAirlinesWriter.write("\"" + airport + "\"");
        if (i++ < size - 1) {
          airportsAndAirlinesWriter.write(", ");
        }
      }

      airportsAndAirlinesWriter.write("], \"Airlines\": [");

      // Loop through each airline in the airlines set and append its name to the file
      i = 0;
      size = airlines.size();
      for (String airline : airlines) {
        airportsAndAirlinesWriter.write("\"" + airline + "\"");
        if (i++ < size - 1) {
          airportsAndAirlinesWriter.write(", ");
        }
      }

      airportsAndAirlinesWriter.write("]}");

    } catch (IOException e) {
      e.printStackTrace();
    }


    // Return the filename of the JSON file that was written to
    return "airports_airlines.json," + airports.size() + "," + airlines.size();
  }



}
