package Packing;

public class WirePack 
{
	public String class_;
	public int myID;
	
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	
	public boolean state;
	
	public int inputID;
	public int inputNum;
	
	public WirePack(String s)
	{
		if (s.startsWith("{"))
			s = s.substring(1);
		
		if (s.endsWith(";}"))
			s = s.substring(0, s.length() - 2);
		
		String tmp[] = s.split("; ");
		String tmp2[];
		for (int i = 0; i < tmp.length; i++)
		{
			tmp2 = tmp[i].split("=");
			
			if (tmp2[0].equals("class"))
				class_ = tmp2[1];
			
			else if (tmp2[0].equals("myID"))
				myID = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("x1"))
				x1 = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("y1"))
				y1 = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("x2"))
				x2 = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("y2"))
				y2 = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("state"))
				state = Boolean.parseBoolean(tmp2[1]);
			
			else if (tmp2[0].equals("inputID"))
				inputID = Integer.parseInt(tmp2[1]);
			
			else if (tmp2[0].equals("inputNum"))
				inputNum = Integer.parseInt(tmp2[1]);
			
			else
				throw new IllegalArgumentException("Unknown String: " + tmp2[0]);
		}
		
		//System.out.println(s);
	}
	
	/**
	 * Shows all data in this instance
	 */
	public String toString()
	{
		String s = "<WirePack>\n";
		s += "class=" + class_ + ";\n";
		s += "myID=" + myID + ";\n";
		s += "x1=" + x1 + ";\n";
		s += "y1=" + y1 + ";\n";
		s += "x2=" + x2 + ";\n";
		s += "y2=" + y2 + ";\n";
		s += "state=" + state + ";\n";
		s += "inputID=" + inputID + ";\n";
		s += "inputNum=" + inputNum + ";\n";
		
		return s + "</WirePack>";
	}
	
	
}
