
public abstract class Menu {
	
	public int startingXpos;
	public int startingYpos;
	public int height;
	public int width;
	public int numberOfClickables;
	public int distanceBetweenClickables;
	public int clickableWidth;
	public int clickableHeight;
	
	
	public abstract void scrollClickables();

	public int getStartingXpos() {
		return this.startingXpos;
	}
	
	public int getStartingYpos() {
		return this.startingXpos;
	}
	
	

}
