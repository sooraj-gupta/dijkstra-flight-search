package server;
/**
 * This class represents a flight Route object that is an edge in our larger graph. Each route
 * contains references to the starting and ending airports of the route, the airlines
 * that run this route, and the distance (weight) of the route
 */
public class RouteDW extends Number implements RouteInterface{
  private AirportInterface startingLoc;
  private AirportInterface endingLoc;
  private String airlines;
  private double distance;

  /**
   * Constructor for a route object that initializes the starting/ending airports, the list
   * of airlines, and the distance
   */
  public RouteDW(AirportInterface startingLoc, AirportInterface endingLoc, String airlines, double distance) {
    this.startingLoc = startingLoc;
    this.endingLoc = endingLoc;
    this.airlines = airlines;
    this.distance = distance;
  }

  /**
   * Returns the reference to the starting location of the route
   * @return the starting airport of this route
   */
  @Override
  public AirportInterface getStartingLocation() {
    return startingLoc;
  }

  /**
   * Returns the reference to the ending location of the route
   * @return the ending airport of this route
   */
  @Override
  public AirportInterface getEndingLocation() {
    return endingLoc;
  }

  /**
   * Returns the list of airlines that run this route
   * @return the list of airlines that run this route
   */
  @Override
  public String getAirline() {
    return airlines;
  }

  /**
   * Returns the distance across this route
   * @return the distance (weight) of this route
   */
  @Override
  public double getDistance() {
    return distance;
  }

  /**
   * Formats the attributes of this route into a String
   * @return a string representation of this route
   */
  @Override
  public String toString() {
    return startingLoc.getName() + " to " + endingLoc.getName() + " (Airlines: " + airlines + ", Distance: " + distance + ")";
  }

  @Override
  public int intValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long longValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public float floatValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double doubleValue() {
    // TODO Auto-generated method stub
    return 0;
  }
}

