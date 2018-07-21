package FileIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IOReader 
{	
	private String path;
	private FileReader r;
	private BufferedReader b;
	
	
	public IOReader(String file) throws FileNotFoundException
	{
		path = file;
		r = null;
		b = null;
		
		r = new FileReader(path);
		b = new BufferedReader(r);
	}
	
	public String readln()
	{
		try 
		{
			return b.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String readAll()
	{
		String r = "";
		String tmp;
		while (true)
		{
			tmp = readln();
			if (tmp == null)
				return r;
			else
				r += tmp + "\n";
		}
	}
	
	public void close()
	{
		try {
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() throws IOException
	{
		b.close();
	}
}
