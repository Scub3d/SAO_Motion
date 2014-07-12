import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;


public class ScreenSwipe {
	
	private int id;
	private State state;
	private Vector position;
	private Vector direction;
	private Vector startPosition;
	
	public ScreenSwipe(SwipeGesture swipe) {
		this.id = swipe.id();
		this.state = swipe.state();
		this.position = swipe.position();
		this.direction = swipe.direction();
		this.startPosition = swipe.startPosition();
	}
	
	public int getId() {
		return this.id;
	}
	
	public State getState() {
		return this.state;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public Vector getDirection() {
		return this.direction;
	}
	
	public Vector getStartPosition() {
		return this.startPosition;
	}
	
	
}
