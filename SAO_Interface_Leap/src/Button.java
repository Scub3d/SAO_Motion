import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.swing.Timer;

/**
 * The abstract class for all types of buttons.
 * @author wrightjt
 *
 */
public abstract class Button {
	
	protected final int CREATING = 1;
	protected final int DESTROYING = 0;
	protected final int HOLDING = 2;
	protected final int DESTROYED = -1;
	protected int state;
	
	
	public boolean inLockState; // Allows a button to be in lock state
	public boolean inHighlightedState; // Changes whether the button is highlighted or not
	public int targetxPos; // Places a target x-Coordinate position
	public int targetyPos; // Places a target y-Coordinate position
	public int currentxPos;
	public int currentyPos;
	public int startingxPos;
	public int startingyPos;
	
	public int increment; // Used for incrementing animation
	
	protected File foregroundFile; // The foreground file to be used
	
	public Image backgroundImage; // The base button image
	public Image selectedbgImage;
	public Image foregroundImage; // The image to be overlayed
	
	protected int imgWidth; // Image width
	protected int imgHeight; // Image height
	
	protected float alpha;
	
	/**
	 * This method is used for initializing the program.
	 */
	public abstract void initialize();
	
	/**
	 * This method is used for updating.
	 */
	public abstract void update();
	
	/**
	 * This should be used for loading beginning animations for orbs and for content panes alike.
	 * @param g2 The graphics for which to draw to.
	 */
	public abstract void onCreation(Graphics2D g2);
	
	/**
	 * Reset animations, reset values and variables, and generally reset the UI.
	 * @param g2
	 */
	public abstract void onDestruction(Graphics2D g2);

	/**
	 * All drawing should be carried out here. Animations can use the timer for running the "onCreation" method.
	 * @param g2 The graphics for which to draw to.
	 */
	public abstract void draw(Graphics2D g2);
	
	
	/**
	 * Determines whether or not the button is focused or not.
	 * @return If the button is focused or not.
	 */
	public boolean isFocused(boolean state) {
		return this.inHighlightedState = state;
	}
	

	public int getxCreationPoint() {
		return (this.targetxPos + this.imgHeight);
	}
	
	public int getyCreationPoint() {
		return this.targetyPos + (this.imgWidth/2);
	}
	
	public int getImageWidth() {
		return this.imgWidth;
	}
	
	public int getImageHeight() {
		return this.imgHeight;
	}
	
}
