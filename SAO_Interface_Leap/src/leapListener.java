/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;

class leapListener extends Listener {

	@Override
	public void onInit(Controller controller) {
        System.out.println("Initialized");
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

    public Hand hand;
    public SwipeGesture swipes;
    public ScreenTapGesture tap;
	public boolean droppedDown = false;

	
	@Override
	public void onFrame(Controller controller) {
        Frame controller_frame = controller.frame();    // 3.0f , 1f, .2f also works okay
        if(controller.config().setFloat("Gesture.ScreenTap.MinForwardVelocity", 3.0f) &&
           controller.config().setFloat("Gesture.ScreenTap.HistorySeconds", 1f) &&
           controller.config().setFloat("Gesture.ScreenTap.MinDistance", 0.2f)) {
            	controller.config().save();
        }
        System.out.println("Frame id: " + controller_frame.id() + ", timestamp: " + controller_frame.timestamp() + ", hands: " + controller_frame.hands().count() + ", fingers: " + controller_frame.fingers().count() + ", tools: " + controller_frame.tools().count() + ", gestures " + controller_frame.gestures().count());
        if (!controller_frame.hands().isEmpty()) {
        	this.hand = new Hand();
        }

        GestureList gestures = controller_frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_SWIPE:
                	this.swipes = new SwipeGesture(gesture);
                	if(this.swipes.state() == State.STATE_START) {
	                	if(this.swipes.direction().getY() < -.5 && Math.abs(this.swipes.direction().getX()) < .35 && Math.abs(this.swipes.direction().getZ()) < .35 && this.droppedDown == false) {
	                		this.droppedDown = true;
	                	} else if(this.swipes.direction().getX() < -.5 && Math.abs(this.swipes.direction().getY()) < .35 && Math.abs(this.swipes.direction().getZ()) < .35 && this.droppedDown == true) {
	                		this.droppedDown = false;
	                	}
                	}
		            break;
		            
		        case TYPE_SCREEN_TAP:
		        	this.tap = new ScreenTapGesture(gesture);		
		        	System.out.println("Tap state: " + this.tap.state() + ",   Tap Position = " + this.tap.position());
		            break;
		                         
                default:
                    System.out.println("Unknown gesture type." + "\n");
                    break;
            }
        }

        if (!controller_frame.hands().isEmpty() || !gestures.isEmpty()) {
            //System.out.println(); Pass along data or just pass along even if there is no data. Probably the latter is better
        }
    }
}
