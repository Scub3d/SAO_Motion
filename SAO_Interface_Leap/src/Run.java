import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class Run {

	public static void main(String[] args) {
		
		JFrame mainFrame = new JFrame(); //Creates the frame for SAO to work on
		
		MasterPanel masterPanel = new MasterPanel();
		
		mainFrame.setUndecorated(true); // Add no decor
		mainFrame.setBackground(new Color(0,0,0,0)); // Set to be transparent
		mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH); // Maximize the frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(masterPanel);
		mainFrame.setVisible(true);
	}

}
