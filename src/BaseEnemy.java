import java.util.ArrayList;
import java.util.List;

public class BaseEnemy implements IEntity, IEnemyBehavior {
    private List<Integer> state;
    private List<Double> X;
    private List<Double> Y;
    private List<Double> V;
    private List<Double> angle;
    private List<Double> RV;
    private List<Double> explosion_start;
    private List<Double> explosion_end;
    private double radius;
    private long nextEnemy;
    private List<Long> nextShoot;

    public BaseEnemy(int quantity, double radius, long nextEnemy) {
        this.state = new ArrayList<>(quantity);
        this.X = new ArrayList<>(quantity);
        this.Y = new ArrayList<>(quantity);
        this.V = new ArrayList<>(quantity);
        this.angle = new ArrayList<>(quantity);
        this.RV = new ArrayList<>(quantity);
        this.explosion_start = new ArrayList<>(quantity);
        this.explosion_end = new ArrayList<>(quantity);
        this.nextShoot = new ArrayList<>(quantity);
        this.radius = radius;
        this.nextEnemy = nextEnemy;
        for (int i = 0; i < quantity; i++) {
            this.state.add(0);
            this.X.add(0.0);
            this.Y.add(0.0);
            this.V.add(0.0);
            this.angle.add(0.0);
            this.RV.add(0.0);
            this.explosion_start.add(0.0);
            this.explosion_end.add(0.0);
            this.nextShoot.add(0L);
        }
    }

    public void Initialize() {
        for (int i = 0; i < state.size(); i++) {
            this.state.set(i, 0);
        }
    }

    public void newEnemy(long currentTime, int free, int shootOffset, int spawnOffset, int random) {
        if (currentTime > getNextEnemy()) {
            if (free < getState().size()) {
                setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, free);
                setY(-10.0, free);
                setV(0.20 + Math.random() * 0.15, free);
                setAngle(3 * Math.PI / 2, free);
                setRV(0, free);
                setState(1, free);
                setNextShoot(currentTime + shootOffset, free);
                setNextEnemy((long) (currentTime + spawnOffset + Math.random() * random));
            }
        }
    }

    public void behavior(Player player, PlayerProjectile e, long delta, int free, long currentTime, int height, int width) {
        for (int i = 0; i < state.size(); i++) {
            if (state.get(i) == 2) {
                if (currentTime > getExplosion_end().get(i)) {
                    setState(0, i);
                }
            } else if (state.get(i) == 1) {
                if (getX().get(i) < -10 || getX().get(i) > width + 10 || getY().get(i) > height + 10) {
                    setState(0, i);
                } else {
                    setX(getX().get(i) + getV().get(i) * Math.cos(getAngle().get(i)) * delta, i);
                    setY(getY().get(i) + getV().get(i) * Math.sin(getAngle().get(i)) * delta * (-1), i);
                    setAngle(getAngle().get(i) + getRV().get(i) * delta, i);

                    if (currentTime > getNextShoot().get(i) && getY().get(i) < player.getY()) {
                        if (free < e.getState().size()) {
                            e.setX(getX().get(i), free);
                            e.setY(getY().get(i), free);
                            e.setVX(Math.cos(getAngle().get(i)) * 0.45, free);
                            e.setVY(Math.sin(getAngle().get(i)) * 0.45 * (-1.0), free);
                            e.setState(1, free);
                            setNextShoot((long) (currentTime + 200 + Math.random() * 500), i);
                        }
                    }
                }
            }
        }
    }

    public List<Integer> getState() {
        return state;
    }
    public void setState(int state, int i) {
        this.state.set(i, state);
    }

    public List<Double> getX() {
        return X;
    }
    public void setX(double X, int i) {
        this.X.set(i, X);
    }

    public List<Double> getY() {
        return Y;
    }
    public void setY(double Y, int i) {
        this.Y.set(i, Y);
    }

    public List<Double> getV() {
        return V;
    }
    public void setV(double V, int i) {
        this.V.set(i, V);
    }

    public List<Double> getAngle() {
        return angle;
    }
    public void setAngle(double angle, int i) {
        this.angle.set(i, angle);
    }

    public List<Double> getRV() {
        return RV;
    }
    public void setRV(double RV, int i) {
        this.RV.set(i, RV);
    }

    public List<Double> getExplosion_start() {
        return explosion_start;
    }
    public void setExplosion_start(double explosion_start, int i) {
        this.explosion_start.set(i, explosion_start);
    }

    public List<Double> getExplosion_end() {
        return explosion_end;
    }
    public void setExplosion_end(double explosion_end, int i) {
        this.explosion_end.set(i, explosion_end);
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

    public List<Long> getNextShoot() {
        return nextShoot;
    }
    public void setNextShoot(long nextShoot, int i) {
        this.nextShoot.set(i, nextShoot);
    }
}
