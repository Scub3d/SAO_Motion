import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Orb extends Button {

	private int imgWidth;
	private int imgHeight;
	
	private ImageObserver i;
	
	BufferedImage drawnImage;
	
	Graphics g;
	
	public Orb() {
		super();
		preLoadFiles();
	}
	
	public void preLoadFiles() {
		try {
			System.out.println("did we get here?");
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_normal.png"));
			System.out.println("File okay");
			this.imgWidth = this.backgroundImage.getWidth(null);
			this.imgHeight = this.backgroundImage.getHeight(null);
		} catch (IOException e) {
			System.err.println("ERROR WITH ORB FILE");
		}
		this.drawnImage = new BufferedImage(this.imgWidth, this.imgHeight,
			BufferedImage.TYPE_INT_ARGB);
		g = this.drawnImage.getGraphics();
	}
	
	public void drawImages(Graphics2D g2, int xPos, int yPos) {
		if(this.backgroundImage != null) {
			g2.drawImage(this.backgroundImage,
					-32, -32, null);
		}
	}

	@Override
	public void onCreation(Graphics2D g2, int xPos, int yPos) {
		drawImages(g2, -32, -32);
		System.out.println("Drawing");
	}

}
