import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> vehicles = new ArrayList<>();
        Ferry ferry = new Ferry(0,vehicles);
        ferry.loadFilePuntos(args[0], vehicles);
        ferry = new Ferry(ferry.getBoatLenght(), vehicles);
        ferry.run();
        ferry.printData();
        ferry.printSolutionTable();
        ferry.printPossibleAssignation();
    }

}
