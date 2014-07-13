import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * The orb is for the initial balls that load. These orbs initially load a file, and then are instantiated
 * in the MasterPanel. The update method determines which image should be used and the isFocused method
 * determines whether or not a button is focused.
 * @author wrightjt
 *
 */
public class Orb extends Button implements ActionListener {
	
	/**
	 * 
	 * @param file
	 * @param yPos
	 */
	public Orb(File file, int yPos) {
		
		super();
		
		this.foregroundFile = file;
		
		this.timer = new Timer(1, this); // Instantiates the timer for the Orb creation.
		
		this.targetyPos = yPos; // The stated target position.
		
		preLoadFiles(); // Pre-load all information regarding images, image dimensions, and other details.
		
	}
	
	public void preLoadFiles() {
		try {
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_normal.png"));
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.imgWidth = this.backgroundImage.getWidth(null);
			this.imgHeight = this.foregroundImage.getHeight(null);
		} catch (IOException e) {
			System.err.println("ERROR WITH ORB FILE");
		}
	}
	
	public void startAnimation()
	{
		timer.start();
	}
	
	public void onCreation(Graphics2D g2) { // This is the code for the starting animation
		if(this.backgroundImage != null && this.foregroundImage != null) {
			g2.drawImage(this.backgroundImage,
					150, increment + 32, null);
			g2.drawImage(this.foregroundImage,
					159, increment + 36, null);
		}
	}

	@Override
	public void draw(Graphics2D g2) { // Used for drawing
		if(timer.isRunning()) { // Timer is running
			onCreation(g2);
		}
		else { // Timer no longer running
			g2.drawImage(this.backgroundImage, 150, targetyPos, null); // draw the background image after creation
			g2.drawImage(this.foregroundImage, 159, targetyPos+6, null); // draw the foreground image after creation
		}	
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) { // Used for timing and alternating positions. 
		if(this.increment > targetyPos - 100) { 
			this.increment += 1;
			System.out.println("Done");
		}
		else this.increment += 4;
		if(this.increment + 32 >= targetyPos) {
			timer.stop();
		}
	}

	@Override
	public void isFocused(boolean state) { 
		this.inHighlightedState = state;
	}

	@Override
	public void update() {
		try {
			if(this.inHighlightedState) {
				this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_hover.png"));
			}
			else this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_normal.png"));
		} catch (IOException e) {
			System.err.println("ERROR READING HOVER FILE");
		}
	}

}
