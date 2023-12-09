import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EquationsSystem {
    protected ArrayList<MathImplicitFunctionOperations> equations;
    protected int dimension;

    EquationsSystem(String pathToPoints, MathImplicitFunction firstFunction, MathImplicitFunction secondFunction) throws FileNotFoundException
    {
        this.dimension = 2;
        this.equations = new ArrayList<>();
        this.addEquation(new MathImplicitFunctionOperations(this.dimension, pathToPoints, firstFunction));
        this.addEquation(new MathImplicitFunctionOperations(this.dimension, pathToPoints, secondFunction));
    }
    EquationsSystem(String pathToPoints, MathImplicitFunction firstFunction, MathImplicitFunction secondFunction, MathImplicitFunction thirdFunction) throws FileNotFoundException
    {
        this.dimension = 3;
        this.equations = new ArrayList<>();
        this.addEquation(new MathImplicitFunctionOperations(this.dimension, pathToPoints, firstFunction));
        this.addEquation(new MathImplicitFunctionOperations(this.dimension, pathToPoints, secondFunction));
        this.addEquation(new MathImplicitFunctionOperations(this.dimension, pathToPoints, thirdFunction));
    }
    EquationsSystem(int dimension)
    { this.equations = new ArrayList<>(); this.dimension = dimension; }
    EquationsSystem(ArrayList<MathImplicitFunctionOperations> equations, int dimension)
    { this.equations = equations; this.dimension = dimension; }
    public ArrayList<MathImplicitFunctionOperations> getEquations()
    { return this.equations; }
    public MathImplicitFunctionOperations getEquation(int index)
    { return this.equations.get(index); }
    public int getDimension()
    { return this.dimension; }
    public void setEquations(ArrayList<MathImplicitFunctionOperations> equations)
    { this.equations = equations; }
    public void setEquation(int index, MathImplicitFunctionOperations equation)
    { this.equations.set(index, equation); }
    public void setDimension(int dimension)
    { this.dimension = dimension; }
    public void addEquation(MathImplicitFunctionOperations equation)
    { this.equations.add(equation); }
    public Vector calculateValuesVector(Vector x)
    {
        Vector y = new Vector(this.dimension);
        for (int i = 0; i < this.dimension; i++)
            y.setItem(i, this.getEquation(i).calculatePoint(x).getY());
        return y;
    }
    public void printSystem()
    {
        System.out.println(Main.HEADER_OUTPUT + "Система Уравнений:" + Main.RESET);
        for (MathImplicitFunctionOperations function : this.equations)
            function.printFunction();
        System.out.println(Main.HEADER_OUTPUT + "Точки Системы Уравнений:" + Main.RESET);
        for (MathImplicitFunctionOperations function : this.equations)
        {
            function.printFunction();
            function.printPoints();
        }
    }
}
