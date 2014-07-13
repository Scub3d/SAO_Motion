import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.Timer;


public class MasterPanel extends JComponent implements Runnable, KeyListener{

	Orb orbs[];
	int screenHeight;
	int startingOrbyPos;
	
	File topImage;
	int numberOfOrbs = 5;
	int selectedOrb = 0;
	Thread SAOthread;
	
	private static boolean[] keyboardState = new boolean[525];
	
	public MasterPanel() {
		
		super();
		
		this.orbs = new Orb[5];
		
		playSound("res/sounds/open.wav"); // play the opening sound file
		this.setFocusable(true);
		requestFocus(); // set the focus to this panel
		this.addKeyListener(this);
		
		if(this.SAOthread == null) {
			this.SAOthread = new Thread(this);
			this.SAOthread.start();
		}
		
		
	}
	
	public void update() {
		for(int orb = 0; orb < orbs.length; orb++) {
			orbs[orb].update();
		}
		orbs[selectedOrb].isFocused(true);
		if(keyboardState[KeyEvent.VK_DOWN])
		{
			orbs[selectedOrb].isFocused(false);
			this.selectedOrb++;
			System.out.println(this.selectedOrb);
			if(this.selectedOrb >= numberOfOrbs) {
				this.selectedOrb = 0;
			} 
			else if(this.selectedOrb < 0) {
				this.selectedOrb = 5;
			} 
		}
	}
	
	public void initialize() {
		this.topImage = new File("res/orbs/orb_icons/info_normal.png");
		this.screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.startingOrbyPos = screenHeight / 4;
		for(int i = 0; i < 5; i++) {
			this.orbs[i] = new Orb(topImage, startingOrbyPos + (72 * i));	
		}
		
		for(int x = 0; x < 5; x++) {
			this.orbs[x].startAnimation();
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
	
	public static boolean getKeyboardState(int key) {
		return keyboardState[key];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyboardState[e.getKeyCode()] = true;
		System.out.println("anything here?");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyboardState[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		initialize();
		
		while(true) {
			
			update();
			repaint();
			
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
