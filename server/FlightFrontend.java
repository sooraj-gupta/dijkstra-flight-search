import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class FlightFrontend implements FlightFrontendInterface
{
  private FlightBackendInterface backend;
  private String stats = "";
  private static String assetsPath = "";
  private String departure = "";
  private String destination = "";

  public static void send(String string)
  {
    System.out.println("{ \"text\":\"" + string + "\"}");
  }

  public static String getAssetsPath()
  { 
    return assetsPath;
  }

  public FlightFrontend( FlightBackendInterface backend, String[] args )
  {

    if( args.length != 1 || !args[0].startsWith("assets=") )
    {
      System.out.println("Usage: java FlightFrontend \"assets=/path/to/assets&departure=Airport 1&destination=Airport 2\"");
      return;
    }

    this.backend = backend;


    // create a list of the attributes:
    LinkedList<String> attributesList = new LinkedList<String>();
		Hashtable<String, String> attributes = new Hashtable<String, String>();
    // new split the query string that the cgi script passes in as arg[0]
		for (String element : args[0].trim().split("&")) 
    {
      if( element.startsWith( "assets=" ) )
      {
        assetsPath = element.split("=")[1];
        loadData();
      }
      if( element.startsWith( "load=" ) )
      {
        statistics();
        return;
      }
      // split the element into key and value
      String[] pair = element.split("=");
      // add the key and value to the list
      attributes.put(pair[0], decode(pair[1]));
      attributesList.add(pair[0]);

    }

    setDeparture(attributes.get("departure"));
    setDestination(attributes.get("destination"));


    if( attributes.get("airline") != null )
    {
      searchByAirline(attributes.get("airline"));
    }
    else if( attributes.get("exclude") != null )
    {
      searchWithoutAirport(attributes.get("exclude"));
    }
    else if( attributes.get("include") != null )
    {
      searchWithAirport(attributes.get("include"));
    }
    else
    {
      // System.out.println("{ \"attributes\":\"" + attributes.toString() + "\"}"); 
      search();
    }
  }

  private String decode(String value) {
    try {
      return java.net.URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    } catch (Exception e) { 
      return value;
    }
  }
  
  public void setDeparture( String departure )
  {
    this.departure = departure;
  }
  public void setDestination( String destination )
  {
    this.destination = destination;
  }

  private static String formatRoute(List<String> route) {
    if( route == null || route.size() == 0 )
    {
      return "{\"route\" : \"\"}";
    }
    String output = "{\"route\" : \"";
    for( String airport : route )
    {
      output += airport + ",";
    }
    output = output.substring(0, output.length() - 1);
    output += "\"}";
    return output;
  }

  public void searchByAirline(String airline) {
    System.out.println(formatRoute(backend.findShortestRouteForAirline(departure, destination, airline)));
  }

  public void search()
  {    
    // System.out.println(formatRoute(backend.findShortestRoute(departure, destination)));
    System.out.println(formatRoute(backend.findShortestRoute(departure, destination)));
  }


  public void searchWithAirport(String airport) {
    System.out.println(formatRoute(backend.findShortestRouteWithAirport(departure, destination, airport)));
  }


  public void searchWithoutAirport(String airport) {
    System.out.println(formatRoute(backend.findShortestRouteWithoutAirport(departure, destination, airport)));
  }

  public void statistics() {
    String[] statsArr = stats.split(",");
    System.out.println("{\"file\":\"" + statsArr[0] + "\",\"num_airports\":\"" + statsArr[1] + "\",\"num_airlines\":\"" + statsArr[2]  + "\"}");  
  }

  public static void main(String[] args) {
   FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
   RouteReaderInterface reader = new RouteReaderDW();
   FlightBackendInterface backend = new FlightBackend(graph, reader);
   new FlightFrontend(backend, args);
    
  }
  @Override
  public void loadData() {
    try {
      backend.loadData(assetsPath + "/flights.gv");
      // System.out.print(assetsPath + "/flights.gv");
      stats = backend.getStatisticsString();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}
