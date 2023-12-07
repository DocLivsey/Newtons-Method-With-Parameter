import java.util.ArrayList;

public class EquationsSystem {
    protected ArrayList<MathImplicitFunctionOperations> equations;
    protected int dimension;
    EquationsSystem(ArrayList<MathImplicitFunctionOperations> equations, int dimension)
    { this.equations = equations; this.dimension = dimension; }
    public ArrayList<MathImplicitFunctionOperations> getEquations()
    { return this.equations; }
    public int getDimension()
    { return this.dimension; }
    public void setEquations(ArrayList<MathImplicitFunctionOperations> equations)
    { this.equations = equations; }
    public void setDimension(int dimension)
    { this.dimension = dimension; }
}
