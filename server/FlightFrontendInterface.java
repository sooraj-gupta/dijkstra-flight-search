package server;
public interface FlightFrontendInterface {
  // public FlightFrontendXX(FlightBackendInterface backend);

  public void loadData();

  public void searchByAirline(String airline);

  public  void search();

  public  void searchWithAirport(String airport);

  public void searchWithoutAirport(String airport);

  public void statistics();

  public void setDeparture(String departure);

  public void setDestination(String destination);

}
