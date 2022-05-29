import java.net.*;
import java.io.*;

public class UDPFileServer
{
	public static void main(String []args) throws Exception
	{
		// creating datagram socket and bind it with port 7777 at server side
		DatagramSocket serverSocket=new DatagramSocket(7777);

		// data bytes
		byte []sData=new byte[4096];  // 4 kB
		byte []rData=new byte[4096];  // 4 kB	

		// creating datagram packet to receive data from client
		DatagramPacket rPacket=new DatagramPacket(rData,rData.length);
		// receive packet
		serverSocket.receive(rPacket);

		String filename=(new String(rPacket.getData())).trim();
	
		String fileContent="";
		try
		{
			// creating file object to work with the file
			File f=new File(filename);
			BufferedReader file=new BufferedReader(new FileReader(f));

			String line;
			// reading the file line by line
			while((line=file.readLine())!=null)
			{
				fileContent+=line+"\n";
			}
		}
		catch(Exception e)
		{
			System.out.println("File Not Found");
			fileContent="File not Found";
		}

		sData=fileContent.getBytes();  // converting to bytes

		// creating datagram packet for sending data to client
		DatagramPacket sPacket=new DatagramPacket(sData,sData.length,InetAddress.getLocalHost(),8888);
		// send packet
		serverSocket.send(sPacket);	
	}
}