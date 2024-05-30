// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.FileNotFoundException;
import java.util.List;

public interface FlightBackendInterface{
  // public BackendXX( DijkstraInterface<String, > AirportInterface destination)

  public void loadData(String filename) throws FileNotFoundException;

  public List<String> findShortestRoute(String departure, String destination);

  public List<String> findShortestRouteForAirline(String departure, String destination,
      String airline);

  public List<String> findShortestRouteWithAirport(String departure, String destination,
      String airport);

  public List<String> findShortestRouteWithoutAirport(String departure, String destination,
      String airport);

  public String getStatisticsString();
}
