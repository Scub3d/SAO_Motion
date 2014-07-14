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
	
	/**
	 * 
	 * @param file
	 * @param yPos
	 */
	public Orb(File file, int xPos, int yPos) {
		
		super();
		
		this.foregroundFile = file;
		
		this.targetxPos = xPos;
		this.targetyPos = yPos; // The stated target position.
		
		this.state = DESTROYED;
		
		this.startingxPos = 150;
		this.startingyPos = 32;
		this.currentxPos = this.startingxPos;
		this.currentyPos = this.startingyPos;
		
		initialize(); // Pre-load all information regarding images, image dimensions, and other details.
		
	}
	@Override
	public void initialize() {
		try {
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/large_normal_button.png"));
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.imgWidth = this.backgroundImage.getWidth(null);
			this.imgHeight = this.foregroundImage.getHeight(null);
		} catch (IOException e) {
			System.err.println("ERROR WITH ORB FILE");
		}
	}
	
	public void onCreation(Graphics2D g2) { // This is the code for the starting animation
		
		if(this.backgroundImage != null && this.foregroundImage != null) {
			g2.drawImage(this.backgroundImage,
					this.targetxPos, this.currentyPos , null);
			//g2.drawImage(this.foregroundImage,
				//	159, this.currentyPos, null);
		}
	}
	
	
	@Override
	public void onDestruction(Graphics2D g2) {
		g2.drawImage(this.backgroundImage, this.currentxPos, this.currentyPos, null);
	}

	@Override
	public void draw(Graphics2D g2) { // Used for drawing
		switch(state) {
		case CREATING:
			onCreation(g2);
			break;
		case HOLDING:	
			g2.drawImage(this.backgroundImage, this.targetxPos, this.targetyPos, null); // draw the background image after creation
			//g2.drawImage(this.foregroundImage, 159, this.currentyPos+6, null); // draw the foreground image after creation
			//onDestruction(g2);
			break;
		case DESTROYING:
			onDestruction(g2);
		
		}	
	}	
	
	@Override
	public void update() {
		System.out.println(state);
		switch(state) {
		case CREATING:
			if(this.currentyPos < this.targetyPos) {
				this.currentyPos += 24;
				System.out.println(this.currentyPos);
			}
			
			
			else
			{
				System.out.println("Here's your problem");
				this.state = HOLDING;
			}
			break;
		
		case HOLDING:
			break;
		case DESTROYING:
			if(this.currentxPos > -96) {
				this.currentxPos -= 12;
			}
			else {
				state = DESTROYED;
			}
			break;
		case DESTROYED:
			this.currentxPos = this.startingxPos;
			this.currentyPos = this.startingyPos;
			break;
		}
		
	}
}
