import java.net.*;
import java.io.*;

public class TCPFileServer
{
	public static void main(String []args)
	{
		try (
			// creating server socket and bind it with socket 8888
			ServerSocket ss=new ServerSocket(8888);

			// accepting a request from client
			Socket s=ss.accept();

			// creating reference of a PrintStream
			PrintStream ps=new PrintStream(s.getOutputStream());

			// creating reference of a BufferedReader
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
		) {
			// getting file name from client
			String filename=br.readLine().trim();
			System.out.println(filename);

			try
			{
				// creating file object to work with a file
				File f=new File(filename);  
				BufferedReader file=new BufferedReader(new FileReader(f));

				String line;
				
				// reading file line by line and sending to client
				while((line=file.readLine())!=null)
				{
					ps.println(line);
				}
			}
			catch(Exception e)
			{
				ps.println("Sorry!! File not exists..");
				throw new Exception("File not found");
			}
		}
		catch(Exception e)
		{
			System.out.println("Request not satisfied");
		}
	}
}