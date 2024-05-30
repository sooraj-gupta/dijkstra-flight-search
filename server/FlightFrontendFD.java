import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class FlightFrontendFD implements FlightFrontendInterface
{
  private FlightBackendInterface backend;
  private String filename = "";
  private String departure = "";
  private String destination = "";

  private boolean fileLoaded = false;

  public FlightFrontendFD( FlightBackendInterface backend, String[] args )
  {

    if( args.length != 1 )
    {
      System.out.println("Usage: java FlightFrontend <QUERY_STRING>");
      return;
    }

    this.backend = backend;
    

    // create a list of the attributes:
    LinkedList<String> attributesList = new LinkedList<String>();
		Hashtable<String, String> attributes = new Hashtable<String, String>();
    // new split the query string that the cgi script passes in as arg[0]
		for (String element : args[0].trim().split("&")) 
    {
      if( element.startsWith( "load" ) )
      {
        loadData();
        return;
      }
      // split the element into key and value
      String[] pair = element.split("=");
      pair[1] = pair[1].replaceAll("\\+", " ");
      // add the key and value to the list
      attributes.put(pair[0], pair[1]);
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
      search();
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
    System.out.println(formatRoute(backend.findShortestRoute(departure, destination)));
  }


  public void searchWithAirport(String airport) {
    System.out.println(formatRoute(backend.findShortestRouteWithAirport(departure, destination, airport)));
  }


  public void searchWithoutAirport(String airport) {
    System.out.println(formatRoute(backend.findShortestRouteWithoutAirport(departure, destination, airport)));
  }

  public void statistics() {
    String[] stats = backend.getStatisticsString().split(",");
    //format into json
    String json = "{";
    for( String stat : stats )
    {
      String key = stat.split(":")[0];
      String[] values = stat.split(":")[1].split(";");
      String actualValue = "[";
      for( int i = 0; i < values.length; i++ )
      {
        actualValue += "\"" + values[i] + "\"";
        if( i != values.length - 1 )
        {
          actualValue += ",";
        }
      }
      actualValue += "]";
      json += "\"" + key + "\":" + actualValue + ",";
    }
    json = json.substring(0, json.length() - 1);
    System.out.println(json + "}");
  }

  public static void main(String[] args) {
    new FlightFrontendFD(new FlightBackendFD(new RouteReaderFD()), args);
  }

  @Override
  public void loadData() {
    if( !fileLoaded )
    {
      try {
        backend.loadData("flights.txt");
        fileLoaded = true;
      } catch (Exception e) {    }
    }
    
    statistics();
  }
}