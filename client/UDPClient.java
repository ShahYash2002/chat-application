import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient
{
	public static void main(String []args) throws Exception
	{
		DatagramSocket clientSocket=new DatagramSocket(7777);
		Scanner sc=new Scanner(System.in);
		byte []sendingData=new byte[512];
		byte []receivingData=new byte[128];
		
		System.out.print("Enter data : ");
		String data=sc.nextLine();
		sendingData=data.getBytes();
		DatagramPacket sPacket=new DatagramPacket(sendingData,sendingData.length,InetAddress.getLocalHost(),8888);
		
		clientSocket.send(sPacket);
		System.out.println("Data Sent        : "+data);

		DatagramPacket rPacket=new DatagramPacket(receivingData,receivingData.length);
		
		clientSocket.receive(rPacket);
		System.out.println("Server Responded : "+(new String(rPacket.getData())).trim());
	}
}