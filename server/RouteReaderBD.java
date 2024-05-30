// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class RouteReaderBD implements RouteReaderInterfaceBD{

 
  public List<RouteInterfaceBD> readFromFile(String filename) throws FileNotFoundException {
   List<RouteInterfaceBD> routes = new ArrayList<RouteInterfaceBD>();
   System.out.println("Before scanner");
   Scanner scanner = new Scanner(new File(filename));
   System.out.println("After scanner");
   String[] fields;
   while(scanner.hasNextLine()) {
     fields = scanner.nextLine().split(",");
     routes.add(new RouteBD(new AirportBD(fields[0], null), new AirportBD(fields[1], null), fields[2], Double.parseDouble(fields[3])));
   }
   scanner.close();
   return routes;
  
}}