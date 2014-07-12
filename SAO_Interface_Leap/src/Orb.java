import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;


public class Orb extends Button {

	
	public Orb() {
		super();
		this.backgroundImage = Toolkit.getDefaultToolkit().getImage("res//orbs//backgrounds//btn_normal.png");
	}

	@Override
	public void onCreation(Graphics2D g2) {
		System.out.println("Drawing");
		g2.drawImage(this.backgroundImage, 100, 100, 50, 50, null);
	}

}
