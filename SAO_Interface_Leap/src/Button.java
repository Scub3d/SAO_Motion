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
	
	public boolean inLockState; // Allows a button to be in lock state
	public boolean inHighlightedState; // Changes whether the button is highlighted or not
	public int targetxPos; // Places a target x-Coordinate position
	public int targetyPos; // Places a target y-Coordinate position
	
	public int increment = 0; // Used for incrementing animation
	
	protected File foregroundFile; // The foreground file to be used
	
	public Image backgroundImage; // The base button image
	public Image foregroundImage; // The image to be overlayed
	
	protected int imgWidth; // Image width
	protected int imgHeight; // Image height
	protected Timer timer; // Timer used for timing animations cleanly in Swing
	
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
	

	public int getCenteredxPosition() {
		return this.targetxPos + this.imgWidth;
	}
	
}
