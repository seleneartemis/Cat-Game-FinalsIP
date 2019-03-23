import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class Draw extends JComponent{

	private BufferedImage image;
	private BufferedImage backgroundImage;
	public URL resource = getClass().getResource("cat_gun/cat_walk0.png");

	// circle's position
	public int x = 140;
	public int y = 420;
	public int height = 0;
	public int width = 0;

	// animation states
	public int state = 0;

	// randomizer
	public Random randomizer;
	private final int gravity = 450;

	// enemy
	public int enemyCount;
	public Monster monster;
	public ArrayList<Monster> monsterList = new ArrayList<>();
	public int counter = 1;
	//public Timer timer = new Timer (20000, this);
	//Monster[] monsters = new Monster[10];

	public Draw(){
		randomizer = new Random();
		spawnEnemy();
		
		try{
			image = ImageIO.read(resource);
			backgroundImage = ImageIO.read(getClass().getResource("background.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();

		startGame();
	}

	public void startGame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						for(int c = 0; c < monsterList.size(); c++){
							if(monsterList.get(c)!=null){
								monsterList.get(c).moveTo(x,y);
								repaint();
							}
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}

	public void spawnEnemy(){
		int spawnPosition = 430;
		if (monsterList.size() != 10){
			monster = new Monster (spawnPosition, gravity + 10, this);
			monsterList.add(monster);
			counter++;
		}

		//if(enemyCount < 10){
		//	monsters[enemyCount] = new Monster(randomizer.nextInt(300), randomizer.nextInt(300), this);
		//	enemyCount++;
		//}
	}

	public void reloadImage(){
		state++;

		if(state == 0){
			resource = getClass().getResource("cat_gun/cat_walk0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("cat_gun/cat_walk1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("cat_gun/cat_walk2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("cat_gun/cat_walk3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("cat_gun/cat_walk4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("cat_gun/cat_walk5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 7; ctr++){
					try {
						if(ctr==6){
							resource = getClass().getResource("cat_gun/cat_walk0.png");
						}
						else{
							resource = getClass().getResource("cat_gun/standshot"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				for(int x=0; x<monsterList.size(); x++){
					if(monsterList.get(x)!=null){
						if(monsterList.get(x).contact){
							monsterList.get(x).life = monsterList.get(x).life - 10;
						}
					}
				}
			}
		});
		thread1.start();
	}

	public void attack(){
		attackAnimation();
	}

	public void moveUp(){
		
		reloadImage();
		repaint();
		checkCollision();
	}

	public void moveDown(){
	
		reloadImage();
		repaint();
		checkCollision();
	}

	public void moveLeft(){
		x = x - 5;
		reloadImage();
		repaint();
		checkCollision();
	}

	public void moveRight(){
		x = x + 5;
		reloadImage();
		repaint();
		checkCollision();
	}

	public void checkCollision(){
		int xChecker = x + width;
		int yChecker = y;

		for(int x=0; x<monsterList.size(); x++){
			boolean collideX = false;
			boolean collideY = false;

			if(monsterList.get(x)!=null){
				monsterList.get(x).contact = false;

				if(yChecker > monsterList.get(x).yPos){
					if(yChecker-monsterList.get(x).yPos < monsterList.get(x).height){
						collideY = true;
						System.out.println("collideY");
					}
				}
				else{
					if(monsterList.get(x).yPos - (yChecker+height) < monsterList.get(x).height){
						collideY = true;
						System.out.println("collideY");
					}
				}

				if(xChecker > monsterList.get(x).xPos){
					if((xChecker-width)-monsterList.get(x).xPos < monsterList.get(x).width){
						collideX = true;
						System.out.println("collideX");
					}
				}
				else{
					if(monsterList.get(x).xPos-xChecker < monsterList.get(x).width){
						collideX = true;
						System.out.println("collideX");
					}
				}
			}

			if(collideX && collideY){
				System.out.println("collision!");
				monsterList.get(x).contact = true;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);

		// character grid for hero
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, this);

		for(int c = 0; c < monsterList.size(); c++){		
			if(monsterList.size()!=0){
				// character grid for monsters
				// g.setColor(Color.BLUE);
				// g.fillRect(monsters[c].xPos, monsters[c].yPos+5, monsters[c].width, monsters[c].height);
				g.drawImage(monsterList.get(c).image, monsterList.get(c).xPos, monsterList.get(c).yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsterList.get(c).xPos+7, monsterList.get(c).yPos, monsterList.get(c).life, 2);
			}	
		}
		
	//	for(int c = 0; c < monsters.length; c++){		
	//		if(monsters[c]!=null){
	//			// character grid for monsters
	//			// g.setColor(Color.BLUE);
	//			// g.fillRect(monsters[c].xPos, monsters[c].yPos+5, monsters[c].width, monsters[c].height);
	//			g.drawImage(monsters[c].image, monsters[c].xPos, monsters[c].yPos, this);
	//			g.setColor(Color.GREEN);
	//			g.fillRect(monsters[c].xPos+7, monsters[c].yPos, monsters[c].life, 2);
	//		}	
	//	}
	}

	public void checkDeath(){
		for(int c = 0; c < monsterList.size(); c++){
			if(monsterList.get(c)!=null){
				if(!monsterList.get(c).alive){
				
					//monsterList.get(c) = null;
				}
			}			
		}
	}
	//public void checkDeath(){
	//	for(int c = 0; c < monsters.length; c++){
	//		if(monsters[c]!=null){
	//			if(!monsters[c].alive){
	//				monsters[c] = null;
	//			}
	//		}			
	//	}
	//}
}