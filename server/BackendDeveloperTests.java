package server;
// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class BackendDeveloperTests {
//
//  @Test
//  void LoadDataTest() {
//    FlightGraphAE graph = new FlightGraphAE();
//    RouteReaderDW reader = new RouteReaderDW();
//    FlightBackend backend = new FlightBackend(graph, reader);
//      
////      try {
////        backend.loadData("data.txt");
////      } catch (FileNotFoundException e) {
////        // TODO Auto-generated catch block
////        e.printStackTrace();
////      }
////      
////      
//  }
//  
//  @Test 
//  void TestFilters() {
//    FlightGraphAE graph = new FlightGraphAE();
//    RouteReaderDW reader = new RouteReaderDW();
//    FlightBackend backend = new FlightBackend(graph, reader);
////    try {
//////      backend.loadData("data.txt");
////    } catch (FileNotFoundException e) {
////      // TODO Auto-generated catch block
////      e.printStackTrace();
////      
////    }
////    System.out.println(backend.airports.toString());
//    
////    assertEquals("Airports:null Delhi; Bangalore;Mumbai;Airlines:[ SS,  Air India,  New Balance]", backend.getStatisticsString());
//    
//    
//  }
//  
//  @SuppressWarnings("unchecked")
//  @Test
//  void TestingShortestPathAndFilter() {
////    FlightGraphAE graph = new FlightGraphAE();
////    RouteReaderDW reader = new RouteReaderDW();
////    FlightBackend backend = new FlightBackend(graph, reader);
////    
////    try {
////      backend.loadData("data.txt");
////      
////    } catch (FileNotFoundException e) {
////      // TODO Auto-generated catch block
////      e.printStackTrace();
////      
////    }
////    System.out.println(backend.airports.toString());
////    
////    System.out.println(backend.findShortestRoute("Mumbai", "Bangalore"));
////    
//  }
//  
  @Test
  void CodeReviewOfFrontendDeveloper1(){

  }
  
  @Test
  void CodeReviewOfFrontendDeveloper2(){

  }
  
  @Test
  void Integration1() {
    try
    {
        String[] arr = {"load=true&departure=General Edward Lawrence Logan International Airport&destination=Dallas Love Field"};
        FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
        RouteReaderInterface reader = new RouteReaderDW();
     FlightBackendInterface backend = new FlightBackend(graph, reader);
        TextUITester tester = new TextUITester("");
        backend.loadData("flights.gv");
        FlightFrontend frontend = new FlightFrontend(backend, arr);
        frontend.search();
        String output = tester.checkOutput();
        System.out.println(output);
        //boston -> La Guardia Airport ->Bill & Hillary Clinton National Airport/Adams Field
        
      assertTrue(output.contains("{\"route\" : \"General Edward Lawrence Logan International Airport,La Guardia Airport,Bill & Hillary Clinton National Airport/Adams Field,Dallas Love Field\"}"));
    }
    catch(Exception e)
    {
  
    }
  }
  

  @Test
  void Integration2() {
    try
    {
        String[] arr = {"load=true&departure=General Edward Lawrence Logan International Airport&destination=Dallas Love Field&include=La Guardia Airport"};
        FlightGraphInterface<AirportInterface, Route> graph = new FlightGraphAE<AirportInterface, Route>();
        RouteReaderInterface reader = new RouteReaderDW();
     FlightBackendInterface backend = new FlightBackend(graph, reader);
        TextUITester tester = new TextUITester("");
        backend.loadData("flights.gv");
        FlightFrontend frontend = new FlightFrontend(backend, arr);
        frontend.searchWithAirport("La Guardia Airport");
        String output = tester.checkOutput();
        System.out.println(output);
        //boston -> La Guardia Airport ->Bill & Hillary Clinton National Airport/Adams Field
        
      assertTrue(output.contains("{\"route\" : \"General Edward Lawrence Logan International Airport,La Guardia Airport,Bill & Hillary Clinton National Airport/Adams Field,Dallas Love Field\"}"));
    }
    catch(Exception e)
    {
  
    }
  }
  

}
