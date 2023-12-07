import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class EquationsSystemSolving {
    protected ArrayList<MathImplicitFunctionOperations> equationsSystem;
    protected ArrayList<PointMultiD> solutions;
    protected ArrayList<PointMultiD> initSolutions;
    protected int dimension;
    EquationsSystemSolving(ArrayList<MathImplicitFunctionOperations> equationsSystem, String pathToInitSolutions) throws FileNotFoundException
    {
        this.equationsSystem = equationsSystem;
        this.solutions = new ArrayList<>();
        this.initSolutions = new ArrayList<>();
        this.readInitSolutionsFromFile(pathToInitSolutions);
        this.dimension = this.getEquation(0).getDimension();
    }
    EquationsSystemSolving(String pathToPoints, String pathToInitSolutions, MathImplicitFunction firstFunction, MathImplicitFunction secondFunction) throws FileNotFoundException
    {
        this.solutions = new ArrayList<>();
        this.initSolutions = new ArrayList<>();
        this.equationsSystem = new ArrayList<>();
        this.dimension = 2;
        MathImplicitFunctionOperations firstEquation = new MathImplicitFunctionOperations(this.dimension, pathToPoints, firstFunction);
        MathImplicitFunctionOperations secondEquation = new MathImplicitFunctionOperations(this.dimension, pathToPoints, secondFunction);
        this.addEquation(firstEquation); this.addEquation(secondEquation);
        this.readInitSolutionsFromFile(pathToInitSolutions);
    }
    public ArrayList<PointMultiD> getSolutions()
    { return this.solutions; }
    public PointMultiD getSolution(int index)
    { return this.solutions.get(index); }
    public ArrayList<MathImplicitFunctionOperations> getEquationsSystem()
    { return this.equationsSystem; }
    public MathImplicitFunctionOperations getEquation(int index)
    { return this.equationsSystem.get(index); }
    public ArrayList<PointMultiD> getInitSolutions()
    { return this.initSolutions; }
    public  PointMultiD getInitSolution(int index)
    { return this.initSolutions.get(index); }
    public int getDimension()
    { return this.dimension; }
    public void setEquationsSystem(ArrayList<MathImplicitFunctionOperations> equationsSystem)
    { this.equationsSystem = equationsSystem; }
    public void setEquation(int index, MathImplicitFunctionOperations equation)
    { this.equationsSystem.set(index, equation); }
    public void setSolutions(ArrayList<PointMultiD> solutions)
    { this.solutions = solutions; }
    public void setSolution(int index, PointMultiD solution)
    { this.solutions.set(index, solution); }
    public void setInitSolutions(ArrayList<PointMultiD> initSolutions)
    { this.initSolutions = initSolutions; }
    public void setInitSolution(int index, PointMultiD initSolution)
    { this.initSolutions.set(index, initSolution); }
    public void setDimension(int dimension)
    { this.dimension = dimension; }
    public void addEquation(MathImplicitFunctionOperations equation)
    { this.equationsSystem.add(equation); }
    public void addSolution(PointMultiD solution)
    { this.solutions.add(solution); }
    public void addInitSolution(PointMultiD initSolution)
    { this.initSolutions.add(initSolution); }
    public void printInitSolutions()
    {
        for (PointMultiD initSolution : this.initSolutions)
            initSolution.print();
    }
    public void readInitSolutionsFromFile(String pathToInitSolutions) throws FileNotFoundException
    {
        File input = new File(pathToInitSolutions);
        Scanner fileScan = new Scanner(input);
        while (fileScan.hasNextLine())
        {
            String point = fileScan.nextLine();
            PointMultiD initSolutionFromStr = new PointMultiD(point, this.getEquation(0).getDimension() + 1);
            this.addInitSolution(initSolutionFromStr);
        }
    }
    public Matrix setJacobianMatrix(PointMultiD point)
    {
        Matrix jacobianMatrix = new Matrix(this.dimension, this.dimension);
        for (int i = 0; i < this.dimension; i++)
        {
            MathImplicitFunctionOperations function = this.getEquation(i);
            for (int j = 0; j < this.dimension; j++)
            {
                double df = function.partialDifferential(j, point);
                jacobianMatrix.setItem(i, j, df);
            }
        }
        return jacobianMatrix;
    }
    public void newtonsIterativeCalculate(double parameter)
    {
        Vector solution, ksi;
        for (PointMultiD initSolution : this.initSolutions)
        {
            Matrix jacobianMatrix = this.setJacobianMatrix(initSolution);
            jacobianMatrix = jacobianMatrix.inversion();
            if (jacobianMatrix.isNanMatrix())
                System.out.println(Main.ERROR + "Для текущего приближения Якобиан вырожден \nПопробуйте изменить начальное приближение");
            else
            {
                Vector currentSolution = initSolution.getVectorX();
            }
        }
    }
    public void newtonsMethod(double parameter)
    {

    }
}
