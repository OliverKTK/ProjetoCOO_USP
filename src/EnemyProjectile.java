public class EnemyProjectile extends PlayerProjectile{
    private double radius;

    public EnemyProjectile(int size, double radius){
        super(size);
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }
}

