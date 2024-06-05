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
    private int life;
    private long invinc = 0;

    public Player(double X, double Y, double baseSpeedXY, double radius, long nextShot, int life) {
        this.state = 1;
        this.X = X;
        this.Y = Y;
        this.VX = baseSpeedXY;
        this.VY = baseSpeedXY;
        this.radius = radius;
        this.nextShot = nextShot;
        this.life = life;
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

    public double getVY() {
        return VY;
    }

    public double getRadius() {
        return radius;
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

    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public long getInvinc() {
        return invinc;
    }
    public void setInvinc(long invinc_end) {
        this.invinc = invinc_end;
    }
}
