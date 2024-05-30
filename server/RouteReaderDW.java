import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class reads through a DOT file and stores a list of Routes and Airports that
 * correspond to each line of the file
 */
public class RouteReaderDW implements RouteReaderInterface{
  /**
   * This method reads each line from the specified file, and stores Airport
   * and Route objects with corresponding data into a list that is then returned.
   * @param filename contains the path and name of the data file that should be read from
   * @return List of routes from the file
   * @throws FileNotFoundException if this file cannot be read
   */
  @Override
  public List<RouteInterface> readFromFile(String filename) throws FileNotFoundException {
    // check that this is a valid file which can be read
    if (filename==null) throw new FileNotFoundException();
    
    File file = new File(filename);
    if (!file.exists()) {
      throw new FileNotFoundException();
    }
    

    //open the file and read the first line
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch(UnsupportedEncodingException e ) {
      throw new FileNotFoundException();
    }
    
    // use a scanner to read the file and store results into list of routes

    List<RouteInterface> routes = new ArrayList<RouteInterface>();

    // hashtable to efficiently store airport references based on their 3-letter codes
    Hashtable<String, AirportInterface> airports = new Hashtable<>();
    
    
    String line;
    try{
      while ((line = br.readLine()) != null) {
        
        // starting and ending lines of a .gv file that can be skipped
        if (line.contains("{") || line.contains("}")) {
          continue;
        }

        // use regex to come up with matches for either an airport reference or route reference
        // that is stored in the DOT
        Pattern airportPattern = Pattern.compile("([A-Za-zÀ-ȕ0-9]{3}) \\[label=\"(.*)\" city=\"?(.*)\"? country=\"?(.*)\"?]");
        Pattern routePattern = Pattern.compile("([A-Z]{3}) -> ([A-Z]{3}) \\[airlines=\"?(.*)\"? weight=\"?(.*)\"?]");
        Matcher matcher1 = airportPattern.matcher(line);
        Matcher matcher2 = routePattern.matcher(line);
        

        // if this line of the scanner provides information about an airport, add a new airport
        // reference with the respective attributes to the hashtable
        if (matcher1.find()) {
          airports.put(matcher1.group(1), new AirportDW(matcher1.group(2),matcher1.group(3),matcher1.group(4).replace("\"", "")));
        }


        // if this line of the scanner provides information about a route, add a new route
        // reference to the list
        if (matcher2.find()) {
          // find the starting & ending airport references in the hashtable based on the 3-letter codes
          AirportInterface startAirport = airports.get(matcher2.group(1));
          AirportInterface endAirport = airports.get(matcher2.group(2));

          // split the string that contains the airlines into a list of each airline on the route
          String[] strSplit = matcher2.group(3).replace("\"", "").split(",");
          ArrayList<String> airlines = new ArrayList<String>(Arrays.asList(strSplit));

          // convert the string that contains the distance into a double
          double weight = Double.MAX_VALUE;
          try {
            weight = Double.parseDouble(matcher2.group(4));
          } catch (Exception e) {}



          // add route instance to list
          for (String airline : airlines) {
            RouteInterface route = new Route(startAirport, endAirport, airline, weight);
            routes.add(route);
            // add this route to the starting airport's list of routes that leave its airport
            startAirport.getRoutes().add(route);
          }
        }

      }
      br.close();
    } catch (IOException e) {
      // e.printStackTrace();
    }

    return routes;
  }

}
