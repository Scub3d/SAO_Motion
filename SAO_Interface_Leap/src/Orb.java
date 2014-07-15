import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		
		this.alpha = 1;
		
		initialize(); // Pre-load all information regarding images, image dimensions, and other details.
		
	}
	@Override
	public void initialize() {
		try {
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/large_normal_button.png")); // btn_normal
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.selectedbgImage = ImageIO.read(new File("res/orbs/backgrounds/large_selected_button.png")); // btn_hover.png
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
		}
	}
	
	
	@Override
	public void onDestruction(Graphics2D g2) {
		g2.drawImage(this.backgroundImage, this.currentxPos, this.targetyPos, null);
	}

	@Override
	public void draw(Graphics2D g2) { // Used for drawing
		g2.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, this.alpha));
		switch(state) {
		case CREATING:
			onCreation(g2);
			break;
		case HOLDING:	
			if(this.inHighlightedState)
				g2.drawImage(this.selectedbgImage, this.currentxPos, this.currentyPos, null); // draw the background image after creation
			else
				g2.drawImage(this.backgroundImage, this.currentxPos, this.currentyPos, null);
				
			break;
		case DESTROYING:
			onDestruction(g2);
		
		}	
	}	
	
	@Override
	public void update() {
		switch(state) {
		case CREATING:
			if(this.currentyPos < this.targetyPos - 48) {
				this.currentyPos += 24;
			}
			
			else if(this.currentyPos < this.targetyPos && this.currentyPos > this.targetyPos - 48) {
				this.currentyPos += 8;
			}
			
			
			
			else
			{
				this.currentxPos = this.targetxPos;
				this.currentyPos = this.targetyPos;
				this.state = HOLDING;
			}
			break;
		
		case HOLDING:
			break; 
			
		case DESTROYING:
			System.out.println("Old: " + this.currentxPos);
			
			if(this.currentxPos > -80) {
				this.currentxPos -= 12;
				this.alpha -= 0.12;
				System.out.println("Alpha: " +this.alpha);
			} else {
				state = DESTROYED;
			}
			if(this.alpha <= 0) {
				this.alpha = 0;
				System.out.println(this.alpha);
			}
			break;
		case DESTROYED:
			this.currentxPos = this.startingxPos;
			this.currentyPos = this.startingyPos;
			this.alpha = 1;
		}
		
	}
}
