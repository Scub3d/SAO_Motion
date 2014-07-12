import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;

import javax.swing.JComponent;


public class MasterPanel extends JComponent {

	Orb borb;
	
	public MasterPanel() {
		super();
		this.borb = new Orb();
		requestFocus();
	}
	
	
	
	public void draw(Graphics2D g2) {
		borb.onCreation(g2, 100, 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
			draw(g2);
	}
	
	
	
}
