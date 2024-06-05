public class Background {
    private double [] X;
    private double [] Y;
    private double speed;
    private double count;

    public Background(int size, double speed, double count){
        this.X = new double[size];
        this.Y = new double[size];
        this.speed = speed;
        this.count = count;
    }

    public double[] getX() {
        return X;
    }
    public void setX(double x, int i) {
        this.X[i] = x;
    }

    public double[] getY() {
        return Y;
    }
    public void setY(double y, int i) {
        this.Y[i] = y;
    }

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCount() {
        return count;
    }
    public void setCount(double count) {
        this.count = count;
    }

}
