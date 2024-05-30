package server;
// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
public class RouteBD extends Number implements RouteInterfaceBD {
 
  private AirportInterfaceBD start;
  private AirportInterfaceBD end;
  private String airline;
  private double distance;
  
  
  public RouteBD(AirportInterfaceBD start, AirportInterfaceBD end, String airline, double distance) {
    this.start = start;
    this.end = end;
    this.airline = airline;
    this.distance = distance;
  }
  
  
  public AirportInterfaceBD getStartingLocation() {
    return start;
  }
  public AirportInterfaceBD getEndingLocation(){
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