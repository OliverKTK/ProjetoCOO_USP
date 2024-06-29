import java.util.ArrayList;
import java.util.List;

public class PlayerProjectile {
    private List<Integer> state;
    private List<Double> X;
    private List<Double> Y;
    private List<Double> VX;
    private List<Double> VY;

    public PlayerProjectile(int quantity) {
        this.state = new ArrayList<>(quantity);
        this.X = new ArrayList<>(quantity);
        this.Y = new ArrayList<>(quantity);
        this.VX = new ArrayList<>(quantity);
        this.VY = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            this.state.add(0);
            this.X.add(0.0);
            this.Y.add(0.0);
            this.VX.add(0.0);
            this.VY.add(0.0);
        }
    }

    public void Initialize() {
        for (int i = 0; i < state.size(); i++) {
            this.state.set(i, 0);
        }
    }

    public void colision(BaseEnemy enemy, long currentTime, int k) {
        for (int i = 0; i < enemy.getState().size(); i++) {
            if (enemy.getState().get(i) == 1) {
                double dx = enemy.getX().get(i) - getX().get(k);
                double dy = enemy.getY().get(i) - getY().get(k);
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < enemy.getRadius()) {
                    if (enemy instanceof Enemy3 enemy3) {
                        if (enemy3.getHealth().get(i) > 0) {
                            enemy3.setHealth(enemy3.getHealth().get(i) - 1, i);
                            enemy3.setState(3, i);
                            enemy3.setExplosion_end(currentTime + 300, i);
                        } else {
                            enemy3.setState(2, i);
                            enemy3.setExplosion_start(currentTime, i);
                            enemy3.setExplosion_end(currentTime + 500, i);
                        }
                    } else {
                        enemy.setState(2, i);
                        enemy.setExplosion_start(currentTime, i);
                        enemy.setExplosion_end(currentTime + 500, i);
                    }
                }
            }
        }
    }

    public void outOfBounds(int height, long delta) {
        for (int i = 0; i < getState().size(); i++) {
            if (getState().get(i) == 1) {
                if (getY().get(i) < 0 || getY().get(i) > height) {
                    setState(0, i);
                } else {
                    setX(getX().get(i) + getVX().get(i) * delta, i);
                    setY(getY().get(i) + getVY().get(i) * delta, i);
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

    public List<Double> getVX() {
        return VX;
    }
    public void setVX(double VX, int i) {
        this.VX.set(i, VX);
    }

    public List<Double> getVY() {
        return VY;
    }
    public void setVY(double VY, int i) {
        this.VY.set(i, VY);
    }
}
