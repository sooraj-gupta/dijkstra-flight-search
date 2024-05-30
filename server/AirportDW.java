import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Airport object that is a node in our larger graph. Each
 * airport contains a name and a list of routes that leave from this airport
 */
public class AirportDW implements AirportInterface {
  private String name;
  private List<RouteInterface> routes;

  /**
   * Constructor for the Airport object that initializes the name and routes list of the airport
   */
  public AirportDW(String name, String city, String country) {
    this.name = name;
    routes = new ArrayList<>();
  }

  /**
   * Returns the full name of the airport asa string
   * @return the full name of the airport
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * This method returns a list of all routes that leave from this airport
   * @return list of all routes that start at this airport
   */
  @Override
  public List<RouteInterface> getRoutes() {
    return routes;
  }
}
