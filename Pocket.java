/** 
 * Defines an object representing a single pocket
 */
public class Pocket {
	
	//initializing the instance variables
	private int num;		//pocket number
	private String color;	//color of the pocket - black/red/green
	private boolean isOdd;	//indicates whether the pocket is even or odd
	
	public Pocket(int num, String color, boolean isOdd)
	{
		this.num = num;
		this.color = color;
		this.isOdd = isOdd;
	}
	
	/**
	 * Mutator methods
	 * This method is used so each time it is called, it can be used to set a variable.
	 */
	public void setNum(int num) { this.num = num; }
	public void setColor(String color) { this.color = color; }
	public void setParity(boolean isOdd) { this.isOdd = isOdd; }
	
	/**
	 * Accessor methods
	 */
	public int getNum() { return this.num; }
	public String getColor() { return this.color; }
	public boolean getParity() { return this.isOdd; }

}
