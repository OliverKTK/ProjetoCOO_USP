public class PlayerProjectile {
    private int [] state =  new int[10];
    private double [] X =  new double[10];
    private double [] Y =  new double[10];
    private double [] VX =  new double[10];
    private double [] VY =  new double[10];

    public PlayerProjectile() {}

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
}
