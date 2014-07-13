import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ExecutableItem extends Button implements ActionListener {

	String executable;
	String text;
	
	public ExecutableItem(File file, int xPos, int yPos, String executable, String text) {
		super();
		
		this.text = text;
		
		this.foregroundFile = file;
		
		this.targetxPos = xPos;
		this.targetyPos = yPos;
		
		this.executable = executable;
		
		initialize();
	}
	
	@Override
	public void initialize() {
		try {
			this.foregroundImage = ImageIO.read(this.foregroundFile);
			this.backgroundImage = ImageIO.read(new File("res/menu/backgrounds/list_normal"));
		} catch (IOException e) {
			System.err.println("ERROR READING BUTTON FILE");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreation(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
