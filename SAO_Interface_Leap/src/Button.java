import java.awt.Graphics2D;


public abstract class Button {
	
	public boolean inLockState; // Allows a button to be in lock state
	
	public boolean inHighlightedState; // Changes whether the button is highlighted or not
	
	public abstract void onCreation(Graphics2D g2);

}
