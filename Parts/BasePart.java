package Parts;

import java.awt.Graphics;

import Wires.Wire;

public abstract class BasePart 
{
	protected boolean[] inputs;
	protected boolean[] outputs;
	protected Wire[] outputWires;
	protected Wire[] inputWires;
	private int x, y;
	
	protected boolean needsUpdate;
	public boolean isMenu;
	
	protected static int BasePartID = 0;
	protected int myID;
	
	/**
	 * Constructor
	 * 
	 * @param in number of inputs
	 * @param out number of outputs
	 * @param xCoord X coordinate
	 * @param yCoord Y coordinate
	 */
	public BasePart(int in, int out, int xCoord, int yCoord)
	{
		myID = BasePartID;
		BasePartID++;
		
		needsUpdate = false;
		isMenu = true; // use part.isMenu = false; if its not part of the menu
		
		inputs = new boolean[in];
		inputWires = new Wire[in];
		outputs = new boolean[out];
		outputWires = new Wire[out];
		
		x = xCoord;
		y = yCoord;
	}
	
	/**
	 * Use only for reconstruction from file
	 * <p>
	 * Will overwrite all variables
	 * 
	 * @param inputs
	 * @param outputs
	 * @param x
	 * @param y
	 * @param isMenu
	 * @param myID
	 */
	public void reconstruct(boolean[] inputs, boolean[] outputs, int x, int y, boolean isMenu, int myID)
	{
		this.inputs = inputs;
		this.outputs = outputs;
		this.x = x;
		this.y = y;
		this.isMenu = isMenu;
		this.myID = myID;
		
		if (this.myID <= BasePartID)
			BasePartID = this.myID + 1;
		
		inputWires = new Wire[inputs.length];
		outputWires = new Wire[outputs.length];
	}
	
	/**
	 * Creates a new wire instance and passes the arguments to the constructor plus this.
	 * 
	 * @param outputNum Node number where the wire originates
	 * @param InputPart The BasePart that the wire is connecting to
	 * @param inputNum Node number where the wire is going to
	 */
	public void setOutputWire(int outputNum, BasicPart InputPart, int inputNum)
	{
		outputWires[outputNum] = new Wire(InputPart, inputNum, this, outputNum);
	}
	
	/**
	 * Creates a new wire instance and passes the arguments to the constructor plus this.
	 * <p>
	 * For creating a wire instance when the target BasePart is not yet determined
	 * 
	 * @param outputNum
	 * @return A reference to the wire instance created
	 */
	public Wire setOutputWireUnknown(int outputNum)
	{
		outputWires[outputNum] = new Wire(this, outputNum);
		return outputWires[outputNum];
	}
	
	/**
	 * Only for reconstructing wires from a file
	 * 
	 * @param outputNum
	 * @return A wire instance
	 */
	public Wire setOutputWireReconstruct(int outputNum)
	{
		outputWires[outputNum] = new Wire(this, outputNum, true);
		return outputWires[outputNum];
	}
	
	/**
	 * Does not update the wire instance and should only be called from a wire instance
	 * @param p
	 * @param q
	 */
	public void switchOutputNode(int p, int q)
	{
		Wire w = outputWires[p];
		outputWires[p] = outputWires[q];
		outputWires[q] = w;
	}

	/**
	 * Does not update the wire instance and should only be called from a wire instance
	 * @param p
	 * @param q
	 */
	public void switchInputNode(int p, int q)
	{
		Wire w = inputWires[p];
		inputWires[p] = inputWires[q];
		inputWires[q] = w;
	}
	
	/**
	 * Removes the input BasePart reference, used when a wire or BasePart is being removed
	 * 
	 * @param num
	 */
	public void removeRefIn(int num)
	{
		inputWires[num] = null;
	}

	/**
	 * Removes the output BasePart reference, used when a wire or BasePart is being removed
	 * 
	 * @param num
	 */
	public void removeRefOut(int num)
	{
		outputWires[num] = null;
	}
	
	
	public void giveRef(Wire w, int inputNum)
	{
		//System.out.println(this.toString());
		inputWires[inputNum] = w;
	}
	
	public void setInput(int num, boolean value)
	{
		inputs[num] = value;
	}
	
	public boolean getInput(int num)
	{
		return inputs[num];
	}
	
	public boolean getOutput(int num)
	{
		return outputs[num];
	}
	
	public int getInputNum()
	{
		return inputs.length;
	}
	
	public int getOutputNum()
	{
		return outputs.length;
	}
	
	public void update() // call this to update it this tick
	{
		needsUpdate = true;
	}
	
	public abstract void updateNow(); // call this at the end of every tick
	
	public abstract void updateXY(); // defined in BasicPart
	
	public int getX() {
		return x;
	}

	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
		updateXY();
	}
	
	public void setX(int x) {
		this.x = x;
		updateXY();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		updateXY();
	}
	
	public int getID()
	{
		return myID;
	}
	
	protected void updateClose()
	{
		for (int i = 0; i < outputs.length; i++)
		{
			if (outputWires[i] != null)
				outputWires[i].update(outputs[i]);
		}
		needsUpdate = false;
	}
	
	public abstract void drawConnectors(Graphics g);
	
	/**
	 * Call to try to remove references to this instance
	 */
	public void destroy()
	{
		inputs = null;
		outputs = null;
		
		for (int i = 0; i < inputWires.length; i++)
		{
			if (inputWires[i] != null)
			{
				inputWires[i].destroy();
				inputWires[i] = null;
			}
		}
		
		for (int i = 0; i < outputWires.length; i++)
		{
			if (outputWires[i] != null)
			{
				outputWires[i].destroy();
				outputWires[i] = null;
			}
		}
		
		//inputWires = null;
		//outputWires = null;
	}
	
	public abstract String toString();

	public boolean isOutputFree(int n) {
		return outputWires[n] == null;
	}
	
	public boolean isInputFree(int n) {
		return inputWires[n] == null;
	}
	
	protected static String boolArrayToString(boolean[] boolArray)
	{
		String s = "[";
		for (boolean b: boolArray)
		{
			if (b)
				s += "1,";
			else
				s += "0,";
		}
		if (s.length() > 1)
			s = s.substring(0, s.length() - 1);
		
		return s + "]";
	}
	
	protected String wiresToString()
	{
		String s = "{";
		
		String out = "";
		for (Wire w: outputWires)
			if (w == null)
				out += "null,";
			else
				out += w.persistent() + ",";
		if (out.length() > 0)
			out = out.substring(0, out.length() - 1);
		
		s += "outputWires=[" + out + "];";
		
		return s + "}";
	}
}
