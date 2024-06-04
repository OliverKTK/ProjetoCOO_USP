public class BaseEnemy {
    private int [] state = new int[10];
    private double [] X = new double[10];
    private double [] Y = new double[10];
    private double [] V = new double[10];
    private double [] angle = new double[10];
    private double [] RV = new double[10];
    private double [] explosion_start = new double[10];
    private double [] explosion_end = new double[10];
    private double radius;
    private long nextEnemy;

    public BaseEnemy(double radius, long nextEnemy){
        this.radius = radius;
        this.nextEnemy = nextEnemy;
    }
    public int[] getState(){
        return state;
    }
    public void setState(int state, int i) {
        this.state[i] = state;
    }

    public double[] getX(){
        return X;
    }
    public void setX(double X, int i) {
        this.X[i] = X;
    }

    public double[] getY(){
        return Y;
    }
    public void setY(double Y, int i) {
        this.Y[i] = Y;
    }

    public double[] getV(){
        return V;
    }
    public void setV(double V, int i) {
        this.V[i] = V;
    }

    public double[] getAngle(){
        return angle;
    }
    public void setAngle(double angle, int i) {
        this.angle[i] = angle;
    }

    public double[] getRV(){
        return RV;
    }
    public void setRV(double RV, int i) {
        this.RV[i] = RV;
    }

    public double[] getExplosion_start() {
        return explosion_start;
    }
    public void setExplosion_start(double explosion_start, int i) {
        this.explosion_start[i] = explosion_start;
    }

    public double[] getExplosion_end() {
        return explosion_end;
    }
    public void setExplosion_end(double explosion_end, int i) {
        this.explosion_end[i] = explosion_end;
    }

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public long getNextEnemy() {
        return nextEnemy;
    }
    public void setNextEnemy(long nextEnemy) {
        this.nextEnemy = nextEnemy;
    }
}
