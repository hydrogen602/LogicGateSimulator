package Packing;

public class DataPack 
{
	public String class_;
	public int myID;
	public int x;
	public int y;
	public boolean inputs[];
	public boolean outputs[];
	public char key;
	public boolean isMenu;
	public WirePack[] outputWires; // TODO find something better
	
	/**
	 * Extracts the information from a list of arguments generated by {@code HelperMethods.packUp}
	 * 
	 * Constructor
	 * @param args A list of arguments in the format of example=stuff;
	 */
	public DataPack(String[] args)
	{
		key = '\0';
		
		String[] tmp;
		for (int i = 0; i < args.length; i++)
		{	
			if (args[i].endsWith(";"))
				args[i] = args[i].substring(0, args[i].length() - 1);
			
			tmp = args[i].split("=", 2);
			if (tmp.length < 2)
				continue;
			
			if (tmp[0].equals("class"))
				class_ = tmp[1];
			
			else if (tmp[0].equals("myID"))
				myID = Integer.parseInt(tmp[1]);
			
			else if (tmp[0].equals("x"))
				x = Integer.parseInt(tmp[1]);
			
			else if (tmp[0].equals("y"))
				y = Integer.parseInt(tmp[1]);
			
			else if (tmp[0].equals("inputs"))
				inputs = stringToBoolArray(tmp[1]);
			
			else if (tmp[0].equals("outputs"))
				outputs = stringToBoolArray(tmp[1]);
			
			else if (tmp[0].equals("key"))
				key = tmp[1].charAt(0);
			
			else if (tmp[0].equals("isMenu"))
				isMenu = Boolean.parseBoolean(tmp[1]);
			
			else if (tmp[0].equals("io"))
			{
				//System.out.println(tmp[1]);
				if (tmp[1].startsWith("{"))
					tmp[1] = tmp[1].substring(1);
				
				if (tmp[1].endsWith(";}"))
					tmp[1] = tmp[1].substring(0, tmp[1].length() - 2);
				
				String tmp2 = tmp[1].split("=", 2)[1];
				
				if (tmp2.equals("[]"))
				{
					outputWires = new WirePack[0];
					continue;
				}
				
				if (tmp2.startsWith("["))
					tmp2 = tmp2.substring(1);
				
				if (tmp2.endsWith("]"))
					tmp2 = tmp2.substring(0, tmp2.length() - 1);
				
				String[] tmp3 = tmp2.split(",");
				//System.out.println("$" + tmp2 + "$");
				
				outputWires = new WirePack[tmp3.length];
				
				for (int n = 0; n < tmp3.length; n++)
				{
					if (tmp3[n].equals("null"))
						outputWires[n] = null;
					else if (tmp3[n].startsWith("{"))
						outputWires[n] = new WirePack(tmp3[n]);
	
					else
						throw new IllegalArgumentException("Unknown String: " + tmp3[n]);
				}
				
								
			}
			else
				throw new IllegalArgumentException("Unknown String: " + tmp[0]);
		}
	}
	
	/**
	 * Another function for extracting information
	 * 
	 * @param s A String in the format of [1,1,0,0]
	 * @return a boolean array
	 */
	private static boolean[] stringToBoolArray(String s)
	{
		if (s.startsWith("["))
			s = s.substring(1);
		
		if (s.endsWith("]"))
			s = s.substring(0, s.length() - 1);
		
		if (s.equals(""))
			return new boolean[0];
		
		String[] tmp = s.split(",");
		boolean[] result = new boolean[tmp.length];
		
		for (int i = 0; i < tmp.length; i++)
		{
			if (tmp[i].equals("1"))
				result[i] = true;
			else if (tmp[i].equals("0"))
				result[i] = false;
			else
				throw new IllegalArgumentException("Unknown String: " + tmp[i]);
		}
		
		return result;
	}
	
	/**
	 * Turns an array of booleans into a string for printing to stdout
	 * @param booleans
	 * @return
	 */
	private static String boolArrayToString(boolean[] booleans)
	{
		String s = "";
		for (boolean b: booleans)
			s += b + ",";
		
		if (s.length() > 0)
			s = s.substring(0, s.length() - 1);
		
		return "[" + s + "]";
	}
	
	/**
	 * Shows all data stored in an instance of a class
	 */
	public String toString()
	{
		String s = "<DataPack>\n";
		s += "class=" + class_ + ";\n";
		s += "myID=" + myID + ";\n";
		s += "x=" + x + ";\n";
		s += "y=" + y + ";\n";
		s += "inputs=" + boolArrayToString(inputs) + ";\n";
		s += "outputs=" + boolArrayToString(outputs) + ";\n";
		if (key != '\0')
			s += "key=" + key + ";\n";
		s += "isMenu=" + isMenu + ";\n"; 
		s += "outputWires={\n";
		for (WirePack w: outputWires)
		{
			if (w == null)
				s += "null\n";
			else
				s += w.toString() + "\n";
		}
		s += "};\n";
		
		return s + "</DataPack>";
	}
	
}
