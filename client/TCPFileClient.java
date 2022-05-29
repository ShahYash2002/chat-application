import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPFileClient
{
	public static void main(String []args) throws Exception
	{
		try (
			// creating socket to connection to server at port 8888
			Socket s=new Socket("localhost",8888);
			
			// creating reference of a PrintStream
			PrintStream ps=new PrintStream(s.getOutputStream());

			// creating reference of a BufferedReader
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));

			// creating reference to Scanner
			Scanner sc=new Scanner(System.in);
		) {
			System.out.print("Enter a file name : ");
			String file=sc.nextLine().trim();  // reading file name 

			ps.println(file);  // sending filename to server

			String line=br.readLine();
			if(line.equals("Sorry!! File not exists.."))
			{
				System.out.println(line);
			}
			else
			{
				// creating FileWriter object to write into file
				FileWriter f=new FileWriter(file);
				while(line!=null)
				{
					f.write(line+"\n");
					line=br.readLine();
				}
				f.close();  // closing the file
			}
		}			
	}
}