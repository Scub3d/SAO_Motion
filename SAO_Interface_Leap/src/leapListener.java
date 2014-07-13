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
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

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
	public boolean isCreated = false;
	
	@Override
	public void onFrame(Controller controller) {
        Frame current_frame = controller.frame();    // 3.0f , 1f, .2f also works okay
        Frame previous_frame = controller.frame(1);
        
        if(previous_frame.isValid()) {
            for (int p = 0; p < current_frame.pointables().count();p++) {
            	Pointable pN = current_frame.pointables().get(p);
                Pointable pN_prev = previous_frame.pointable(pN.id());
                //System.out.println(pN_prev.touchDistance() + ", " + pN.touchDistance());

                if (!pN_prev.isValid())
                    continue; 

                if (pN_prev.touchDistance() <= 0 && pN.touchDistance() > 0) {
                    System.out.format("Leap Thread: Pointable %d tapped\n",pN.id());
                }
            }
        }
 
        System.out.println("Frame id: " + current_frame.id() + ", timestamp: " + current_frame.timestamp() + ", hands: " + current_frame.hands().count() + ", fingers: " + current_frame.fingers().count() + ", tools: " + current_frame.tools().count() + ", gestures " + current_frame.gestures().count());
        if (!current_frame.hands().isEmpty()) {
        	this.hand = new Hand();
        }
        
        GestureList gestures = current_frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_SWIPE:
                	this.swipes = new SwipeGesture(gesture);
                	if(this.swipes.state() == State.STATE_START) {
                		System.out.println(this.swipes.direction());
	                	if(this.swipes.direction().getY() < -.5 && Math.abs(this.swipes.direction().getX()) < .35 && Math.abs(this.swipes.direction().getZ()) < .35 && this.isCreated == false) {
	                		this.isCreated = true;
	                	} else if(this.swipes.direction().getX() < -.5 && Math.abs(this.swipes.direction().getY()) < .35 && Math.abs(this.swipes.direction().getZ()) < .35 && this.isCreated == true) {
	                		this.isCreated = false;
	                	}
                	} 
		            break;
		            
		        case TYPE_SCREEN_TAP:
		        	//this.tap = new ScreenTapGesture(gesture);		
		        	//System.out.println("Tap state: " + this.tap.state() + ",   Tap Position = " + this.tap.position());
		            break;
		                         
                default:
                    //System.out.println("Unknown gesture type." + "\n");
                    break;
            }
        }

        if (!current_frame.hands().isEmpty() || !gestures.isEmpty()) {
            //System.out.println(); Pass along data or just pass along even if there is no data. Probably the latter is better
        }
    }
}
