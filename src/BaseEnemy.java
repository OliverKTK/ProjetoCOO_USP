public class BaseEnemy {
    private int [] state;
    private double [] X;
    private double [] Y;
    private double [] V;
    private double [] angle;
    private double [] RV;
    private double [] explosion_start;
    private double [] explosion_end;
    private double radius;
    private long nextEnemy;

    public BaseEnemy(int quantity, double radius, long nextEnemy){
        this.state = new int[quantity];
        this.X = new double[quantity];
        this.Y = new double[quantity];
        this.V = new double[quantity];
        this.angle = new double[quantity];
        this.RV = new double[quantity];
        this.explosion_start = new double[quantity];
        this.explosion_end =  new double[quantity];
        this.radius = radius;
        this.nextEnemy = nextEnemy;
    }
    public void Initialize(){
        for(int i = 0; i < state.length; i++){
            this.state[i] = 0;
        }
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
