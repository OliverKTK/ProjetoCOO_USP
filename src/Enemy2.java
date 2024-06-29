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
