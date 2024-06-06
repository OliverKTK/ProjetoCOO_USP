public class PowerUp {
    private int state;
    private double X;
    private double Y;
    private double V;
    private double radius;
    private int type;
    private int active = 0;
    private long nextPow;

    public PowerUp(int type, double radius, double V, long nextPow, long currentTime) {
        this.type = type;
        this.radius = radius;
        this.V = V;
        this.nextPow = currentTime + nextPow;
    }

    public void Initialize(){
        this.state = 0;
        this.active = 0;
    }

    public void newPow(long currentTime, int spawnOffset, int random){
        if(currentTime > getNextPow()){
            setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
            setY(10.0);
            setV(0.20 + Math.random() * 0.15);
            setState(1);
            setNextPow((long) (currentTime + spawnOffset + Math.random() * random));
            System.out.println(getState());
        }
    }

    public void behavior(long delta,long currentTime, int height){
        if(getState() == 1){
            if(getY() > height + 10){
                setState(0);
            }
            else{
                setY(getY()+ getV() * Math.random() * delta * 2);
            }
        }
    }


    public int getState(){
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public double getX(){
        return X;
    }
    public void setX(double X) {
        this.X = X;
    }

    public double getY(){
        return Y;
    }
    public void setY(double Y) {
        this.Y = Y;
    }

    public double getRadius(){
        return radius;
    }

    public double getV(){
        return V;
    }
    public void setV(double V) {
        this.V = V;
    }

    public int getType(){
        return type;
    }

    public int getActive(){
        return active;
    }
    public void setActive(int active){
        this.active = active;
    }

    public long getNextPow(){
        return nextPow;
    }
    public void setNextPow(long nextPow){
        this.nextPow = nextPow;
    }

}
