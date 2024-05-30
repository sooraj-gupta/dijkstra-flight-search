package server;
import java.util.ArrayList;
import java.util.List;

public class AirportFD implements AirportInterface {
  private String name;
  private List<RouteInterface> routes;
  
  public AirportFD(String name) {
    this.name = name;
    this.routes = new ArrayList<RouteInterface>();
  }
  
  public String getName() {
    return this.name;
  }
  
  public List<RouteInterface> getRoutes() {
    return this.routes;
  }
  
  public void addRoute(RouteInterface route) {
    this.routes.add(route);
  }
  
  public String toString() {
    return this.name;
  }

}
