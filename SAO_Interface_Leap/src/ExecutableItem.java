import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ExecutableItem extends Button  {

	String executable;
	String text;
	
	public ExecutableItem(File icon, int xPos, int yPos, String executable, String text) {
		super();
		
		this.text = text;
		
		this.foregroundFile = icon;
		
		this.targetxPos = xPos;
		this.targetyPos = yPos;
		
		this.executable = executable;
		
		initialize();
	}
	
	@Override
	public void initialize() {
		try {
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.backgroundImage = ImageIO.read(new File("res/menu/backgrounds/list_normal.png"));
		} catch (IOException e) {
			System.err.println("ERROR READING BUTTON FILE");
		}
	}
	
	@Override
	public void onCreation(Graphics2D g2) {
		g2.drawImage(this.backgroundImage, targetxPos, targetyPos, null);
		g2.drawImage(this.foregroundImage, targetxPos + 20, targetyPos + 10, null);

	}

	@Override
	public void draw(Graphics2D g2) {
		if(!doneAnimating) {
			onCreation(g2);
		}
		else {
			g2.drawImage(this.backgroundImage, targetxPos, targetyPos, null);
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
