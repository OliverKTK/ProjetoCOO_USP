public class Player {
    private int state;
    private double X;
    private double Y;
    private double VX;
    private double VY;
    private double radius;
    private double explosion_start= 0;
    private double explosion_end= 0;
    private long nextShot;

    public Player(int state, double X, double Y, double VX, double VY, double radius, long nextShot) {
        this.state = state;
        this.X = X;
        this.Y = Y;
        this.VX = VX;
        this.VY = VY;
        this.radius = radius;
        this.nextShot = nextShot;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public double getX() {
        return X;
    }
    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }
    public void setY(double Y) {
        this.Y = Y;
    }

    public double getVX() {
        return VX;
    }
    public void setVX(double VX) {
        this.VX = VX;
    }

    public double getVY() {
        return VY;
    }
    public void setVY(double VY) {
        this.VY = VY;
    }

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getExplosion_start() {
        return explosion_start;
    }
    public void setExplosion_start(double explosion_start) {
        this.explosion_start = explosion_start;
    }

    public double getExplosion_end() {
        return explosion_end;
    }
    public void setExplosion_end(double explosion_end) {
        this.explosion_end = explosion_end;
    }

    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }
}
