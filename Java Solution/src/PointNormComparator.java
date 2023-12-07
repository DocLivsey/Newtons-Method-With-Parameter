import java.util.Comparator;

public class PointNormComparator implements Comparator<PointMultiD> {
    @Override
    public int compare(PointMultiD point1, PointMultiD point2) {
        if (point1.getVectorX().getVectorSize() != point2.getVectorX().getVectorSize())
        {
            System.out.println(Main.ERROR + "Размерность точек разная" + Main.RESET);
            return 0;
        }
        double norm1 = 0, norm2 = 0;
        for (int i = 0; i < point1.getVectorX().getVectorSize(); i++)
        {
            norm1 += point1.getX(i) * point1.getX(i);
            norm2 += point2.getX(i) * point2.getX(i);
        }
        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);
        return (int) (norm1 - norm2);
    }
}
