import java.util.List;

public class Enemy2 extends BaseEnemy implements IEnemyBehavior {
    private double spawnX;
    private int count;

    public Enemy2(int quantity, double radius, long nextEnemy, double spawnX) {
        super(quantity, radius, nextEnemy);
        this.spawnX = spawnX;
        this.count = 0;
    }

    public void newEnemy(long currentTime, int free, int spawnOffset, int random) {
        if (currentTime > getNextEnemy()) {
            if (free < getState().size()) {
                setX(getSpawnX(), free);
                setY(-10.0, free);
                setV(0.42, free);
                setAngle((3 * Math.PI) / 2, free);
                setRV(0.0, free);
                setState(1, free);
                setCount(getCount() + 1);

                if (getCount() < 10) {
                    setNextEnemy(currentTime + 120);
                } else {
                    setCount(0);
                    setSpawnX(Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
                    setNextEnemy((long) (currentTime + spawnOffset + Math.random() * random));
                }
            }
        }
    }

    public void behavior(Player player, PlayerProjectile e, long delta, int[] freeArray, long currentTime, int height, int width){
        for (int i = 0; i < getState().size(); i++) {
            if (getState().get(i) == 2) {
                if (currentTime > getExplosion_end().get(i)) {
                    setState(0, i);
                }
            }

            if (getState().get(i) == 1) {
                if (getX().get(i) < -10 || getX().get(i) > GameLib.WIDTH + 10) {
                    setState(0, i);
                } else {
                    boolean shootNow = false;
                    double previousY = getY().get(i);

                    setX(getX().get(i) + getV().get(i) * Math.cos(getAngle().get(i)) * delta, i);
                    setY(getY().get(i) + getV().get(i) * Math.sin(getAngle().get(i)) * delta * (-1.0), i);
                    setAngle(getAngle().get(i) + getRV().get(i) * delta, i);

                    double threshold = GameLib.HEIGHT * 0.30;
                    if (previousY < threshold && getY().get(i) >= threshold) {
                        if (getX().get(i) < (double) GameLib.WIDTH / 2) setRV(0.003, i);
                        else setRV(-0.003, i);
                    }

                    if (getRV().get(i) > 0 && Math.abs(getAngle().get(i) - 3 * Math.PI) < 0.05) {
                        setRV(0, i);
                        setAngle(3 * Math.PI, i);
                        shootNow = true;
                    }

                    if (getRV().get(i) < 0 && Math.abs(getAngle().get(i)) < 0.05) {
                        setRV(0.0, i);
                        setAngle(0, i);
                        shootNow = true;
                    }

                    if (shootNow) {
                        double[] angles = {Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8};
                        for (int k = 0; k < freeArray.length; k++) {
                            int free = freeArray[k];
                            if (free < e.getState().size()) {
                                double a = angles[k] + Math.random() * Math.PI / 6 - Math.PI / 12;
                                double vx = Math.cos(a);
                                double vy = Math.sin(a);

                                e.setX(getX().get(i), free);
                                e.setY(getY().get(i), free);
                                e.setVX(vx * 0.30, free);
                                e.setVY(vy * 0.30, free);
                                e.setState(1, free);
                            }
                        }
                    }
                }
            }
        }
    }

    public double getSpawnX() {
        return spawnX;
    }
    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
