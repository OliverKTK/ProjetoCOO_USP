public class EnemyProjectile extends PlayerProjectile{
    private double radius;

    public EnemyProjectile(int quantity, double radius){
        super(quantity);
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }
}

