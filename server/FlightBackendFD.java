package server;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;

public class FlightBackendFD implements FlightBackendInterface {
  private FlightGraphInterface<AirportInterface, RouteFD> graph;
  private RouteReaderInterface reader;

  public FlightBackendFD(RouteReaderInterface reader) {
    this.reader = reader;
    this.graph = new FlightGraphFD<AirportInterface, RouteFD>();
  }


  @Override
  public void loadData(String filename) throws FileNotFoundException {
    
  }


  @Override
  public List<String> findShortestRoute(String departure, String destination) {
    List<String> route = new java.util.ArrayList<String>();
    route.add(departure);
    route.add("Charleston (CHS)");
    route.add("Raleigh (RDU)");
    route.add(destination);
    return route;
  }

  @Override
  public List<String> findShortestRouteForAirline(String departure, String destination,
      String airline) {
    List<String> route = new java.util.ArrayList<String>();
    route.add(departure);
    route.add("Greenville (GSP)");
    route.add(destination);
    return route;
  }

  @Override
  public List<String> findShortestRouteWithAirport(String departure, String destination,
      String airport) {
    List<String> route = new java.util.ArrayList<String>();
    route.add(departure);
    route.add(airport);
    route.add(destination);
    return route;

  }

  @Override
  public List<String> findShortestRouteWithoutAirport(String departure, String destination,
      String airport) {
    List<String> route = new java.util.ArrayList<String>();
    route.add(departure);
    route.add("Philadelphia (PHL)");
    route.add(destination);
    return route;
  }

  @Override
  public String getStatisticsString() {
    // Placeholder code:
    return "Airports:San Francisco (SFO);Los Angeles (LAX);"
        + "John F. Kennedy (JFK);Chicago (ORD);Denver (DEN);Dallas (DFW);"
        + "Newark (EWR);Seattle (SEA);Washington (DCA);Boston (BOS);"
        + "Atlanta (ATL);Detroit (DTW);Minneapolis (MSP);Charlotte (CLT);"
        + "Houston (IAH);Phoenix (PHX);Philadelphia (PHL);Miami (MIA);"
        + "New York (LGA);Las Vegas (LAS);Portland (PDX);San Diego (SAN);"
        + "Orlando (MCO);Tampa (TPA);Salt Lake City (SLC);New Orleans (MSY);"
        + "Pittsburgh (PIT);Cleveland (CLE);Baltimore (BWI);Sacramento (SMF);"
        + "Kansas City (MCI);St. Louis (STL);Cincinnati (CVG);Indianapolis (IND);"
        + "Nashville (BNA);Milwaukee (MKE);Raleigh (RDU);Memphis (MEM);Honolulu (HNL);"
        + "Tucson (TUS);Oakland (OAK);Providence (PVD);Richmond (RIC);"
        + "Greensboro (GSO);Albuquerque (ABQ);Oklahoma City (OKC);"
        + "Omaha (OMA);Buffalo (BUF);Dayton (DAY);Rochester (ROC);"
        + "Spokane (GEG);Charleston (CHS);Columbus (CMH);Wichita (ICT);"
        + "Greenville (GSP);Tulsa (TUL);Ft. Lauderdale (FLL);Ft. Myers (RSW),"
        + "Airlines:American Airlines;Delta Air Lines;United Airlines;"
        + "Southwest Airlines;JetBlue Airways;Alaska Airlines;"
        + "Frontier Airlines;Spirit Airlines;Hawaiian Airlines;"
        + "Virgin America";
  }

}


