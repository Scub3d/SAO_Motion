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
public class Orb extends Button {
	
	private int animationSlower = 0;
	
	/**
	 * 
	 * @param file
	 * @param yPos
	 */
	public Orb(File file, int yPos) {
		
		super();
		
		this.foregroundFile = file;
		
		this.targetyPos = yPos; // The stated target position.
		
		initialize(); // Pre-load all information regarding images, image dimensions, and other details.
		
	}
	@Override
	public void initialize() {
		try {
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_normal.png"));
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.imgWidth = this.backgroundImage.getWidth(null);
			this.imgHeight = this.foregroundImage.getHeight(null);
		} catch (IOException e) {
			System.err.println("ERROR WITH ORB FILE");
		}
	}
	
	public void onCreation(Graphics2D g2) { // This is the code for the starting animation
		if(this.backgroundImage != null && this.foregroundImage != null && !doneAnimating) {
			g2.drawImage(this.backgroundImage,
					150, increment, null);
			g2.drawImage(this.foregroundImage,
					159, increment+4, null);
		}
	}

	@Override
	public void draw(Graphics2D g2) { // Used for drawing
		if(!doneAnimating)
		{
			onCreation(g2); // Timer no longer running
		}
		else {
			g2.drawImage(this.backgroundImage, 150, targetyPos, null); // draw the background image after creation
			g2.drawImage(this.foregroundImage, 159, targetyPos+6, null); // draw the foreground image after creation
		}
	}	

	
	@Override
	public void update() {
		if(!doneAnimating) {
			this.animationSlower = 0;
			System.out.println(increment);
			if(this.increment <= this.targetyPos-25) {
				increment += 16;
			}
			
			else if(this.increment <= this.targetyPos && this.increment > this.targetyPos - 25) {
				increment += 8;
			}
			
			else this.doneAnimating = true;
		}
	}
}
