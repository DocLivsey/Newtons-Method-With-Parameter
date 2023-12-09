import java.io.*;
import java.util.*;
public class EquationsSystemSolving extends MathBase {
    protected EquationsSystem equationsSystem;
    protected TreeMap<Vector, Vector> solutions;
    protected ArrayList<Vector> initApproximations;
    protected int dimension;
    EquationsSystemSolving(String pathToInitSolutions, EquationsSystem equationsSystem) throws FileNotFoundException
    {
        this.equationsSystem = equationsSystem;
        this.dimension = this.equationsSystem.getDimension();
        this.solutions = new TreeMap<>(Comparator.comparingDouble(Vector::ChebyshevNorm));
        this.initApproximations = new ArrayList<>();
        this.readInitSolutionsFromFile(pathToInitSolutions);
    }
    public TreeMap<Vector, Vector> getSolutions()
    { return this.solutions; }
    public Vector getSolution(int index)
    {
        if (Math.abs(index) > this.solutions.size())
        {
            System.out.println(Main.ERROR + "Выход за границы TreeMap. Неверно введен номер решения" + Main.RESET);
            return null;
        }
        else
        {
            int i = 0;
            for (Vector x : this.solutions.keySet())
            {
                if (i == index) return x;
                i++;
            }
        }
        return new Vector(this.dimension);
    }
    public Vector getValue(Vector solution)
    { return this.solutions.get(solution); }
    public EquationsSystem getEquationsSystem()
    { return this.equationsSystem; }
    public ArrayList<Vector> getInitApproximations()
    { return this.initApproximations; }
    public  Vector getInitSolution(int index)
    { return this.initApproximations.get(index); }
    public int getDimension()
    { return this.dimension; }
    public void setEquationsSystem(EquationsSystem equationsSystem)
    { this.equationsSystem = equationsSystem; }
    public void setSolutions(TreeMap<Vector, Vector> solutions)
    { this.solutions = solutions; }
    public void setSolution(Vector solution, Vector value)
    { this.solutions.put(solution, value); }
    public void setInitApproximations(ArrayList<Vector> initApproximations)
    { this.initApproximations = initApproximations; }
    public void setInitSolution(int index, Vector initSolution)
    { this.initApproximations.set(index, initSolution); }
    public void setDimension(int dimension)
    { this.dimension = dimension; }
    public void removeSolution(int index)
    {
        if (Math.abs(index) > this.solutions.size())
            System.out.println(Main.ERROR + "Решение по заданному номеру не найдено" + Main.RESET);
        else
        {
            int i = 0;
            for (Vector x : this.solutions.keySet())
            {
                if (i == index) this.solutions.remove(x);
                i++;
            }
        }
    }
    public void addInitSolution(Vector initSolution)
    { this.initApproximations.add(initSolution); }
    public void printSolutions()
    {
        System.out.println(Main.HEADER_OUTPUT + "Решения Системы Уравнений " + Main.COMMENT + this.toString() + Main.RESET);
        for (Vector solution : this.solutions.keySet())
        {
            String solutionToString = Arrays.toString(solution.getVector());
            String valueToString = Arrays.toString(this.getValue(solution).getVector());
            System.out.println(Main.OUTPUT + "{" + solutionToString + "; " + valueToString + "}");
        }
    }
    public void printInitSolutions()
    {
        for (Vector initSolution : this.initApproximations)
            initSolution.printVector();
    }
    public void readInitSolutionsFromFile(String pathToInitSolutions) throws FileNotFoundException
    {
        File input = new File(pathToInitSolutions);
        Scanner fileScan = new Scanner(input);
        while (fileScan.hasNextLine())
        {
            String[] pointsStrArr = fileScan.nextLine().trim().split("\\s+");
            Vector initSolutionFromStr = new Vector(this.dimension);
            if (this.dimension != pointsStrArr.length)
                System.out.println(Main.ERROR + "Неверно введены начальные приближения! Размерности не совпадают!\n Измените размерность входных данных" + Main.RESET);
            else
            {
                for (int i = 0; i < this.dimension; i++)
                {
                    if (!isNumeric(pointsStrArr[i]))
                        System.out.println(Main.ERROR + "Ошибка чтения с файла! Введено не число" + Main.RESET);
                    else
                        initSolutionFromStr.setItem(i, Double.parseDouble(pointsStrArr[i]));
                }
            }
            this.addInitSolution(initSolutionFromStr);
        }
    }
    public Matrix setJacobianMatrix(Vector x)
    {
        Matrix jacobianMatrix = new Matrix(this.dimension, this.dimension);
        for (int i = 0; i < this.dimension; i++)
        {
            MathImplicitFunctionOperations function = this.equationsSystem.getEquation(i);
            for (int j = 0; j < this.dimension; j++)
            {
                double df = function.partialDifferential(j, function.calculatePoint(x));
                jacobianMatrix.setItem(i, j, df);
            }
        }
        return jacobianMatrix;
    }
    public Vector newtonsIterativeCalculate(double parameter, Vector currentApproximation, Matrix jacobianMatrix)
    {
        Vector ksi = currentApproximation.cloneVector();
        Vector y = this.equationsSystem.calculateValuesVector(ksi);
        Vector JxF = jacobianMatrix.matrixAndVectorMultiplication(y).constantMultiplication(parameter);
        ksi = currentApproximation.vectorDifference(JxF);
        ksi.printVector();
        return ksi;
    }
    public void newtonsMethod(double parameter)
    {
        for (Vector initApproximation : this.initApproximations)
        {
            int it = 0;
            Vector currentApproximation = initApproximation.cloneVector();
            Vector newApproximation, solution, deltaApproximation;
            do {
                System.out.println("Iteration " + it); it++;

                solution = currentApproximation.cloneVector();
                Matrix jacobianMatrix = this.setJacobianMatrix(currentApproximation);
                System.out.println("Jacobian:");jacobianMatrix.printMatrix();
                jacobianMatrix = jacobianMatrix.inversion();
                System.out.println("Inversion Jacobian:");jacobianMatrix.printMatrix();
                if (jacobianMatrix.isNanMatrix())
                {
                    System.out.println(Main.HEADER_OUTPUT + "Текущее приближение:" + Main.RESET); currentApproximation.printVector();
                    System.out.println(Main.ERROR + "Для текущего приближения Якобиан вырожден \nПопробуйте изменить начальное приближение" + Main.RESET);
                }
                else
                {
                    newApproximation = this.newtonsIterativeCalculate(parameter, currentApproximation, jacobianMatrix);
                    currentApproximation = newApproximation.cloneVector();
                }
                deltaApproximation = currentApproximation.vectorDifference(solution);
            } while (it < 10000 && deltaApproximation.ChebyshevNorm() >= super.getEpsilon() && !this.equationsSystem.calculateValuesVector(currentApproximation).isZeroVector());
            System.out.print("solution:"); solution.printVector(); this.equationsSystem.calculateValuesVector(solution).printVector();
        }
    }
}
