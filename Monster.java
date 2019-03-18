import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JComponent;
import java.awt.Rectangle;

public class Monster{
	
	public int xPos;
	public int yPos;
	public int width;
	public int height;
	public int life = 20;
	public int power = 5;
	
	//Creature States
	public boolean isIdle = false;
	public boolean isMoving = true;
	public boolean isFacingRight = false;
	public boolean isAttacking = false;
	public boolean isDead = false;

	public Draw draw;
	public BufferedImage image;
	public URL resource = getClass().getResource("enemy/enemy_walk0.png");

	public Monster(Draw comp){
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		animate(comp);
	}

	public Monster(int xPass, int yPass, Draw comp){
		xPos = xPass;
		yPos = yPass;


		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		this.draw = comp;
		this.height = image.getHeight();
		this.width = image.getWidth();

		animate(comp);

	}

	public Rectangle monsterBounds(){
		return(new Rectangle (xPos, yPos, width, height));

	}



	public void animate(Draw compPass){
		Thread monThread = new Thread(new Runnable(){
			public void run(){
				while(isIdle){
					for(int ctr = 0; ctr < 5; ctr++){
						try {
							if(ctr==4){
								resource = getClass().getResource("enemy/enemy_walk0.png");
							}
							else{
								resource = getClass().getResource("enemy/enemy_walk"+ctr+".png");
							}
							
							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}

					        compPass.repaint();
					        Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if(life<=0){


					}
				}
			}
		});
		monThread.start();
	}







	public void monsAttack(){

		isMoving = false;

		for (int i = 0; i < 4; i++){
			if(isAttacking == true){
				resource = getClass().getResource("enemy/enemy_attack"+i+".png");
				xPos-=5;
			}else{
				resource = getClass().getResource("enemy/enemy_die"+i+".png");
				xPos+=5;
			}

			try{ draw.repaint();
					        Thread.sleep(100);
				} catch (InterruptedException e) {
							e.printStackTrace();
			}
		}
	}

	public boolean checkHealth(){

		if (life <= 0){
			isAttacking = false;
			isMoving = false;
			return true;
		}
		return false;
	}

	public void monDeath(){
		for(int i=1; i<5; i++){
			resource = getClass().getResource("enemy/enemy_die"+i+".png");

			try{ draw.repaint();
					        Thread.sleep(100);
				} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println ("Dead Monster");

			isDead = true;
		}
	}


		public void moveTo(int toX, int toY){
		if(xPos<toX){
			xPos++;
		}
		else if(xPos>toX){
			xPos--;
		}

		if(yPos<toY){
			yPos++;
		}
		else if(yPos>toY){
			yPos--;
		}
	}

}