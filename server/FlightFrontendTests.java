import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests the FlightFrontend class.
 * 
 * @version 1.0
 */
public class FlightFrontendTests {
  @Test
  public void testSearch() {
    FlightBackendInterface backend = new FlightBackendFD(new RouteReaderFD());

    TextUITester tester = new TextUITester("");

    new FlightFrontendFD(backend, new String[] {"departure=San+Francisco+(SFO)&destination=New+York+(JFK)"});

    String output = tester.checkOutput();
    assertEquals(
        "{\"route\" : \"San Francisco (SFO),Charleston (CHS),Raleigh (RDU),New York (JFK)\"}\n",
        output);
  }

  @Test
  public void testSearchByAirline() {
    FlightBackendInterface backend = new FlightBackendFD(new RouteReaderFD());

    TextUITester tester = new TextUITester("");

    new FlightFrontendFD(backend, new String[] {
        "departure=San+Francisco+(SFO)&destination=New+York+(JFK)&airline=Delta+Air+Lines"});

    String output = tester.checkOutput();
    assertEquals("{\"route\" : \"San Francisco (SFO),Greenville (GSP),New York (JFK)\"}\n",
        output);
  }

  @Test
  public void testSearchWithAirport() {
    FlightBackendInterface backend = new FlightBackendFD(new RouteReaderFD());

    TextUITester tester = new TextUITester("");

    new FlightFrontendFD(backend, new String[] {
        "departure=San+Francisco+(SFO)&destination=New+York+(JFK)&include=Charleston+(CHS)"});

    String output = tester.checkOutput();
    assertEquals("{\"route\" : \"San Francisco (SFO),Charleston (CHS),New York (JFK)\"}\n",
        output);
  }

  @Test
  public void testSearchWithoutAirport() {
    FlightBackendInterface backend = new FlightBackendFD(new RouteReaderFD());

    TextUITester tester = new TextUITester("");

    new FlightFrontendFD(backend, new String[] {
        "departure=San+Francisco+(SFO)&destination=New+York+(JFK)&exclude=Philadelphia+(PHL)"});

    String output = tester.checkOutput();
    assertEquals("{\"route\" : \"San Francisco (SFO),Philadelphia (PHL),New York (JFK)\"}\n",
        output);
  }

  @Test
  public void testStatistics() {
    FlightBackendInterface backend = new FlightBackendFD(new RouteReaderFD());

    TextUITester tester = new TextUITester("");

    new FlightFrontendFD(backend, new String[] {"load=true"});

    String output = tester.checkOutput();
    boolean containsAirports = output.contains("\"Airports\":");
    boolean containsAirlines = output.contains("\"Airlines\":");
    boolean containsSFO = output.contains("San Francisco (SFO)");
    boolean containsLAX = output.contains("Los Angeles (LAX)");
    boolean containsJFK = output.contains("John F. Kennedy (JFK)");

    assertTrue(
        "containsAirports: " + containsAirports + "\ncontainsAirlines: " + containsAirlines
            + "\ncontainsSFO: " + containsSFO + "\ncontainsLAX: " + containsLAX + "\ncontainsJFK: "
            + containsJFK,
        containsAirports && containsAirlines && containsSFO && containsLAX && containsJFK);
  }

  @Test
  public void testSearchIntegration()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);

    TextUITester tester = new TextUITester("");

    new FlightFrontend(backend, new String[] {"assets=.&departure=San Francisco International Airport&destination=Indira Gandhi International Airport"});

    String output = tester.checkOutput();
    assertEquals(
        "{\"route\" : \"San Francisco International Airport,Zurich Airport,Indira Gandhi International Airport\"}\n",
        output);
  }

  @Test
  public void testSearchByAirlineIntegration()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);

    TextUITester tester = new TextUITester("");

    //testing wtih United Airlines
    new FlightFrontend(backend, new String[] {"assets=.&departure=San Francisco International Airport&destination=Dubai International Airport&airline=UA"});

    String output = tester.checkOutput();
    assertEquals(
        "{\"route\" : \"San Francisco International Airport,Dubai International Airport\"}\n",
        output);
  }

  @Test
  public void testSearchWithAirportIntegration()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);

    TextUITester tester = new TextUITester("");

    //testing wtih United Airlines
    new FlightFrontend(backend, new String[] {"assets=.&departure=San Francisco International Airport&destination=Dane County Regional Truax Field&include=Chicago O'Hare International Airport"});

    String output = tester.checkOutput();
    assertEquals(
        "{\"route\" : \"San Francisco International Airport,Chicago O'Hare International Airport,Dane County Regional Truax Field\"}\n",
        output);
  }

  @Test
  public void testSearchWithoutAirportIntegration()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);

    TextUITester tester = new TextUITester("");

    //testing wtih United Airlines
    new FlightFrontend(backend, new String[] {"assets=.&departure=San Francisco International Airport&destination=Dane County Regional Truax Field&exclude=Zurich Airport"});

    String output = tester.checkOutput();
    assertEquals(
        "{\"route\" : \"San Francisco International Airport,Lester B. Pearson International Airport,La Guardia Airport,Dane County Regional Truax Field\"}\n",
        output);
  }

  @Test
  public void testStatisticsIntegration()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);

    TextUITester tester = new TextUITester("");

    //testing wtih United Airlines
    new FlightFrontend(backend, new String[] {"assets=.&load=true"});

    String output = tester.checkOutput();
    String correctString = "{\"file\":\"airports_airlines.json\",\"num_airports\":\"3244\",\"num_airlines\":\"565\"}\n";

    assertEquals(correctString, output);
  }

  @Test
  public void testBackendLoadData()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);
    
    boolean thrown = false;
    try{
      backend.loadData("./flights.gv");
    }
    catch(Exception e)
    {
      thrown = true;
    }

    assertEquals(backend.getStatisticsString().split(",")[1], "3244");
    assertEquals(backend.getStatisticsString().split(",")[2], "565");
  }

  @Test
  public void testBackendFindShortestRoute()
  {
    FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
    RouteReaderInterface reader = new RouteReaderDW();
    FlightBackendInterface backend = new FlightBackend(graph, reader);
    
    boolean thrown = false;
    try{
      backend.loadData("./flights.gv");
    }
    catch(Exception e)
    {
      thrown = true;
    }

    assertEquals(backend.findShortestRoute("San Francisco International Airport", "Indira Gandhi International Airport").toString(), "[San Francisco International Airport, Zurich Airport, Indira Gandhi International Airport]");
  }



}
