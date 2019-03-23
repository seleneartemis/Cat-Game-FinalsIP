import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JComponent;

public class Monster{
	
	public int xPos = 300;
	public int yPos = 430;
	public int width = 0;
	public int height = 0;
	public int life = 20;
	public boolean idle = true;
	public boolean alive = true;
	public boolean contact = false;

	public BufferedImage image;
	public URL resource = getClass().getResource("enemy/enemy_walk0.png");
	public Draw draw;

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

		height = image.getHeight();
		width = image.getWidth();

		animate(comp);
	}

	public void animate(Draw compPass){
		Thread monThread = new Thread(new Runnable(){
			public void run(){
				while(idle){
					for(int ctr = 0; ctr < 4; ctr++){
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
						die(compPass);
					}
				}
			}
		});
		monThread.start();
	}

	public void moveTo(int toX, int toY){
		if(xPos<toX){
			xPos++;
		}
		else if(xPos>toX){
			xPos--;
		}
	}

	public void die(Draw compPass){
		idle = false;
		if(alive){
			Thread monThread = new Thread(new Runnable(){
				public void run(){
					for(int ctr = 0; ctr < 8; ctr++){
						try {					
							resource = getClass().getResource("enemy/enemy_die"+ctr+".png");
							
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

					alive = false;
					compPass.checkDeath();
				}
			});
			monThread.start();
		}
	}
}