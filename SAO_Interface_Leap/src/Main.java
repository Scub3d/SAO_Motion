import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

import com.leapmotion.leap.Controller;


public class Main {
	
	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//    		@Override
//    		public void run() {
    			final JFrame mainFrame = new JFrame();
    			mainFrame.setSize(400, 300);
    			mainFrame.setVisible(true);
    			JButton startInterfaceButton = new JButton("Link Start");
    			JButton exitButton = new JButton("Quit");
    			
    			JPanel buttonPanel = new JPanel();
    			buttonPanel.add(startInterfaceButton);
    			buttonPanel.add(exitButton);
    			
    			mainFrame.add(buttonPanel, BorderLayout.CENTER);
    			
    			final SampleListener listener = new SampleListener();
				final Controller controller = new Controller();
    			
    			startInterfaceButton.addActionListener(new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					mainFrame.setVisible(false);
    					
    					controller.addListener(listener);
    				}
    			});
    			ActionListener exit = new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					if(e.getID() == KeyEvent.KEY_PRESSED) {
    						controller.removeListener(listener);
    					}
    				}
    			};
				 // Keep this process running until Enter is pressed
//		        System.out.println("Press any key to quit...");
//		        mainFrame.addKeyListener(new KeyListener() {
//		        	public void keyPressed(KeyEvent e) {
//		        		System.out.println("YOU PRESSED A KEY");
//		        	}
//
//					@Override
//					public void keyReleased(KeyEvent arg0) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void keyTyped(KeyEvent arg0) {
//						// TODO Auto-generated method stub
//						
//					}
//		        });
    		}
//		});
//	}
}