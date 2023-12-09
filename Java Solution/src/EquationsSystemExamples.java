import java.io.FileNotFoundException;

public final class EquationsSystemExamples {
    public static EquationsSystem firstExample(String pathToPoints) throws FileNotFoundException
    {
        // x^2 - y^2
        MathImplicitFunction firstFunction = (x) -> new PointMultiD(x, x.getItem(0) * x.getItem(0) - x.getItem(1) * x.getItem(1));
        // y - x^2
        MathImplicitFunction secondFunction = (x) -> new PointMultiD(x,  - x.getItem(0) * x.getItem(0) + x.getItem(1));
        return new EquationsSystem(pathToPoints, secondFunction, firstFunction);
    }
    public static EquationsSystem secondExample(String pathToPoints) throws FileNotFoundException
    {
        // x^2 + y^2 + z^2
        MathImplicitFunction firstFunction = (x) -> new PointMultiD(x, Math.pow(x.getItem(0), 2) + Math.pow(x.getItem(1), 2) + Math.pow(x.getItem(2), 2));
        // 2x^2 + y^2 - 4z
        MathImplicitFunction secondFunction = (x) -> new PointMultiD(x, 2 * Math.pow(x.getItem(0), 2) + Math.pow(x.getItem(1), 2) - 4 * x.getItem(2));
        // 3x^2 - 4y + z^2
        MathImplicitFunction thirdFunction = (x) -> new PointMultiD(x, 3 * Math.pow(x.getItem(0), 2) - 4 * x.getItem(1) + Math.pow(x.getItem(2), 2));
        return new EquationsSystem(pathToPoints, firstFunction, secondFunction, thirdFunction);
    }
    public static EquationsSystem thirdExample(String pathToPoints) throws FileNotFoundException
    {
        // y - arcsinx
        MathImplicitFunction firstFunction = (x) -> new PointMultiD(x, x.getItem(1) - Math.asin(x.getItem(0)));
        // y - sinx
        MathImplicitFunction secondFunction = (x) -> new PointMultiD(x, x.getItem(1) - Math.sin(x.getItem(0)));
        return new EquationsSystem(pathToPoints, firstFunction, secondFunction);
    }
    public static EquationsSystem fourthExample(String pathToPoints) throws FileNotFoundException
    {return null;}
}
