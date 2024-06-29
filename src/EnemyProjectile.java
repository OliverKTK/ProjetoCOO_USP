import java.util.List;

public class EnemyProjectile extends PlayerProjectile implements IEntity {
    private double radius;

    public EnemyProjectile(int quantity, double radius) {
        super(quantity);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
