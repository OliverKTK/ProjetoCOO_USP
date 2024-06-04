public class Enemy2 extends BaseEnemy {
    private double spawnX;
    private int count;

    public Enemy2(double radius, long nextEnemy, double spawnX, int count){
        super(radius,nextEnemy);
        this.spawnX = spawnX;
        this.count = count;
    }

    public double getSpawnX(){
        return spawnX;
    }
    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }


}
