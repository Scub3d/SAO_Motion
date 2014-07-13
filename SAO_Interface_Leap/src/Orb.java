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


public class Orb extends Button implements ActionListener {
	
	
	public Orb(File file, int yPos) {
		
		super();
		
		this.foregroundFile = file;
		
		this.timer = new Timer(1, this);
		
		this.targetyPos = yPos;
		
		this.increment = 0;
		
		preLoadFiles();
		
	}
	
	public void preLoadFiles() {
		try {
			this.backgroundImage = ImageIO.read(new File("res/orbs/backgrounds/btn_normal.png"));
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.imgWidth = this.backgroundImage.getWidth(null);
			this.imgHeight = this.backgroundImage.getHeight(null);
		} catch (IOException e) {
			System.err.println("ERROR WITH ORB FILE");
		}
		//this.drawnImage = new BufferedImage(this.imgWidth, this.imgHeight,
			//BufferedImage.TYPE_INT_ARGB);
		// this.overlayImage = new BufferedImage(this.imgWidth, this.imgHeight,
			//BufferedImage.TYPE_INT_ARGB);
	}
	
	public void startAnimation()
	{
		timer.start();
	}
	
	public void onCreation(Graphics2D g2) { // This is the code for the starting animation
		if(this.backgroundImage != null && this.foregroundImage != null) {
			g2.drawImage(this.backgroundImage,
					150, increment + 32, null);
			g2.drawImage(this.foregroundImage,
					160, increment + 36, null);
		}
	}

	@Override
	public void draw(Graphics2D g2) { //
		if(timer.isRunning()) {
			onCreation(g2);
		}
		else {
			g2.drawImage(this.backgroundImage, 150, targetyPos, null);
			g2.drawImage(this.foregroundImage, 158, targetyPos+6, null);
		}
		
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if(this.increment > targetyPos - 100) { 
			this.increment += 1;
			System.out.println("Done");
		}
		else this.increment += 3;
		if(this.increment + 32 >= targetyPos) {
			timer.stop();
		}
	}

}
