import java.util.Comparator;

public class PointNormComparator implements Comparator<PointMultiD> {
    @Override
    public int compare(PointMultiD point1, PointMultiD point2) {
        if (point1.getVectorX().getVectorSize() != point2.getVectorX().getVectorSize())
        {
            System.out.println(Main.ERROR + "Размерность точек разная" + Main.RESET);
            return 0;
        }
        return Double.compare(point1.getVectorX().ChebyshevNorm(), point2.getVectorX().ChebyshevNorm());
    }
}
