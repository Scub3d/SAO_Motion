import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;


public abstract class Button {
	
	public boolean inLockState; // Allows a button to be in lock state
	public boolean inHighlightedState; // Changes whether the button is highlighted or not
	public int xPos;
	public int yPos;
	public String type;
	public Image backgroundImage;
	public Image foregroundImage;
	
	public abstract void onCreation(Graphics2D g2);

}
