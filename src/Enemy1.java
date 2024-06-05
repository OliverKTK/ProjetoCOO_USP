public class Enemy1 extends BaseEnemy{
    private long [] nextShoot= new long[10];

    public Enemy1(int quantity, double radius, long nextEnemy){
        super(quantity, radius, nextEnemy);
    }

    public long[] getNextShoot() {
        return nextShoot;
    }
    public void setNextShoot(long nextShoot, int i){
        this.nextShoot[i] = nextShoot;
    }
}
