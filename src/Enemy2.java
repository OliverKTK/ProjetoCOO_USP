public class Enemy2 extends BaseEnemy {
    private double spawnX;
    private int count;

    public Enemy2(int quantity, double radius, long nextEnemy, double spawnX){
        super(quantity, radius, nextEnemy);
        this.spawnX = spawnX;
        this.count = 0;
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
