package FileIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class IOWriter 
{
	private String path;
	private PrintStream writer;
	
	public IOWriter(String file) throws FileNotFoundException
	{
		path = file;
		writer = null;
		
		writer = new PrintStream(path);
	}
	
	public void println(String s)
	{
		writer.println(s);
	}
	
	public void close()
	{
		writer.close();
	}
	
	public void finalize() throws IOException
	{
		writer.close();
	}
}
