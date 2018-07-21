package Parts;

import java.awt.Color;
import java.awt.Graphics;

public class GatedLatch extends BasicPart {

	public GatedLatch(int xCoord, int yCoord) 
	{
		super(2, 1, xCoord, yCoord);
	}

	public void updateNow() 
	{
		if (!needsUpdate)
			return;
		
		if (inputs[1])
			outputs[0] = inputs[0];
		
		updateClose();
	}

	public String toString() 
	{
		return "GATEDLATCH";
	}
	
	public void drawConnectors(Graphics g)
	{
		super.drawConnectors(g);

		g.setColor(Color.GRAY);
		g.fillRect(getX(), getY(), 30, 30);
		
		if (outputs[0])
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		
		g.drawLine(getX()+5, getY()+5, getX()+25, getY()+25);
		g.drawLine(getX()+25, getY()+5, getX()+5, getY()+25);
	}

}
