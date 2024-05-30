// --== CS400 File Header Information ==--
// Name:Kyran
// Email: ksinha22@gmail.com
// Group and Team: BL blue
// Group TA: Naman
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.io.FileNotFoundException;
import java.util.List;

public interface RouteReaderInterfaceBD {
public List<RouteInterfaceBD> readFromFile(String filename) throws FileNotFoundException;
}
