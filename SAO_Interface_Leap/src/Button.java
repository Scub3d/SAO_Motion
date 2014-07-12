import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.Timer;


public abstract class Button {
	
	public boolean inLockState; // Allows a button to be in lock state
	public boolean inHighlightedState; // Changes whether the button is highlighted or not
	public int targetxPos;
	public int targetyPos;
	public int increment;
	public String type;
	
	public Image backgroundImage; // The base button image
	public Image foregroundImage; // The image to be overlayed
	public BufferedImage drawnImage; // The buffered image
	public BufferedImage overlayImage; // The overlayed image
	protected int imgWidth; // Image width
	protected int imgHeight; // Image height
	
	protected Timer timer;
	
	public abstract void onCreation(Graphics2D g2);

	public abstract void draw(Graphics2D g2);

}
