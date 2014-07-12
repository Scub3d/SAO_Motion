/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.awt.Color;
import java.io.File;

import javax.print.attribute.AttributeSetUtilities;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;

class SampleListener extends Listener {
	
    @Override
	public void onInit(Controller controller) {
        System.out.println("Initialized");
        saoInterface();
    }
    
    @Override
	public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    @Override
	public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    @Override
	public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public TheHand hand;
    public ScreenSwipe swipes;
    public ScreenTap tap;
	public boolean droppedDown = false;
	public saoComponent comp = new saoComponent();
	
	@Override
	public void onFrame(Controller controller) {
        Frame frame = controller.frame();    // 3.0f , 1f, .2f also works okay
        if(controller.config().setFloat("Gesture.ScreenTap.MinForwardVelocity", 3.0f) &&
           controller.config().setFloat("Gesture.ScreenTap.HistorySeconds", 1f) &&
           controller.config().setFloat("Gesture.ScreenTap.MinDistance", 0.2f)) {
            	controller.config().save();
        }
        //System.out.println("Frame id: " + frame.id() + ", timestamp: " + frame.timestamp() + ", hands: " + frame.hands().count() + ", fingers: " + frame.fingers().count() + ", tools: " + frame.tools().count() + ", gestures " + frame.gestures().count());
        if (!frame.hands().isEmpty()) {
        	this.hand = new TheHand(frame.hands().frontmost());
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_SWIPE:
                	this.swipes = new ScreenSwipe(new SwipeGesture(gesture));
                	if(this.swipes.getState() == State.STATE_START) {
	                	if(this.swipes.getDirection().getY() < -.5 && Math.abs(this.swipes.getDirection().getX()) < .35 && Math.abs(this.swipes.getDirection().getZ()) < .35 && this.droppedDown == false) {
	                		this.droppedDown = true;
	                	} else if(this.swipes.getDirection().getX() < -.5 && Math.abs(this.swipes.getDirection().getY()) < .35 && Math.abs(this.swipes.getDirection().getZ()) < .35 && this.droppedDown == true) {
	                		this.droppedDown = false;
	                	}
                	}
		            break;
		            
		        case TYPE_SCREEN_TAP:
		        	this.tap = new ScreenTap(new ScreenTapGesture(gesture));		
		        	System.out.println("Tap state: " + this.tap.getState() + ",   Tap Position = " + this.tap.getPosition());
		            break;
		                         
                default:
                    System.out.println("Unknown gesture type." + "\n");
                    break;
            }
        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
            //System.out.println();
        }
        
        if(this.hand != null || this.tap != null) {
        	this.comp.updateData(this.hand, this.tap, this.droppedDown);
        }
        this.frame.repaint();        
        this.frame.setVisible(true);
    }
	
	public JFrame frame;
	public void saoInterface() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0,0,0,0));
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.add(comp);
		frame.setVisible(true);
	}
}
