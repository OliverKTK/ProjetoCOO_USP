import java.util.ArrayList;
import java.util.List;

public class Background {
    private List<Double> X;
    private List<Double> Y;
    private double speed;
    private double count;

    public Background(int size, double speed, double count) {
        this.X = new ArrayList<>(size);
        this.Y = new ArrayList<>(size);
        this.speed = speed;
        this.count = count;
        for (int i = 0; i < size; i++) {
            this.X.add(0.0);
            this.Y.add(0.0);
        }
    }

    public void Initialize(double width, double height) {
        for (int i = 0; i < X.size(); i++) {
            this.X.set(i, Math.random() * width);
            this.Y.set(i, Math.random() * height);
        }
    }

    public List<Double> getX() {
        return X;
    }
    public void setX(double x, int i) {
        this.X.set(i, x);
    }

    public List<Double> getY() {
        return Y;
    }
    public void setY(double y, int i) {
        this.Y.set(i, y);
    }

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCount() {
        return count;
    }
    public void setCount(double count) {
        this.count = count;
    }
}
