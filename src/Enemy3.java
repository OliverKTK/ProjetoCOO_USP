public class Enemy3 extends BaseEnemy {
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
