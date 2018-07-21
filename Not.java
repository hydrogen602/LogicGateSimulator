package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class Not extends BasicPart 
{
	public Not(int xCoord, int yCoord) 
	{
		super(1, 1, xCoord, yCoord);
		this.outputs[0] = true;
	}
	
	public void updateNow() 
	{
		if (!needsUpdate)
			return;
		
		// stuff here
		outputs[0] = !inputs[0];
		
		updateClose();
	}

	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);

		g.setColor(Color.GRAY);
		int[] xs = {getX(), getX()+30, getX()};
		int[] ys = {getY(), getY()+15, getY()+30};
		g.fillPolygon(xs, ys, 3);
		//g.fillArc(getX()+10-20, getY(), 40, 30, -90, 180);
		//g.fillRect(getX(), getY(), 10, 30);
	}

	public String toString() 
	{
		return "NOT";
	}

}
