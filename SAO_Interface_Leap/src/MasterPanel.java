import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import com.leapmotion.leap.Controller;


public class MasterPanel extends JComponent implements Runnable, KeyListener{

	int stage = 0;
	
	Orb orbs[];
	ExecutableItem tester;
	
	
	int screenHeight;
	int startingOrbyPos;
	
	File topImage;
	int numberOfOrbs = 5;
	int selectedOrb = 0;
	Thread SAOthread;
	
	
	int fps = 60;
	int frameCount = 0;
	
	private static boolean[] keyboardState = new boolean[525];
	private boolean isKeyPressed;
	
	public MasterPanel() {
		
		super();
		
		this.orbs = new Orb[5];
		
		this.setFocusable(true);
		requestFocus(); // set the focus to this panel
		this.addKeyListener(this);
		setDoubleBuffered(true);
		
		if(this.SAOthread == null) {
			this.SAOthread = new Thread(this);
			this.SAOthread.start();
		}	
	}
	
	public void update() {
		
		if(!isKeyPressed && getKeyboardState(KeyEvent.VK_R)) {
			stage++;
			System.out.println("Enter Pressed");
			isKeyPressed = !isKeyPressed;
		}
		else if(keyboardState[KeyEvent.VK_BACK_SPACE] && !isKeyPressed) {
			stage--;
			isKeyPressed = !isKeyPressed;
			
		}
		
	
		
		if(stage == 0) {
			for(int orb = 0; orb < orbs.length; orb++) {
				if(orbs[orb].state == 1 || orbs[orb].state == 2) {
					orbs[orb].state = 0;
				}
				orbs[orb].update();
			}			
		}
		if(stage == 1)
		{
			for(int orb = 0; orb < orbs.length; orb++) {
				if(orbs[orb].state != 2 && orbs[orb].state != 1) {
					orbs[orb].state = 1;
				}
				orbs[orb].update();
			}
		}
		
	}
	
	public void initialize() {
		
		this.screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		this.topImage = new File("res/orbs/orb_icons/info_normal.png");
			this.startingOrbyPos = screenHeight / 4;
			for(int i = 0; i < 5; i++) {
				this.orbs[i] = new Orb(topImage, 150, startingOrbyPos + (108 * i));	
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyboardState[e.getKeyCode()] = false;
		isKeyPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		final double GAME_HERTZ = 30.0;
		
		// Keeps updates from out-doing rendering
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		
		// Last update time
		double lastUpdateTime = System.nanoTime();
		// Last render time
		double lastRenderTime = System.nanoTime();
		
		final double TARGET_FPS = 60;
		
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
		
		leapListener listener = new leapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		initialize();
		
		while(true) {
			
			double now = System.nanoTime();
			int updateCount = 0;
			
			while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER ) {
				update();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			
			if( now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}
			
			repaint();
			lastRenderTime = now;
			
			//Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime)
			{
				System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
				fps = frameCount;
				frameCount = 0;
				lastSecondTime = thisSecond;
			}
			
			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
			{
				Thread.yield();
				
				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
				try {Thread.sleep(1);} catch(Exception e) {} 
				
				now = System.nanoTime();
			}
		}
	}	
}
