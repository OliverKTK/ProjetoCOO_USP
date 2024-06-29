import java.util.ArrayList;
import java.util.List;

public class Enemy3 extends BaseEnemy implements IEnemyBehavior {
    private List<Integer> health;
    private int max_health;

    public Enemy3(int quantity, double radius, long nextEnemy, int health) {
        super(quantity, radius, nextEnemy);
        this.health = new ArrayList<>(quantity);
        this.max_health = health;
        for (int i = 0; i < quantity; i++) {
            this.health.add(health);
        }
    }

    public void Initialize() {
        super.Initialize();
        for (int i = 0; i < health.size(); i++) {
            this.health.set(i, max_health);
        }
    }

    public void behavior(Player player, PlayerProjectile e, long delta, int free, long currentTime, int height, int width) {
        for (int i = 0; i < getState().size(); i++) {
            if (getState().get(i) == 3) {
                if (currentTime > getExplosion_end().get(i)) {
                    setState(1, i);
                }
            } else if (getState().get(i) == 2) {
                if (currentTime > getExplosion_end().get(i)) {
                    setState(0, i);
                    setHealth(getMaxHealth(), i);
                }
            } else if (getState().get(i) == 1) {
                if (getY().get(i) < Math.random() * GameLib.HEIGHT * 0.25) {
                    setY(getY().get(i) + getV().get(i) * Math.sin(getAngle().get(i)) * delta * (-1.0), i);
                }
                setX(getX().get(i) + getV().get(i) * Math.cos(getAngle().get(i)) * delta, i);
                setAngle(getAngle().get(i) + getRV().get(i) * delta, i);

                if (currentTime > getNextShoot().get(i) && getY().get(i) < player.getY()) {
                    double co = player.getX() - getX().get(i);
                    double ca = player.getY() - getY().get(i);
                    double hip = Math.sqrt(co * co + ca * ca);
                    if (free < e.getState().size()) {
                        e.setX(getX().get(i), free);
                        e.setY(getY().get(i), free);
                        e.setVX((co / hip) * 0.4, free);
                        e.setVY((ca / hip) * 0.4, free);
                        e.setState(1, free);
                        setNextShoot((long) (currentTime + 1500 + Math.random() * 600), i);
                    }
                }
            }
        }
    }

    public List<Integer> getHealth() {
        return health;
    }
    public void setHealth(int health, int i) {
        this.health.set(i, health);
    }

    public int getMaxHealth() {
        return max_health;
    }

}
