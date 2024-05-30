runAlgorithmEngineerTests: AlgorithmEngineerTests.class junit5.jar
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java FlightGraphAE.class junit5.jar
	javac -cp junit5.jar:. AlgorithmEngineerTests.java

FlightGraphAE.class: FlightGraphAE.java
	javac FlightGraphAE.java

runBackendDeveloperTests: BackendDeveloperTests.class junit.jar
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java AirportBD.class FlightBackend.class FlightGraphBD.class RouteBD.class RouteReaderBD.class
	javac -cp junit5.jar:. BackendDeveloperTests.java

AirportBD.class: AirportBD.java
	javac AirportBD.java

FlightBackend.class: FlightBackend.java
	javac FlightBackend.java

FlightGraphBD.class: FlightGraphBD.java
	javac FlightGraphBD.java

RouteBD.class: RouteBD.java
	javac RouteBD.java

RouteReaderBD.class: RouteReaderBD.java
	javac RouteReaderBD.java	 
runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . --select-class=DataWranglerTests
DataWranglerTests.class: DataWranglerTests.java AirportDW.class RouteDW.class RouteReaderDW.class
	javac -cp .:junit5.jar DataWranglerTests.java
AirportDW.class: AirportDW.java AirportInterface.java
	javac AirportDW.java AirportInterface.java
RouteDW.class: RouteDW.java RouteInterface.java
	javac RouteDW.java RouteInterface.java
RouteReaderDW.class: RouteReaderDW.java RouteReaderInterface.java
	javac RouteReaderDW.java RouteReaderInterface.java

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java FlightGraphAE.class junit5.jar
	javac -cp junit5.jar:. AlgorithmEngineerTests.java

FlightGraphAE.class: FlightGraphAE.java
	javac FlightGraphAE.java

clean:
	rm *.class

runTests: FlightFrontendTests.class FlightFrontendInterface.class AlgorithmEngineerTests.class BackendDeveloperTests.class junit5.jar
	java -jar junit5.jar -cp . -c  AlgorithmEngineerTest
	java -jar junit5.jar -cp . -c BackendDeveloperTests
	java -jar junit5.jar -cp . --select-class=DataWranglerTests
	java -jar lib/junit5.jar -cp . --select-class=FlightFrontendTests

runFrontendTests: FlightFrontendTests.class FlightFrontendInterface.class
	javac --release 11 -cp .:lib/junit5.jar FlightFrontendTests.java
	java -jar lib/junit5.jar -cp . --select-class=FlightFrontendTests

FlightFrontendTests.class: FlightFrontendTests.java FlightFrontendInterface.class
	javac --release 11 -cp .:lib/junit5.jar FlightFrontendTests.java

FlightFrontendInterface.class: FlightFrontendInterface.java
	javac --release 11 FlightFrontendInterface.java
TextUITester.class: TextUITester.java
	javac TextUITester.java

run:
	echo "http://34.122.167.233/Project3/"
