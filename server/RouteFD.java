package server;
public class RouteFD extends Number implements RouteInterface {
  
  private double distance;
  private AirportInterface startingLocation;
  private AirportInterface endingLocation;
  private String airline;

  public RouteFD(AirportInterface startingLocation, AirportInterface endingLocation, String airline, double distance)
  {
    this.startingLocation = startingLocation;
    this.endingLocation = endingLocation;
    this.airline = airline;
    this.distance = distance;
  }

  @Override
  public AirportInterface getStartingLocation() {
    
    return startingLocation;
    
  }

  @Override
  public AirportInterface getEndingLocation() {
    return endingLocation;
  }

  @Override
  public String getAirline() {
    return airline;
  }

  @Override
  public int intValue() {
    // TODO Auto-generated method stub
    return (int) distance;
  }

  @Override
  public long longValue() {
    // TODO Auto-generated method stub
    return (long) distance;
  }

  @Override
  public float floatValue() {
    // TODO Auto-generated method stub
    return (float) distance;
  }

  @Override
  public double doubleValue() {
    // TODO Auto-generated method stub
    return distance;
  }

  @Override
  public double getDistance() {
    // TODO Auto-generated method stub
    return doubleValue();
  }
  
}
