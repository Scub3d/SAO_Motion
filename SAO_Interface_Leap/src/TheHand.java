import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;


public class TheHand {
	
	private Finger frontMostFinger;
	private FingerList fingers;
	private boolean isValid;
	private Vector direction;
	private Vector palmPosition;
	private int id;

	public TheHand(Hand hand) {
		this.frontMostFinger = hand.fingers().frontmost();
		this.fingers = hand.fingers();
		this.isValid = hand.isValid();
		this.direction = hand.direction();
		this.palmPosition = hand.palmPosition();
		this.id = hand.id();
	}
	
	public Finger getFrontMostFinger() {
		return this.frontMostFinger;
	}
	
	public Vector getFrontMostFingerTipPosition() {
		return this.frontMostFinger.tipPosition();
	}
	
	public FingerList getFingers() {
		return this.fingers;
	}
	
	public boolean getIsValid() {
		return this.isValid;
	}
	
	public Vector getDirection() {
		return this.direction;
	}
	
	public Vector getPalmPosition() {
		return this.palmPosition;
	}
	
	public int getId() {
		return this.id;
	}
}
