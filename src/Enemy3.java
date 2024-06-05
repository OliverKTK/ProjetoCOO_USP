public class Enemy3 extends BaseIEnemy implements IEnemyBehavior {
    private int[] health;
    private int max_health;
    private double max_radius;

    public Enemy3(int quantity, double radius, long nextEnemy, int health) {
        super(quantity, radius, nextEnemy);
        this.health = new int[quantity];
        this.max_health = health;
        this.max_radius = radius;
    }

    public void Initialize() {
        super.Initialize();
        for (int i = 0; i < health.length; i++) {
            this.health[i] = max_health;
        }
    }

    public void behavior(Player player, PlayerProjectile e, long delta, int free, long currentTime, int height, int width){
        for(int i = 0; i < getState().length; i++){

            if(getState()[i] == 3){
                if(currentTime > getExplosion_end()[i]){
                    setState(1, i);
                }
            }

            else if(getState()[i] == 2){

                if(currentTime > getExplosion_end()[i]){

                    setState(0, i);
                    setHealth(getMaxHealth(), i);
                }
            }

            else if(getState()[i] == 1){
                if(getY()[i] <  Math.random() * GameLib.HEIGHT * 0.25){
                    setY(getY()[i] + getV()[i] * Math.sin(getAngle()[i]) * delta * (-1.0), i);
                }
                setX(getX()[i] + getV()[i] * Math.cos(getAngle()[i]) * delta, i);
                setAngle(getAngle()[i] + getRV()[i]*delta, i);

                if(currentTime > getNextShoot()[i] && getY()[i] < player.getY()){

                    double co = player.getX() - getX()[i];
                    double ca = player.getY() - getY()[i];
                    double hip = Math.sqrt(co*co + ca*ca);
                    if(free < e.getState().length){

                        e.setX(getX()[i], free);
                        e.setY(getY()[i], free);
                        e.setVX((co/hip) *0.4,free);
                        e.setVY((ca/hip) *0.4,free);
                        e.setState(1, free);

                        setNextShoot((long) (currentTime + 1500 + Math.random() * 600), i);
                    }
                }
            }
        }
    }

    public int[] getHealth() {
        return health;
    }

    public void setHealth(int health, int i) {
        this.health[i] = health;
    }

    public int getMaxHealth() {
        return max_health;
    }

    public double getMax_radius() {
        return max_radius;
    }
}
