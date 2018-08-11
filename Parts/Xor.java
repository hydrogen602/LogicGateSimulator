/**
 * 
 */
package Parts;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Jonathan
 *
 */
public class Xor extends BasicPart 
{
	public Xor(int xCoord, int yCoord) 
	{
		super(2, 1, xCoord, yCoord);
	}
	
	public void updateNow()
	{
		if (!needsUpdate)
			return;
		
		// stuff here		
		outputs[0] = inputs[0] || inputs[1];
		if (inputs[0] && inputs[1])
			outputs[0] = false;
		
		updateClose();
	}
	
	public void drawConnectors(Graphics g)
	{
		g.setColor(Color.GRAY);
		//g.drawRect(getX(), getY(), 30, 30);
		//g.fillArc(getX()+10-20, getY(), 40, 30, -90, 90);
		g.fillArc(getX()-40+5, getY(), 70-2, 45, 20, 70);
		g.fillArc(getX()-40+5, getY()-15, 70-2, 45, -20, -70);
		
		Color c = new Color(238, 238, 238);
		g.setColor(c);
		g.fillArc(getX()-11, getY(), 20, 30, -90, 180);
		//g.fillRect(getX(), getY(), 10, 30);
		
		super.drawConnectors(g);
		
		g.setColor(Color.GRAY);
		g.drawArc(getX()-16-7, getY(), 30, 30, -70, 140);
		g.drawArc(getX()-17-7, getY(), 30, 30, -70, 140);
	}
	
	public String toString() 
	{
		return "XOR";
	}
	

}
