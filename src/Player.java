import java.util.List;

public class Player {
    private int state;
    private double X;
    private double Y;
    private double VX;
    private double VY;
    private double radius;
    private double explosion_start = 0;
    private double explosion_end = 0;
    private long nextShot;
    private int life;
    private long invinc = 0;
    private int lives;  // Número de vidas do jogador

    public Player(double X, double Y, double baseSpeedXY, double radius, long nextShot, int life) {
        this.state = 1;
        this.X = X;
        this.Y = Y;
        this.VX = baseSpeedXY;
        this.VY = baseSpeedXY;
        this.radius = radius;
        this.nextShot = nextShot;
        this.life = life;
        this.lives = 3;  // Valor padrão de 3 vidas se não especificado
    }

    public Player(double X, double Y, double baseSpeedXY, double radius, long nextShot, int life, int lives) {
        this.state = 1;
        this.X = X;
        this.Y = Y;
        this.VX = baseSpeedXY;
        this.VY = baseSpeedXY;
        this.radius = radius;
        this.nextShot = nextShot;
        this.life = life;
        this.lives = lives;  // Número de vidas especificado
    }

    public void colision(IEntity entity, long currentTime, int max_life) {
        for (int i = 0; i < entity.getState().size(); i++) {
            double dx = entity.getX().get(i) - getX();
            double dy = entity.getY().get(i) - getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < (getRadius() + entity.getRadius()) * 0.8) {
                if (entity instanceof BaseEnemy || entity instanceof PlayerProjectile) {
                    if (getLife() > 0) {
                        setLife(getLife() - 1);
                        setState(0);
                        setInvinc(currentTime + 500);
                    } else {
                        setLives(getLives() - 1);  // Decrementa o número de vidas
                        if (getLives() > 0) {
                            setLife(max_life);
                            setState(2);
                            setExplosion_start(currentTime);
                            setExplosion_end(currentTime + 2000);
                        } else {
                            setState(4);  // Estado de jogo acabado
                        }
                    }
                }
            }
        }
    }

    public void pickPow(PowerUp pow, long currentTime) {
        double dx = pow.getX() - getX();
        double dy = pow.getY() - getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (getRadius() + pow.getRadius()) * 0.8 && pow.getState() == 1) {
            if (pow.getType() == 0) {
                setState(0);
                pow.setActive(1);
                pow.setState(0);
                setInvinc(currentTime + 5000);
            } else if (pow.getType() == 1) {
                increaseLife(); // Incrementa a vida ao pegar o PowerUpHealth
                pow.setActive(1);
                pow.setState(0);
                setInvinc(currentTime + 500);
            }
        }
    }

    public void increaseLife() {
        if (this.lives < 3) {
            this.lives++;
        }
    }

    public void stateUpdate(long currentTime) {
        if (getState() == 0) {
            if (currentTime > getInvinc()) {
                setState(1);
            }
        } else if (getState() == 2) {
            if (currentTime > getExplosion_end()) {
                setState(3);
                setInvinc(currentTime + 600);
            }
        } else if (getState() == 3) {
            if (currentTime > getInvinc()) {
                setState(1);
            }
        }
    }

    // Getters e setters para `lives`
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }

    // Outros getters e setters...
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public double getX() {
        return X;
    }
    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }
    public void setY(double Y) {
        this.Y = Y;
    }

    public double getVX() {
        return VX;
    }

    public double getVY() {
        return VY;
    }

    public double getRadius() {
        return radius;
    }

    public double getExplosion_start() {
        return explosion_start;
    }
    public void setExplosion_start(double explosion_start) {
        this.explosion_start = explosion_start;
    }

    public double getExplosion_end() {
        return explosion_end;
    }
    public void setExplosion_end(double explosion_end) {
        this.explosion_end = explosion_end;
    }

    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public long getInvinc() {
        return invinc;
    }
    public void setInvinc(long invinc_end) {
        this.invinc = invinc_end;
    }
}
