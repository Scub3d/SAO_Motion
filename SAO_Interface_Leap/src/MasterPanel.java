import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.Timer;


public class MasterPanel extends JComponent {

	Orb orbs[];
	int screenHeight;
	int startingOrbyPos;
	
	File topImage;
	
	public MasterPanel() {
		
		super();
		this.orbs = new Orb[5];
		
		playSound("res/sounds/open.wav"); // play the opening sound file
		requestFocus(); // set the focus to this panel
		initialize(); 
	}
	
	public void initialize() {
		this.topImage = new File("res/orbs/orb_icons/info_normal.png");
		this.screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.startingOrbyPos = screenHeight / 4;
		for(int i = 0; i < 5; i++) {
			this.orbs[i] = new Orb(topImage, startingOrbyPos + (72 * i));
			this.orbs[i].startAnimation();
			
		}
	}
	
	public void draw(Graphics2D g2) {
		if(orbs[0] != null) orbs[0].draw(g2);
		if(orbs[1] != null) orbs[1].draw(g2);
		if(orbs[2] != null) orbs[2].draw(g2);
		if(orbs[3] != null) orbs[3].draw(g2);
		if(orbs[4] != null) orbs[4].draw(g2);
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
			draw(g2);
	}
	
	public void playSound(String sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }	
}
