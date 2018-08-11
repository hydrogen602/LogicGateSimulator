package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class Split extends BasicPart 
{

	public Split(int xCoord, int yCoord) 
	{
		super(1, 2, xCoord, yCoord);
	}
	
	public void updateNow() 
	{
		if (!needsUpdate)
			return;
		
		// stuff here
		outputs[0] = inputs[0];
		outputs[1] = inputs[0];
		
		updateClose();
	}
	
	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);

		int x = getX();
		int y = getY();
		
		g.setColor(Color.GRAY);
		int[] xs = {x, x, x+30, x+30, x+10, x+30, x+30};
		int[] ys = {y+10, y+20, y+30, y+20, y+15, y+10, y};
		g.fillPolygon(xs, ys, 7);
		//g.fillRect(getX(), getY(), 30, 30);
	}

	public String toString() {
		return "SPLIT";
	}
}
