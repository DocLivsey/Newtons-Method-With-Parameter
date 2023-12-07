import java.io.FileNotFoundException;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String ERROR = "\u001B[31m"; // RED
    public static final String INPUT = "\u001B[32m"; // GREEN
    public static final String COMMENT = "\u001B[33m"; // YELLOW
    public static final String CHOOSE = "\u001B[34m"; // BLUE
    public static final String OUTPUT = "\u001B[35m"; // PURPLE
    public static final String HEADER_OUTPUT = "\u001B[36m"; // CYAN
    public static void main(String[] args) throws FileNotFoundException {
        String pathToPoints = "src/pointsInput.txt";
        String pathToInitSolutions = "src/initSolutionsInput.txt";

        MathImplicitFunction functionMV1 =
                (x) -> new PointMultiD(x, x.getItem(0) * x.getItem(0) - x.getItem(1) * x.getItem(1));
        MathImplicitFunction functionMV2 = (x) -> new PointMultiD(x,  - x.getItem(0) * x.getItem(0) + x.getItem(1));

        EquationsSystemSolving system = new EquationsSystemSolving(pathToPoints, pathToInitSolutions, functionMV1, functionMV2);
        system.newtonsIterativeCalculate(1);
    }
}