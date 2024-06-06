import java.awt.Color;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (‘player’, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	public static final int REVIVING = 3;
	public static final int MAX_LIFE = 2;
	public static final int MAX_ENEMY_HEALTH = 1;


	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "tempo".    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	
	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */ 

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){

			if(stateArray[i] == INACTIVE) {

				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */

		long delta;
		long currentTime = System.currentTimeMillis();

		/* variáveis do player */

		Player player = new Player((double) GameLib.WIDTH /2, GameLib.HEIGHT*0.9, 0.25, 12, currentTime, MAX_LIFE);
		PlayerProjectile p_projectile = new PlayerProjectile(10);

		/* variáveis dos inimigos tipo 1 */
		BaseEnemy enemy1 = new BaseEnemy(10,9, currentTime+2000);
		EnemyProjectile e1_projectile = new EnemyProjectile(100, 2);

		/* variáveis dos inimigos tipo 2 */
		Enemy2 enemy2 = new Enemy2(10,12, currentTime+7000, GameLib.WIDTH*0.2);
		EnemyProjectile e2_projectile = new EnemyProjectile(100, 2);

		/* variáveis dos inimigos tipo 3 */
		Enemy3 enemy3 = new Enemy3(10, 10, currentTime+10000, MAX_ENEMY_HEALTH);
		EnemyProjectile e3_projectile = new EnemyProjectile(100, 18);

		/* variaveis do powerup */
		PowerUpShield shield = new PowerUpShield(10, 10000, currentTime);
		PowerUpHealth heal = new PowerUpHealth(10, 10000, currentTime);

		/* estrelas que formam o fundo de primeiro plano */
		Background background1 = new Background(20, 0.07, 0.0);

		/* estrelas que formam o fundo de segundo plano */
		Background background2 = new Background(50, 0.045, 0.0);

		/* inicializações */
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

		/* iniciado interface gráfica */

		GameLib.initGraphics();

        /*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */

        while(running){

			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */

			delta = System.currentTimeMillis() - currentTime;

			/* Já a variável "currentTime" nos dá o timestamp atual.  */

			currentTime = System.currentTimeMillis();

            /* Verificação de colisões */

            if(player.getState() == ACTIVE){

				/* colisões player - projeteis (inimigo) */
				player.colision(e1_projectile, currentTime, MAX_LIFE);
				player.colision(e2_projectile, currentTime, MAX_LIFE);
				player.colision(e3_projectile, currentTime, MAX_LIFE);

				/* colisões player - inimigos */
				player.colision(enemy1, currentTime, MAX_LIFE);
				player.colision(enemy2, currentTime, MAX_LIFE);
				player.colision(enemy3, currentTime, MAX_LIFE);
			}

			if(player.getState() == ACTIVE || player.getState() == INACTIVE){
				/* colisoes player - powerup */
				player.pickPow(shield, currentTime, MAX_LIFE);
				player.pickPow(heal, currentTime, MAX_LIFE);
			}

			/* colisões projeteis (player) - inimigos */

			for(int k = 0; k < p_projectile.getState().length; k++){
				p_projectile.colision(enemy1, currentTime, k);
				p_projectile.colision(enemy2, currentTime, k);
				p_projectile.colision(enemy3, currentTime, k);
			}

            /* Atualizações de estados */

            /* projeteis (player) */

			p_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e1_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e2_projectile.outOfBounds(GameLib.HEIGHT, delta);
			e3_projectile.outOfBounds(GameLib.HEIGHT, delta);

			/* inimigos tipo 1 */
			enemy1.behavior(player,e1_projectile, delta, findFreeIndex(e1_projectile.getState()), currentTime, GameLib.HEIGHT, GameLib.WIDTH);
			
			/* inimigos tipo 2 */
			
			for(int i = 0; i < enemy2.getState().length; i++){
				if(enemy2.getState()[i] == EXPLODING){
					if(currentTime > enemy2.getExplosion_end()[i]){
						enemy2.setState(INACTIVE, i);
					}
				}
				
				if(enemy2.getState()[i] == ACTIVE){
					/* verificando se inimigo saiu da tela */
					if(	enemy2.getX()[i] < -10 || enemy2.getX()[i] > GameLib.WIDTH + 10 ) {
						enemy2.setState(INACTIVE, i);
					}
					else {
						
						boolean shootNow = false;
						double previousY = enemy2.getY()[i];

						enemy2.setX(enemy2.getX()[i] + enemy2.getV()[i]*Math.cos(enemy2.getAngle()[i])*delta, i);
						enemy2.setY(enemy2.getY()[i] + enemy2.getV()[i]*Math.sin(enemy2.getAngle()[i])*delta*(-1.0), i);
						enemy2.setAngle(enemy2.getAngle()[i] + enemy2.getRV()[i]*delta, i);

						double threshold = GameLib.HEIGHT * 0.30;
						if(previousY < threshold && enemy2.getY()[i] >= threshold) {
							if(enemy2.getX()[i] < (double) GameLib.WIDTH / 2) enemy2.setRV(0.003, i);
							else enemy2.setRV(-0.003, i);
						}
						
						if(enemy2.getRV()[i] > 0 && Math.abs(enemy2.getAngle()[i] - 3 * Math.PI) < 0.05){

							enemy2.setRV(0, i);
							enemy2.setAngle(3*Math.PI, i);
							shootNow = true;
						}
						
						if(enemy2.getRV()[i] < 0 && Math.abs(enemy2.getAngle()[i]) < 0.05){

							enemy2.setRV(0.0, i);
							enemy2.setAngle(0, i);
							shootNow = true;
						}
																		
						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							int [] freeArray = findFreeIndex(e2_projectile.getState(), angles.length);
							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < e2_projectile.getState().length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									e2_projectile.setX(enemy2.getX()[i], free);
									e2_projectile.setY(enemy2.getY()[i], free);
									e2_projectile.setVX(vx * 0.30,free);
									e2_projectile.setVY(vy * 0.30,free);
									e2_projectile.setState(1, free);
								}
							}
						}
					}
				}
			}

			/* inimigos tipo 3 */
			enemy3.behavior(player,e3_projectile, delta, findFreeIndex(e3_projectile.getState()), currentTime, GameLib.HEIGHT, GameLib.WIDTH);

			/* powerup */
			shield.behavior(delta, currentTime, GameLib.HEIGHT);
			heal.behavior(delta, currentTime, GameLib.HEIGHT);

			/* verificando se novos inimigos devem ser "lançados" */
			enemy1.newEnemy(currentTime, findFreeIndex(enemy1.getState()), 500, 500, 0);
			enemy2.newEnemy(currentTime, findFreeIndex(enemy2.getState()), 3000, 3000);
			enemy3.newEnemy(currentTime, findFreeIndex(enemy3.getState()), 500, 4000, 2000);

			if(player.getLife() != MAX_LIFE){
				if(Math.random() > 0.5){
					shield.newPow(currentTime, 15000, 7000);
				}
				heal.newPow(currentTime, 15000, 5000);
			}
			else{
				shield.newPow(currentTime, 15000, 7000);
			}



			/* Verificando mudanca de estado do player */
			player.stateUpdate(currentTime);
			if(currentTime > player.getInvinc()){
				shield.setActive(0);
				heal.setActive(0);
			}


            /* Verificando entrada do usuário (teclado) */

            if(player.getState() != EXPLODING){
				double alpha = 1;
				if(player.getState() == INACTIVE && shield.getActive() == 0){
					alpha = 1.85;
				}
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta* player.getVY()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta* player.getVY()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta* player.getVX()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta* player.getVX()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					if(currentTime > player.getNextShot()){
						
						int free = findFreeIndex(p_projectile.getState());
												
						if(free < p_projectile.getState().length){

							p_projectile.setX(player.getX(), free);
							p_projectile.setY(player.getY()- 2*player.getRadius(), free);
							p_projectile.setVX(0.0, free);
							p_projectile.setVY(-1.0, free);
							p_projectile.setState(1, free);
							player.setNextShot((long)(currentTime+ 100*
									alpha));
						}
					}	
				}
			}


			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */
			
			if(player.getX() < 0.0) player.setX(0.0);
			if(player.getX() >= GameLib.WIDTH) player.setX(GameLib.WIDTH - 1);
			if(player.getY() < 25.0) player.setY(25.0);
			if(player.getY() >= GameLib.HEIGHT) player.setY(GameLib.HEIGHT - 1);

            /* Desenho da cena */

            /* desenhando plano fundo distante */
			
			GameLib.setColor(Color.DARK_GRAY);
			background2.setCount(background2.getCount() + background2.getSpeed()*delta);
			
			for(int i = 0; i < background2.getX().length; i++){
				
				GameLib.fillRect(background2.getX()[i], (background2.getY()[i] + background2.getCount()) % GameLib.HEIGHT, 2, 2);
			}
			
			/* desenhando plano de fundo próximo */
			
			GameLib.setColor(Color.GRAY);
			background1.setCount(background1.getCount() + background1.getSpeed()*delta);
			
			for(int i = 0; i < background1.getX().length; i++){
				
				GameLib.fillRect(background1.getX()[i], (background1.getY()[i] + background1.getCount()) % GameLib.HEIGHT, 3, 3);
			}

			/* desenahndo os powerUps */

			if(shield.getState() == ACTIVE) {
				GameLib.setColor(Color.blue);
				GameLib.drawCircle(shield.getX(), shield.getY(), shield.getRadius());
				GameLib.drawDiamond(shield.getX(), shield.getY(), shield.getRadius() - 3);
			}

			if(heal.getState() == ACTIVE){
				GameLib.setColor(Color.GREEN);
				GameLib.drawDiamond(heal.getX(), heal.getY(), heal.getRadius());
				GameLib.drawDiamond(heal.getX(), heal.getY(), heal.getRadius() - 6);
			}

			/* desenhando player */


			if(player.getState() == EXPLODING){
				
				double alpha = (currentTime - player.getExplosion_start()) / (player.getExplosion_end() - player.getExplosion_start());
				GameLib.drawExplosion(player.getX(), player.getY(), alpha);
			}
			else if(player.getState() == ACTIVE){
				
				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			}
			else if(player.getState() == INACTIVE){
				if(shield.getActive() == 1){
					GameLib.setColor(Color.BLUE);
					GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
					GameLib.drawCircle(player.getX(), player.getY(), player.getRadius()+5);
				}
				else{
					GameLib.setColor(Color.ORANGE);
					GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
				}
			}
			else if(player.getState() == REVIVING){
				GameLib.setColor(Color.GREEN);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			}
				
			
			/* deenhando projeteis (player) */
			
			for(int i = 0; i < p_projectile.getState().length; i++){
				
				if(p_projectile.getState()[i] == ACTIVE){
					
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(p_projectile.getX()[i], p_projectile.getY()[i] - 5, p_projectile.getX()[i], p_projectile.getY()[i] + 5);
					GameLib.drawLine(p_projectile.getX()[i] - 1, p_projectile.getY()[i] - 3, p_projectile.getX()[i] - 1, p_projectile.getY()[i] + 3);
					GameLib.drawLine(p_projectile.getX()[i] + 1, p_projectile.getY()[i] - 3, p_projectile.getX()[i] + 1, p_projectile.getY()[i] + 3);
				}
			}
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < e1_projectile.getState().length; i++){

				if(e1_projectile.getState()[i] == ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e1_projectile.getX()[i], e1_projectile.getY()[i], e1_projectile.getRadius());
				}
			}

			for(int i = 0; i < e2_projectile.getState().length; i++){

				if(e2_projectile.getState()[i] == ACTIVE){

					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e2_projectile.getX()[i], e2_projectile.getY()[i], e2_projectile.getRadius());
				}
			}

			for(int i = 0; i < e3_projectile.getState().length; i++){

				if(e3_projectile.getState()[i] == ACTIVE){

					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e3_projectile.getX()[i], e3_projectile.getY()[i], e3_projectile.getRadius());
				}
			}

			/* desenhando inimigos (tipo 1) */
			
			for(int i = 0; i < enemy1.getState().length; i++){
				
				if(enemy1.getState()[i] == EXPLODING){
					
					double alpha = (currentTime - enemy1.getExplosion_start()[i]) / (enemy1.getExplosion_end()[i] - enemy1.getExplosion_start()[i]);
					GameLib.drawExplosion(enemy1.getX()[i], enemy1.getY()[i], alpha);
				}
				
				if(enemy1.getState()[i] == ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1.getX()[i], enemy1.getY()[i], enemy1.getRadius());
				}
			}
			
			/* desenhando inimigos (tipo 2) */
			
			for(int i = 0; i < enemy2.getState().length; i++){
				
				if(enemy2.getState()[i] == EXPLODING){
					
					double alpha = (currentTime - enemy2.getExplosion_start()[i]) / (enemy2.getExplosion_end()[i] - enemy2.getExplosion_start()[i]);
					GameLib.drawExplosion(enemy2.getX()[i], enemy2.getY()[i], alpha);
				}
				
				if(enemy2.getState()[i] == ACTIVE){
			
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2.getX()[i], enemy2.getY()[i], enemy2.getRadius());
				}
			}

			/* desenhando inimigos (tipo 3) */

			for(int i = 0; i < enemy3.getState().length; i++){

				if(enemy3.getState()[i] == EXPLODING){
					double alpha = (currentTime - enemy3.getExplosion_start()[i]) / (enemy3.getExplosion_end()[i] - enemy3.getExplosion_start()[i]);
					GameLib.drawExplosion(enemy3.getX()[i], enemy3.getY()[i], alpha);
				}

				if(enemy3.getState()[i] == REVIVING){
					GameLib.setColor(Color.PINK);
					GameLib.drawDiamond(enemy3.getX()[i], enemy3.getY()[i], enemy3.getRadius());
				}

				if(enemy3.getState()[i] == ACTIVE){
					GameLib.setColor(Color.YELLOW);
					GameLib.drawCircle(enemy3.getX()[i], enemy3.getY()[i], enemy3.getRadius());
				}
			}

			/* desenhando o número de vidas do player */

			if(player.getLife() == MAX_LIFE){
				GameLib.setColor(Color.green);
				GameLib.drawDiamond(GameLib.WIDTH - 30, GameLib.HEIGHT - 30, 7);
				GameLib.drawDiamond(GameLib.WIDTH - 40, GameLib.HEIGHT - 40, 7);
				GameLib.drawDiamond(GameLib.WIDTH - 50, GameLib.HEIGHT - 30, 7);
			}
			else if(player.getLife() >= (2*MAX_LIFE)/3){
				GameLib.setColor(Color.orange);
				GameLib.drawDiamond(GameLib.WIDTH - 29, GameLib.HEIGHT - 20, 9);
				GameLib.drawDiamond(GameLib.WIDTH - 51, GameLib.HEIGHT - 20, 9);
			}
			else if(player.getLife() >= MAX_LIFE/3){
				GameLib.setColor(Color.red);
				GameLib.drawDiamond(GameLib.WIDTH - 40, GameLib.HEIGHT - 30, 11);
			}

			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
