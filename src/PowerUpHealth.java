public class PowerUpHealth extends PowerUp {

    public PowerUpHealth(int numOfPowerUps, long spawnTime, long currentTime) {
        super(1, 10.0, spawnTime, currentTime); // Passa os valores corretos para o construtor da classe pai
    }

    @Override
    public void behavior(long delta, long currentTime, int height) {
        super.behavior(delta, currentTime, height);
    }
}
