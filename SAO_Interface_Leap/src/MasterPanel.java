import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;

import javax.swing.JComponent;


public class MasterPanel extends JComponent {

	public MasterPanel() {
		super();
	}
	
	public void draw(Graphics2D g2) {
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
	
	
}
