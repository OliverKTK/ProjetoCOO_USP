public class EnemyProjectile extends BaseProjectile {
    private double radius;

    public EnemyProjectile(double radius){
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }
    public void setRadius(double radius){
        this.radius = radius;
    }

}

