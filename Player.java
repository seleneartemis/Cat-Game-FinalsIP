import javax.swing.JComponent;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;


public class Player {

	Draw draw;

	public BufferedImage image;
	public URL resource = getClass().getResource("cat_walk0.png");

	// circle's position
	public int x = 90;
	public int y = 430;
	public int height;
	public int width;

	// animation states
	public boolean isAttacking = false;
	public int state = 0;
	

	public Player (Draw draw){
		this.draw = draw;

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public Player(int x, int y, Draw draw){

		this.x = x;
		this.y = y;

		this.draw = draw;

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth() + 10;
	}

	public Rectangle playerBounds(){
		return(new Rectangle (x, y, width, height));
	}


	public void reloadImage(){
		state++;

		if(state == 0){
			resource = getClass().getResource("cat_walk0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("cat_walk1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("cat_walk2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("cat_walk3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("cat_walk4.png");
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
				isAttacking = true;
				for(int ctr = 1; ctr < 6; ctr++){
					try {
						if(ctr==5){
							resource = getClass().getResource("cat_walk0.png");
						}
						else{
							resource = getClass().getResource("fastshot"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        draw.repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				draw.checkCollision();
				isAttacking = false;
			}
		});
		thread1.start();
	}


	public void attack(){
		attackAnimation();
	}

	public void moveUp(){
		y = y - 10;
		x = x + 10;
		reloadImage();
		draw.repaint();
		
	}

	public void moveDown(){
	
		reloadImage();
		draw.repaint();
	}

	public void moveLeft(){
		x = x - 5;
		reloadImage();
		draw.repaint();
	}

	public void moveRight(){
		x = x + 5;
		reloadImage();
		draw.repaint();
	}
}