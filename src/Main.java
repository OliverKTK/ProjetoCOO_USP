import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	public static final int REVIVING = 3;
	public static final int MAX_LIFE = 2;
	public static final int MAX_ENEMY_HEALTH = 1;

	public static void busyWait(long time) {
		while (System.currentTimeMillis() < time) Thread.yield();
	}

	public static int findFreeIndex(List<Integer> stateList) {
		for (int i = 0; i < stateList.size(); i++) {
			if (stateList.get(i) == INACTIVE) return i;
		}
		return stateList.size();
	}

	public static int[] findFreeIndex(List<Integer> stateList, int amount) {
		int[] freeArray = new int[amount];
		Arrays.fill(freeArray, stateList.size());

		for (int i = 0, k = 0; i < stateList.size() && k < amount; i++) {
			if (stateList.get(i) == INACTIVE) {
				freeArray[k] = i;
				k++;
			}
		}

		return freeArray;
	}

	public static void main(String[] args) {
		boolean running = true;
		boolean gameOver = false; // Flag de game over
		GameOverScreen gameOverScreen = new GameOverScreen(); // Instância da tela de Game Over
		long delta;
		long currentTime = System.currentTimeMillis();

		// Inicialização dos objetos do jogo
		Player player = new Player((double) GameLib.WIDTH / 2, GameLib.HEIGHT * 0.9, 0.25, 12, currentTime, MAX_LIFE, 3);  // Inicializa com 3 vidas
		PlayerProjectile p_projectile = new PlayerProjectile(10);

		BaseEnemy enemy1 = new BaseEnemy(10, 9, currentTime + 2000);
		EnemyProjectile e1_projectile = new EnemyProjectile(100, 2);

		Enemy2 enemy2 = new Enemy2(10, 12, currentTime + 7000, GameLib.WIDTH * 0.2);
		EnemyProjectile e2_projectile = new EnemyProjectile(100, 2);

		Enemy3 enemy3 = new Enemy3(10, 10, currentTime + 10000, MAX_ENEMY_HEALTH);
		EnemyProjectile e3_projectile = new EnemyProjectile(100, 18);

		PowerUpShield shield = new PowerUpShield(10, 10000, currentTime);
		PowerUpHealth heal = new PowerUpHealth(10, 13000, currentTime);

		Background background1 = new Background(20, 0.07, 0.0);
		Background background2 = new Background(50, 0.045, 0.0);

		p_projectile.Initialize();
		enemy1.Initialize();
		e1_projectile.Initialize();
		enemy2.Initialize();
		e2_projectile.Initialize();
		enemy3.Initialize();
		e3_projectile.Initialize();
		shield.Initialize();
		heal.Initialize();
		background1.Initialize(GameLib.WIDTH, GameLib.HEIGHT);
		background2.Initialize(GameLib.WIDTH, GameLib.HEIGHT);

		GameLib.initGraphics();

		while (running) {
			if (gameOver) {
				gameOverScreen.draw();
				if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
					running = false;
				} else if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					gameOver = false;
					// Reinicializa os objetos do jogo
					player = new Player((double) GameLib.WIDTH / 2, GameLib.HEIGHT * 0.9, 0.25, 12, System.currentTimeMillis(), MAX_LIFE, 3);
					p_projectile.Initialize();
					enemy1.Initialize();
					e1_projectile.Initialize();
					enemy2.Initialize();
					e2_projectile.Initialize();
					enemy3.Initialize();
					e3_projectile.Initialize();
					shield.Initialize();
					heal.Initialize();
					background1.Initialize(GameLib.WIDTH, GameLib.HEIGHT);
					background2.Initialize(GameLib.WIDTH, GameLib.HEIGHT);
				}
				GameLib.display();
				busyWait(currentTime + 5);
				continue;
			}

			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();

			if (player.getState() == ACTIVE) {
				player.colision(e1_projectile, currentTime, MAX_LIFE);
				player.colision(e2_projectile, currentTime, MAX_LIFE);
				player.colision(e3_projectile, currentTime, MAX_LIFE);

				player.colision(enemy1, currentTime, MAX_LIFE);
				player.colision(enemy2, currentTime, MAX_LIFE);
				player.colision(enemy3, currentTime, MAX_LIFE);
			}

			if (player.getState() == ACTIVE || player.getState() == INACTIVE) {
				player.pickPow(shield, currentTime, MAX_LIFE);
				player.pickPow(heal, currentTime, MAX_LIFE); // Aqui o player pega o power-up de vida
			}

			if (player.getState() == ACTIVE || player.getState() == INACTIVE) {
				player.pickPow(shield, currentTime, MAX_LIFE);
				player.pickPow(heal, currentTime, MAX_LIFE);
			}

			for (int k = 0; k < p_projectile.getState().size(); k++) {
				p_projectile.colision(enemy1, currentTime, k);
				p_projectile.colision(enemy2, currentTime, k);
				p_projectile.colision(enemy3, currentTime, k);
			}

			p_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e1_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e2_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e3_projectile.outOfBounds(GameLib.HEIGHT, delta);

			enemy1.behavior(player, e1_projectile, delta, findFreeIndex(e1_projectile.getState()), currentTime, GameLib.HEIGHT, GameLib.WIDTH);

			for (int i = 0; i < enemy2.getState().size(); i++) {
				if (enemy2.getState().get(i) == EXPLODING) {
					if (currentTime > enemy2.getExplosion_end().get(i)) {
						enemy2.setState(INACTIVE, i);
					}
				}

				if (enemy2.getState().get(i) == ACTIVE) {
					if (enemy2.getX().get(i) < -10 || enemy2.getX().get(i) > GameLib.WIDTH + 10) {
						enemy2.setState(INACTIVE, i);
					} else {
						boolean shootNow = false;
						double previousY = enemy2.getY().get(i);

						enemy2.setX(enemy2.getX().get(i) + enemy2.getV().get(i) * Math.cos(enemy2.getAngle().get(i)) * delta, i);
						enemy2.setY(enemy2.getY().get(i) + enemy2.getV().get(i) * Math.sin(enemy2.getAngle().get(i)) * delta * (-1.0), i);
						enemy2.setAngle(enemy2.getAngle().get(i) + enemy2.getRV().get(i) * delta, i);

						double threshold = GameLib.HEIGHT * 0.30;
						if (previousY < threshold && enemy2.getY().get(i) >= threshold) {
							if (enemy2.getX().get(i) < (double) GameLib.WIDTH / 2) enemy2.setRV(0.003, i);
							else enemy2.setRV(-0.003, i);
						}

						if (enemy2.getRV().get(i) > 0 && Math.abs(enemy2.getAngle().get(i) - 3 * Math.PI) < 0.05) {
							enemy2.setRV(0, i);
							enemy2.setAngle(3 * Math.PI, i);
							shootNow = true;
						}

						if (enemy2.getRV().get(i) < 0 && Math.abs(enemy2.getAngle().get(i)) < 0.05) {
							enemy2.setRV(0.0, i);
							enemy2.setAngle(0, i);
							shootNow = true;
						}

						if (shootNow) {
							double[] angles = {Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8};
							int[] freeArray = findFreeIndex(e2_projectile.getState(), angles.length);
							for (int k = 0; k < freeArray.length; k++) {
								int free = freeArray[k];
								if (free < e2_projectile.getState().size()) {
									double a = angles[k] + Math.random() * Math.PI / 6 - Math.PI / 12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									e2_projectile.setX(enemy2.getX().get(i), free);
									e2_projectile.setY(enemy2.getY().get(i), free);
									e2_projectile.setVX(vx * 0.30, free);
									e2_projectile.setVY(vy * 0.30, free);
									e2_projectile.setState(1, free);
								}
							}
						}
					}
				}
			}

			enemy3.behavior(player, e3_projectile, delta, findFreeIndex(e3_projectile.getState()), currentTime, GameLib.HEIGHT, GameLib.WIDTH);

			shield.behavior(delta, currentTime, GameLib.HEIGHT);
			heal.behavior(delta, currentTime, GameLib.HEIGHT);

			enemy1.newEnemy(currentTime, findFreeIndex(enemy1.getState()), 500, 500, 0);
			enemy2.newEnemy(currentTime, findFreeIndex(enemy2.getState()), 3000, 3000);
			enemy3.newEnemy(currentTime, findFreeIndex(enemy3.getState()), 500, 4000, 2000);

			if (player.getLife() != MAX_LIFE) {
				if (Math.random() > 0.5) {
					shield.newPow(currentTime, 15000, 7000);
				}
				heal.newPow(currentTime, 15000, 5000);
			} else {
				shield.newPow(currentTime, 15000, 7000);
			}

			player.stateUpdate(currentTime);
			if (currentTime > player.getInvinc()) {
				shield.setActive(0);
				heal.setActive(0);
			}

			if (player.getState() != EXPLODING) {
				double alpha = 1;
				if (player.getState() == INACTIVE && shield.getActive() == 0) {
					alpha = 1.85;
				}

				if (GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta * player.getVY() / alpha);
				if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta * player.getVY() / alpha);
				if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta * player.getVX() / alpha);
				if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta * player.getVX() / alpha);
				if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					if (currentTime > player.getNextShot()) {
						int free = findFreeIndex(p_projectile.getState());
						if (free < p_projectile.getState().size()) {
							p_projectile.setX(player.getX(), free);
							p_projectile.setY(player.getY() - 2 * player.getRadius(), free);
							p_projectile.setVX(0.0, free);
							p_projectile.setVY(-1.0, free);
							p_projectile.setState(1, free);
							player.setNextShot((long) (currentTime + 100 * alpha));
						}
					}
				}
			}

			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			if (player.getX() < 0.0) player.setX(0.0);
			if (player.getX() >= GameLib.WIDTH) player.setX(GameLib.WIDTH - 1);
			if (player.getY() < 25.0) player.setY(25.0);
			if (player.getY() >= GameLib.HEIGHT) player.setY(GameLib.HEIGHT - 1);

			GameLib.setColor(Color.DARK_GRAY);
			background2.setCount(background2.getCount() + background2.getSpeed() * delta);
			for (int i = 0; i < background2.getX().size(); i++) {
				GameLib.fillRect(background2.getX().get(i), (background2.getY().get(i) + background2.getCount()) % GameLib.HEIGHT, 2, 2);
			}

			GameLib.setColor(Color.GRAY);
			background1.setCount(background1.getCount() + background1.getSpeed() * delta);
			for (int i = 0; i < background1.getX().size(); i++) {
				GameLib.fillRect(background1.getX().get(i), (background1.getY().get(i) + background1.getCount()) % GameLib.HEIGHT, 3, 3);
			}

			if (shield.getState() == ACTIVE) {
				GameLib.setColor(Color.blue);
				GameLib.drawCircle(shield.getX(), shield.getY(), shield.getRadius());
				GameLib.drawDiamond(shield.getX(), shield.getY(), shield.getRadius() - 3);
			}

			if (heal.getState() == ACTIVE) {
				GameLib.setColor(Color.GREEN);
				GameLib.drawDiamond(heal.getX(), heal.getY(), heal.getRadius());
				GameLib.drawDiamond(heal.getX(), heal.getY(), heal.getRadius() - 6);
			}

			if (player.getState() == EXPLODING) {
				double alpha = (currentTime - player.getExplosion_start()) / (player.getExplosion_end() - player.getExplosion_start());
				GameLib.drawExplosion(player.getX(), player.getY(), alpha);
			} else if (player.getState() == ACTIVE) {
				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			} else if (player.getState() == INACTIVE) {
				if (shield.getActive() == 1) {
					GameLib.setColor(Color.BLUE);
					GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
					GameLib.drawCircle(player.getX(), player.getY(), player.getRadius() + 5);
				} else {
					GameLib.setColor(Color.ORANGE);
					GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
				}
			} else if (player.getState() == REVIVING) {
				GameLib.setColor(Color.GREEN);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			}

			for (int i = 0; i < p_projectile.getState().size(); i++) {
				if (p_projectile.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(p_projectile.getX().get(i), p_projectile.getY().get(i) - 5, p_projectile.getX().get(i), p_projectile.getY().get(i) + 5);
					GameLib.drawLine(p_projectile.getX().get(i) - 1, p_projectile.getY().get(i) - 3, p_projectile.getX().get(i) - 1, p_projectile.getY().get(i) + 3);
					GameLib.drawLine(p_projectile.getX().get(i) + 1, p_projectile.getY().get(i) - 3, p_projectile.getX().get(i) + 1, p_projectile.getY().get(i) + 3);
				}
			}

			for (int i = 0; i < e1_projectile.getState().size(); i++) {
				if (e1_projectile.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e1_projectile.getX().get(i), e1_projectile.getY().get(i), e1_projectile.getRadius());
				}
			}

			for (int i = 0; i < e2_projectile.getState().size(); i++) {
				if (e2_projectile.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e2_projectile.getX().get(i), e2_projectile.getY().get(i), e2_projectile.getRadius());
				}
			}

			for (int i = 0; i < e3_projectile.getState().size(); i++) {
				if (e3_projectile.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e3_projectile.getX().get(i), e3_projectile.getY().get(i), e3_projectile.getRadius());
				}
			}

			for (int i = 0; i < enemy1.getState().size(); i++) {
				if (enemy1.getState().get(i) == EXPLODING) {
					double alpha = (currentTime - enemy1.getExplosion_start().get(i)) / (enemy1.getExplosion_end().get(i) - enemy1.getExplosion_start().get(i));
					GameLib.drawExplosion(enemy1.getX().get(i), enemy1.getY().get(i), alpha);
				}

				if (enemy1.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1.getX().get(i), enemy1.getY().get(i), enemy1.getRadius());
				}
			}

			for (int i = 0; i < enemy2.getState().size(); i++) {
				if (enemy2.getState().get(i) == EXPLODING) {
					double alpha = (currentTime - enemy2.getExplosion_start().get(i)) / (enemy2.getExplosion_end().get(i) - enemy2.getExplosion_start().get(i));
					GameLib.drawExplosion(enemy2.getX().get(i), enemy2.getY().get(i), alpha);
				}

				if (enemy2.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2.getX().get(i), enemy2.getY().get(i), enemy2.getRadius());
				}
			}

			for (int i = 0; i < enemy3.getState().size(); i++) {
				if (enemy3.getState().get(i) == EXPLODING) {
					double alpha = (currentTime - enemy3.getExplosion_start().get(i)) / (enemy3.getExplosion_end().get(i) - enemy3.getExplosion_start().get(i));
					GameLib.drawExplosion(enemy3.getX().get(i), enemy3.getY().get(i), alpha);
				}

				if (enemy3.getState().get(i) == REVIVING) {
					GameLib.setColor(Color.PINK);
					GameLib.drawDiamond(enemy3.getX().get(i), enemy3.getY().get(i), enemy3.getRadius());
				}

				if (enemy3.getState().get(i) == ACTIVE) {
					GameLib.setColor(Color.YELLOW);
					GameLib.drawCircle(enemy3.getX().get(i), enemy3.getY().get(i), enemy3.getRadius());
				}
			}

			if (player.getLives() == 3) {
				GameLib.setColor(Color.green);
				GameLib.drawDiamond(GameLib.WIDTH - 30, GameLib.HEIGHT - 30, 7);
				GameLib.drawDiamond(GameLib.WIDTH - 40, GameLib.HEIGHT - 40, 7);
				GameLib.drawDiamond(GameLib.WIDTH - 50, GameLib.HEIGHT - 30, 7);
			} else if (player.getLives() == 2) {
				GameLib.setColor(Color.orange);
				GameLib.drawDiamond(GameLib.WIDTH - 29, GameLib.HEIGHT - 20, 9);
				GameLib.drawDiamond(GameLib.WIDTH - 51, GameLib.HEIGHT - 20, 9);
			} else if (player.getLives() == 1) {
				GameLib.setColor(Color.red);
				GameLib.drawDiamond(GameLib.WIDTH - 40, GameLib.HEIGHT - 30, 11);
			} else {
				gameOver = true; // Ativa a flag de game over
			}

			GameLib.display();
			busyWait(currentTime + 5);
		}

		System.exit(0);
	}
}
