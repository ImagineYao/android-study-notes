import java.math.BigDecimal;
import java.util.Objects;

public class Point2D {
    private double x;
    private double y;

    public static final int EUCLIDEAN = 1;    // 欧氏距离
    public static final int MANHATTAN = 2;    // 曼哈顿距离

    public static void getCoordinateInfo() {
        System.out.println("Using 2D Cartesian Coordinate System");
    }

    public Point2D() {  }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistanceToZero() {
        double distance = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        BigDecimal bg = new BigDecimal(distance);

        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double getDistance(Point2D another, int type) {
        double distance = 0;
        switch (type) {
            case EUCLIDEAN:
                distance = Math.sqrt(Math.pow(this.x - another.x, 2) + Math.pow(this.y - another.y, 2));
                break;
            case MANHATTAN:
                distance = Math.abs(this.x - another.x) + Math.abs(this.y - another.y);
                break;
            default:
                break;
        }
        BigDecimal bg = new BigDecimal(distance);

        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "Point2D(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 &&
                Double.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
