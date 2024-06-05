public class PlayerProjectile {
    private int [] state;
    private double [] X;
    private double [] Y;
    private double [] VX;
    private double [] VY;

    public PlayerProjectile(int qunatity) {
        this.state = new int[qunatity];
        this.X = new double[qunatity];
        this.Y = new double[qunatity];
        this.VX = new double[qunatity];
        this.VY = new double[qunatity];
    }

    public void Initialize() {
        for (int i = 0; i < state.length; i++) {
            this.state[i] = 0;
        }
    }

    public void projectileColision(BaseEnemy enemy, long currentTime, int k){
        for(int i = 0; i < enemy.getState().length; i++) {

            if (enemy.getState()[i] == 1) {

                double dx = enemy.getX()[i] - getX()[k];
                double dy = enemy.getY()[i] - getY()[k];
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < enemy.getRadius()){
                    if (enemy instanceof Enemy3 enemy3) {
                        if (enemy3.getHealth()[i] > 0) {
                            enemy3.setHealth(enemy3.getHealth()[i] - 1, i);
                            enemy3.setState(3, i);
                            enemy3.setExplosion_end(currentTime + 300, i);
                        } else {
                            enemy3.setState(2, i);
                            enemy3.setExplosion_start(currentTime, i);
                            enemy3.setExplosion_end(currentTime + 500, i);
                        }
                    }
                    else  {
                        enemy.setState(2, i);
                        enemy.setExplosion_start(currentTime, i);
                        enemy.setExplosion_end(currentTime + 500, i);
                    }
                }
            }
        }
    }

    public void outOfBounds(int height, long delta){
        for(int i = 0; i < getState().length; i++){

            if(getState()[i] == 1){

                /* verificando se projétil saiu da tela */
                if(getY()[i] < 0 || getY()[i] > height) {

                    setState(0, i);
                }
                else {

                    setX(getX()[i]+ getVX()[i] *delta, i);
                    setY(getY()[i]+ getVY()[i] *delta, i);
                }
            }
        }
    }

    public int[] getState() {
        return state;
    }
    public void setState(int state, int i) {
        this.state[i] = state;
    }

    public double[] getX() {
        return X;
    }
    public void setX(double X, int i) {
        this.X[i] = X;
    }

    public double[] getY() {
        return Y;
    }
    public void setY(double Y, int i) {
        this.Y[i] = Y;
    }

    public double[] getVX() {
        return VX;
    }
    public void setVX(double VX, int i) {
        this.VX[i] = VX;
    }

    public double[] getVY() {
        return VY;
    }
    public void setVY(double VY, int i) {
        this.VY[i] = VY;
    }
}
