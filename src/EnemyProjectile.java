public class EnemyProjectile {
    private int [] state =  new int[100];
    private double [] X =  new double[100];
    private double [] Y =  new double[100];
    private double [] VX =  new double[100];
    private double [] VY =  new double[100];


    private double radius;

    public EnemyProjectile(double radius){

        this.radius = radius;
    }
    public int[] getState() {
        return state;
    }
    public void setState(int state, int i) {
        this.state[i] = state;
    }

    public double[] getX() {
        return X;
    }
    public void setX(double X, int i) {
        this.X[i] = X;
    }

    public double[] getY() {
        return Y;
    }
    public void setY(double Y, int i) {
        this.Y[i] = Y;
    }

    public double[] getVX() {
        return VX;
    }
    public void setVX(double VX, int i) {
        this.VX[i] = VX;
    }

    public double[] getVY() {
        return VY;
    }
    public void setVY(double VY, int i) {
        this.VY[i] = VY;
    }

    public double getRadius(){
        return radius;
    }
    public void setRadius(double radius){
        this.radius = radius;
    }

}

