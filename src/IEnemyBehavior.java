import java.util.List;

public interface IEnemyBehavior {
    void behavior(Player player, PlayerProjectile e, long delta, int free, long currentTime, int height, int width);
}
