import java.util.List;

public interface IEntity {
    List<Integer> getState();
    List<Double> getX();
    List<Double> getY();
    double getRadius();
}
