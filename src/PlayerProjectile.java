public class PlayerProjectile {
    private int [] state;
    private double [] X;
    private double [] Y;
    private double [] VX;
    private double [] VY;

    public PlayerProjectile(int qunatity) {
        this.state = new int[qunatity];
        this.X = new double[qunatity];
        this.Y = new double[qunatity];
        this.VX = new double[qunatity];
        this.VY = new double[qunatity];
    }

    public void Initialize() {
        for (int i = 0; i < state.length; i++) {
            this.state[i] = 0;
        }
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
}
