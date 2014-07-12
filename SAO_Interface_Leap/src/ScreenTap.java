import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.Vector;


public class ScreenTap {
	
	private int id;
	private State state;
	private Vector position;
	private Vector direction;
	private long duration;
	private Type type;
	
	public ScreenTap(ScreenTapGesture tap) {
		this.id = tap.id();
		this.state = tap.state();
		this.position = tap.position();
		this.direction = tap.direction();
		this.duration = tap.duration();
		this.type = tap.type();
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

	public long getDuration() {
		return this.duration;
	}
	
	public Type getType() {
		return this.type;
	}
}
