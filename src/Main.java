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
		Enemy1 enemy1 = new Enemy1(10,9, currentTime+2000);

		/* variáveis dos inimigos tipo 2 */
		Enemy2 enemy2 = new Enemy2(10,12, currentTime+7000, GameLib.WIDTH*0.2);

		Enemy3 enemy3 = new Enemy3(10, 7, currentTime+10000, MAX_ENEMY_HEALTH);

		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		EnemyProjectile e_projectile = new EnemyProjectile(200, 2);
		EnemyProjectile e3_projectile = new EnemyProjectile(100, 2);

		/* estrelas que formam o fundo de primeiro plano */
		Background background1 = new Background(20, 0.07, 0.0);

		/* estrelas que formam o fundo de segundo plano */
		Background background2 = new Background(50, 0.045, 0.0);

		/* inicializações */
		p_projectile.Initialize();
		e_projectile.Initialize();
		e3_projectile.Initialize();
		enemy1.Initialize();
		enemy2.Initialize();
		enemy3.Initialize();

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

				for(int i = 0; i < e_projectile.getState().length; i++){

					double dx = e_projectile.getX()[i] - player.getX();
					double dy = e_projectile.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.getRadius() + e_projectile.getRadius()) * 0.8){
						if(player.getLife() > 0){
							player.setLife(player.getLife()-1);
							player.setState(INACTIVE);
							player.setInvinc(currentTime+500);

						}
						else{
							player.setLife(MAX_LIFE);
							player.setState(EXPLODING);
							player.setExplosion_start(currentTime);
							player.setExplosion_end(currentTime+2000);


						}
					}
				}

				for(int i = 0; i < e3_projectile.getState().length; i++){

					double dx = e3_projectile.getX()[i] - player.getX();
					double dy = e3_projectile.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.getRadius() + e3_projectile.getRadius()) * 0.8){
						if(player.getLife() > 0){
							player.setLife(player.getLife()-1);
							player.setState(INACTIVE);
							player.setInvinc(currentTime+500);

						}
						else{
							player.setLife(MAX_LIFE);
							player.setState(EXPLODING);
							player.setExplosion_start(currentTime);
							player.setExplosion_end(currentTime+2000);


						}
					}
				}

				/* colisões player - inimigos */

				for(int i = 0; i < enemy1.getState().length; i++){

					double dx = enemy1.getX()[i] - player.getX();
					double dy = enemy1.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.getRadius() + enemy1.getRadius()) * 0.8){
						if(player.getLife() > 0){
							player.setLife(player.getLife()-1);
							player.setState(INACTIVE);
							player.setInvinc(currentTime+500);

						}
						else{
							player.setLife(MAX_LIFE);
							player.setState(EXPLODING);
							player.setExplosion_start(currentTime);
							player.setExplosion_end(currentTime+2000);
						}
					}
				}

				for(int i = 0; i < enemy2.getState().length; i++){

					double dx = enemy2.getX()[i] - player.getX();
					double dy = enemy2.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.getRadius() + enemy2.getRadius()) * 0.8){
						if(player.getLife() > 0){
							player.setLife(player.getLife()-1);
							player.setState(INACTIVE);
							player.setInvinc(currentTime+500);

						}
						else{
							player.setLife(MAX_LIFE);
							player.setState(EXPLODING);
							player.setExplosion_start(currentTime);
							player.setExplosion_end(currentTime+2000);
						}

					}
				}

				for(int i = 0; i < enemy3.getState().length; i++){

					double dx = enemy3.getX()[i] - player.getX();
					double dy = enemy3.getY()[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.getRadius() + enemy3.getRadius()) * 0.8){
						if(player.getLife() > 0){
							player.setLife(player.getLife()-1);
							player.setState(REVIVING);
							player.setInvinc(currentTime+500);

						}
						else{
							player.setLife(MAX_LIFE);
							player.setState(EXPLODING);
							player.setExplosion_start(currentTime);
							player.setExplosion_end(currentTime+2000);
						}

					}
				}
			}

			/* colisões projeteis (player) - inimigos */

			for(int k = 0; k < p_projectile.getState().length; k++){

				for(int i = 0; i < enemy1.getState().length; i++){

					if(enemy1.getState()[i] == ACTIVE){

						double dx = enemy1.getX()[i] - p_projectile.getX()[k];
						double dy = enemy1.getY()[i] - p_projectile.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);

						if(dist < enemy1.getRadius()){

							enemy1.setState(EXPLODING, i);
							enemy1.setExplosion_start(currentTime,i);
							enemy1.setExplosion_end(currentTime+500,i);
						}
					}
				}

				for(int i = 0; i < enemy2.getState().length; i++){

					if(enemy2.getState()[i] == ACTIVE){

						double dx = enemy2.getX()[i] - p_projectile.getX()[k];
						double dy = enemy2.getY()[i] - p_projectile.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);

						if(dist < enemy2.getRadius()){

							enemy2.setState(EXPLODING, i);
							enemy2.setExplosion_start(currentTime, i);
							enemy2.setExplosion_end(currentTime+ 500, i);
						}
					}
				}

				for(int i = 0; i < enemy3.getState().length; i++){

					if(enemy3.getState()[i] == ACTIVE){

						double dx = enemy3.getX()[i] - p_projectile.getX()[k];
						double dy = enemy3.getY()[i] - p_projectile.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);

						if(dist < enemy3.getRadius()){

							if(enemy3.getHealth()[i] > 0){
								enemy3.setHealth(enemy3.getHealth()[i]-1, i);
								enemy3.setState(REVIVING, i);
								enemy3.setExplosion_end(currentTime+ 300, i);
							}
							else{
								enemy3.setState(EXPLODING, i);
								enemy3.setExplosion_start(currentTime, i);
								enemy3.setExplosion_end(currentTime+ 500, i);
							}
						}
					}
				}
			}

            /* Atualizações de estados */

            /* projeteis (player) */
			
			for(int i = 0; i < p_projectile.getState().length; i++){

				if(p_projectile.getState()[i] == ACTIVE){

					/* verificando se projétil saiu da tela */
					if(p_projectile.getY()[i] < 0) {

						p_projectile.setState(INACTIVE, i);
					}
					else {

						p_projectile.setX(p_projectile.getX()[i]+ p_projectile.getVX()[i] *delta, i);
						p_projectile.setY(p_projectile.getY()[i]+ p_projectile.getVY()[i] *delta, i);
					}
				}
			}
			
			/* projeteis (inimigos) */
			
			for(int i = 0; i < e_projectile.getState().length; i++){
				
				if(e_projectile.getState()[i] == ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(e_projectile.getY()[i] > GameLib.HEIGHT) {

						e_projectile.setState(INACTIVE, i);
					}
					else {

						e_projectile.setX(e_projectile.getX()[i]+ e_projectile.getVX()[i] *delta, i);
						e_projectile.setY(e_projectile.getY()[i]+ e_projectile.getVY()[i] *delta, i);
					}
				}
			}

			for(int i = 0; i < e3_projectile.getState().length; i++){

				if(e3_projectile.getState()[i] == ACTIVE){

					/* verificando se projétil saiu da tela */
					if(e3_projectile.getY()[i] > GameLib.HEIGHT) {

						e3_projectile.setState(INACTIVE, i);
					}
					else {

						e3_projectile.setX(e3_projectile.getX()[i]+ e3_projectile.getVX()[i] *delta, i);
						e3_projectile.setY(e3_projectile.getY()[i]+ e3_projectile.getVY()[i] *delta, i);
					}
				}
			}
			
			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemy1.getState().length; i++){
				
				if(enemy1.getState()[i] == EXPLODING){
					
					if(currentTime > enemy1.getExplosion_end()[i]){

						enemy1.setState(INACTIVE, i);
					}
				}
				
				if(enemy1.getState()[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemy1.getY()[i] > GameLib.HEIGHT + 10) {

						enemy1.setState(INACTIVE, i);
					}
					else {

						enemy1.setX(enemy1.getX()[i] + enemy1.getV()[i] * Math.cos(enemy1.getAngle()[i]) * delta, i);
						enemy1.setY(enemy1.getY()[i] + enemy1.getV()[i] * Math.sin(enemy1.getAngle()[i]) * delta * (-1.0), i);
						enemy1.setAngle(enemy1.getAngle()[i] + enemy1.getRV()[i]*delta, i);
						
						if(currentTime > enemy1.getNextShoot()[i] && enemy1.getY()[i] < player.getY()){
																							
							int free = findFreeIndex(e_projectile.getState());
							
							if(free < e_projectile.getState().length){

								e_projectile.setX(enemy1.getX()[i], free);
								e_projectile.setY(enemy1.getY()[i], free);
								e_projectile.setVX(Math.cos(enemy1.getAngle()[i]) * 0.45,free);
								e_projectile.setVY(Math.sin(enemy1.getAngle()[i]) * 0.45 * (-1.0),free);
								e_projectile.setState(1, free);

								enemy1.setNextShoot((long) (currentTime + 200 + Math.random() * 500), i);
							}
						}
					}
				}
			}
			
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
							int [] freeArray = findFreeIndex(e_projectile.getState(), angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < e_projectile.getState().length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									e_projectile.setX(enemy2.getX()[i], free);
									e_projectile.setY(enemy2.getY()[i], free);
									e_projectile.setVX(vx * 0.30,free);
									e_projectile.setVY(vy * 0.30,free);
									e_projectile.setState(1, free);
								}
							}
						}
					}
				}
			}

			/* inimigos tipo 3 */

			for(int i = 0; i < enemy3.getState().length; i++){

				if(enemy3.getState()[i] == REVIVING){
					if(currentTime > enemy3.getExplosion_end()[i]){

						enemy3.setState(ACTIVE, i);
					}
				}

				else if(enemy3.getState()[i] == EXPLODING){

					if(currentTime > enemy3.getExplosion_end()[i]){

						enemy3.setState(INACTIVE, i);
						enemy3.setHealth(enemy3.getMaxHealth(), i);
					}
				}

				else if(enemy3.getState()[i] == ACTIVE){
					if(enemy3.getY()[i] <  Math.random() * GameLib.HEIGHT * 0.25){
						enemy3.setY(enemy3.getY()[i] + enemy3.getV()[i] * Math.sin(enemy3.getAngle()[i]) * delta * (-1.0), i);
					}
					enemy3.setX(enemy3.getX()[i] + enemy3.getV()[i] * Math.cos(enemy3.getAngle()[i]) * delta, i);
					enemy3.setAngle(enemy3.getAngle()[i] + enemy3.getRV()[i]*delta, i);

					if(currentTime > enemy3.getNextShoot()[i] && enemy3.getY()[i] < player.getY()){

						int free = findFreeIndex(e3_projectile.getState());

						if(free < e3_projectile.getState().length){

							e3_projectile.setX(enemy3.getX()[i], free);
							e3_projectile.setY(enemy3.getY()[i], free);
							e3_projectile.setVX(Math.cos(enemy3.getAngle()[i]) * 0.45,free);
							e3_projectile.setVY(Math.sin(enemy3.getAngle()[i]) * 0.45 * (-1.0),free);
							e3_projectile.setState(1, free);

							enemy3.setNextShoot((long) (currentTime + 300 + Math.random() * 500), i);

						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
			if(currentTime > enemy1.getNextEnemy()){
				
				int free = findFreeIndex(enemy1.getState());
								
				if(free < enemy1.getState().length){

					enemy1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0,free);
					enemy1.setY(-10.0,free);
					enemy1.setV(0.20 + Math.random() * 0.15, free);
					enemy1.setAngle(3 * Math.PI / 2, free);
					enemy1.setRV(0, free);
					enemy1.setState(ACTIVE, free);
					enemy1.setNextShoot(currentTime+500, free);
					enemy1.setNextEnemy(currentTime+500);
				}
			}
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			
			if(currentTime > enemy2.getNextEnemy()){
				
				int free = findFreeIndex(enemy2.getState());
								
				if(free < enemy2.getState().length){

					enemy2.setX(enemy2.getSpawnX(), free);
					enemy2.setY(-10.0, free);
					enemy2.setV(0.42, free);
					enemy2.setAngle((3*Math.PI)/2, free);
					enemy2.setRV(0.0, free);
					enemy2.setState(ACTIVE, free);

					enemy2.setCount(enemy2.getCount()+1);
					
					if(enemy2.getCount() < 10){

						enemy2.setNextEnemy(currentTime + 120);
					}
					else {

						enemy2.setCount(0);
						enemy2.setSpawnX(Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
						enemy2.setNextEnemy((long) (currentTime + 3000 + Math.random() * 3000));
					}
				}
			}

			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */

			if(currentTime > enemy3.getNextEnemy()){

				int free = findFreeIndex(enemy3.getState());

				if(free < enemy3.getState().length){

					enemy3.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0,free);
					enemy3.setY(-10.0,free);
					enemy3.setV(0.20 + Math.random() * 0.15, free);
					enemy3.setAngle(3 * Math.PI / 2, free);
					enemy3.setRV(0, free);
					enemy3.setState(ACTIVE, free);
					enemy3.setNextShoot(currentTime+500, free);
					enemy3.setNextEnemy((long) (currentTime + 4000 + Math.random() * 5000));
				}
			}
			
			/* Verificando mudanca de estado do player */

			if(player.getState() == INACTIVE){

				if(currentTime > player.getInvinc()){

					player.setState(ACTIVE);
				}
			}

			else if(player.getState() == EXPLODING){
				
				if(currentTime > player.getExplosion_end()){

					player.setState(REVIVING);
					player.setInvinc(currentTime+600);
				}
			}

			else if(player.getState() == REVIVING){

				if(currentTime > player.getInvinc()){

					player.setState(ACTIVE);
				}
			}

            /* Verificando entrada do usuário (teclado) */

            if(player.getState() != EXPLODING){
				double alpha = 1;
				if(player.getState() == INACTIVE){
					alpha = 1.85;
				}
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta* player.getVY()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta* player.getVY()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta* player.getVX()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta* player.getVX()/alpha);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.getState() == ACTIVE) {
					
					if(currentTime > player.getNextShot()){
						
						int free = findFreeIndex(p_projectile.getState());
												
						if(free < p_projectile.getState().length){

							p_projectile.setX(player.getX(), free);
							p_projectile.setY(player.getY()- 2*player.getRadius(), free);
							p_projectile.setVX(0.0, free);
							p_projectile.setVY(-1.0, free);
							p_projectile.setState(1, free);
							player.setNextShot(currentTime+ 100);
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
				GameLib.setColor(Color.ORANGE);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
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
		
			for(int i = 0; i < e_projectile.getState().length; i++){

				if(e_projectile.getState()[i] == ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e_projectile.getX()[i], e_projectile.getY()[i], e_projectile.getRadius());
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
					GameLib.setColor(Color.red);
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
				GameLib.drawCircle(GameLib.WIDTH - 30, GameLib.HEIGHT - 20, 7);
				GameLib.drawCircle(GameLib.WIDTH - 50, GameLib.HEIGHT - 20, 7);
				GameLib.drawCircle(GameLib.WIDTH - 70, GameLib.HEIGHT - 20, 7);
			}
			else if(player.getLife() >= (2*MAX_LIFE)/3){
				GameLib.setColor(Color.orange);
				GameLib.drawCircle(GameLib.WIDTH - 30, GameLib.HEIGHT - 20, 7);
				GameLib.drawCircle(GameLib.WIDTH - 50, GameLib.HEIGHT - 20, 7);
			}
			else if(player.getLife() >= MAX_LIFE/3){
				GameLib.setColor(Color.red);
				GameLib.drawCircle(GameLib.WIDTH - 30, GameLib.HEIGHT - 20, 7);
			}



			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
