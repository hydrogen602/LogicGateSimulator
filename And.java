package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class And extends BasicPart
{
	public And(int xCoord, int yCoord) 
	{
		super(2, 1, xCoord, yCoord);
	}
	
	public void updateNow()
	{
		if (!needsUpdate)
			return;
		
		// stuff here
		outputs[0] = inputs[0] && inputs[1];
		
		updateClose();
	}
	
	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);

		g.setColor(Color.GRAY);
		g.fillArc(getX()+10-20, getY(), 40, 30, -90, 180);
		g.fillRect(getX(), getY(), 10, 30);
	}
	
	public String toString()
	{
		return "AND";
	}
}
