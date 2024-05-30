// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.util.List;

public class AirportBD implements AirportInterfaceBD{
    public String name;
    public List<RouteInterfaceBD> routes;
    
    public AirportBD(String name, List<RouteInterfaceBD>routes){
      this.name = name;
      this.routes = routes;
    }
    public String getName() {
      return name;
    }
    public List<RouteInterfaceBD> getRoutes(){
      return routes;
    }
    
    
}
