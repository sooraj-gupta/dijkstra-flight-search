// --== CS400 File Header Information ==--
// Name:
// Email: 
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
public class Route extends Number implements RouteInterface {
 
  private AirportInterface start;
  private AirportInterface end;
  private String airline;
  private double distance;
  
  
  public Route(AirportInterface start, AirportInterface end, String airline, double distance) {
    this.start = start;
    this.end = end;
    this.airline = airline;
    this.distance = distance;
  }
  
  
  public AirportInterface getStartingLocation() {
    return start;
  }
  public AirportInterface getEndingLocation(){
    return end;
  }
  public String getAirline(){
    return airline;
  }
  public double getDistance(){
    return distance;
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