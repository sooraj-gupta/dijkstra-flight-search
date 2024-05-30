import java.io.FileNotFoundException;
import java.util.List;

public class RouteReaderFD implements RouteReaderInterface{

  @Override
  public List<RouteInterface> readFromFile(String filename) throws FileNotFoundException {
    return new java.util.ArrayList<RouteInterface>();
  }


}
