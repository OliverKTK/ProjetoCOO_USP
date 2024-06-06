public class BaseEnemy implements IEntity, IEnemyBehavior {
    private int [] state;
    private double [] X;
    private double [] Y;
    private double [] V;
    private double [] angle;
    private double [] RV;
    private double [] explosion_start;
    private double [] explosion_end;
    private double radius;
    private long nextEnemy;
    private long [] nextShoot;

    public BaseEnemy(int quantity, double radius, long nextEnemy){
        this.state = new int[quantity];
        this.X = new double[quantity];
        this.Y = new double[quantity];
        this.V = new double[quantity];
        this.angle = new double[quantity];
        this.RV = new double[quantity];
        this.explosion_start = new double[quantity];
        this.explosion_end =  new double[quantity];
        this.radius = radius;
        this.nextEnemy = nextEnemy;
        this.nextShoot = new long[quantity];
    }
    public void Initialize(){
        for(int i = 0; i < state.length; i++){
            this.state[i] = 0;
        }
    }

    public void newEnemy(long currentTime, int free, int shootOffset, int spawnOffset, int random){
        if(currentTime > getNextEnemy()){
            if(free < getState().length) {

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

    public void behavior(Player player, PlayerProjectile e, long delta, int free, long currentTime, int height, int width){
        for(int i = 0; i < state.length; i++){
            if(state[i] == 2){
                if(currentTime > getExplosion_end()[i]){
                    setState(0, i);
                }
            }
            else if(state[i] == 1){
                if(getX()[i] < -10 || getX()[i] > width + 10 || getY()[i] > height + 10){
                    setState(0, i);
                }
                else{
                    setX(getX()[i]+ getV()[i] * Math.cos(getAngle()[i]) * delta, i);
                    setY(getY()[i]+ getV()[i] * Math.sin(getAngle()[i]) * delta * (-1), i);
                    setAngle(getAngle()[i] + getRV()[i]* delta, i);

                    if(currentTime > getNextShoot()[i] && getY()[i] < player.getY()){
                        if(free < e.getState().length){
                            e.setX(getX()[i], free);
                            e.setY(getY()[i], free);
                            e.setVX(Math.cos(getAngle()[i]) * 0.45,free);
                            e.setVY(Math.sin(getAngle()[i]) * 0.45 * (-1.0),free);
                            e.setState(1, free);
                            setNextShoot((long) (currentTime + 200 + Math.random() * 500), i);
                        }
                    }
                }

            }
        }
    }

    public int[] getState(){
        return state;
    }
    public void setState(int state, int i) {
        this.state[i] = state;
    }

    public double[] getX(){
        return X;
    }
    public void setX(double X, int i) {
        this.X[i] = X;
    }

    public double[] getY(){

        return Y;
    }
    public void setY(double Y, int i) {
        this.Y[i] = Y;
    }

    public double[] getV(){

        return V;
    }
    public void setV(double V, int i) {

        this.V[i] = V;
    }

    public double[] getAngle(){
        return angle;
    }
    public void setAngle(double angle, int i) {
        this.angle[i] = angle;
    }

    public double[] getRV(){
        return RV;
    }
    public void setRV(double RV, int i) {
        this.RV[i] = RV;
    }

    public double[] getExplosion_start() {
        return explosion_start;
    }
    public void setExplosion_start(double explosion_start, int i) {
        this.explosion_start[i] = explosion_start;
    }

    public double[] getExplosion_end() {
        return explosion_end;
    }
    public void setExplosion_end(double explosion_end, int i) {
        this.explosion_end[i] = explosion_end;
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

    public long[] getNextShoot() {
        return nextShoot;
    }
    public void setNextShoot(long nextShoot, int i){
        this.nextShoot[i] = nextShoot;
    }
}
