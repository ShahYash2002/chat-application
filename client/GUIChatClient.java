import java.net.*;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIChatClient extends JFrame implements ActionListener,Runnable
{
	Thread receiver;

	JPanel inputArea;
	JPanel chatArea;
	JTextArea jta;
	JButton send;

	Socket s;
	PrintStream ps;
	BufferedReader br;

	GUIChatClient()
	{
		super("ChatBox Client");
	
		receiver=new Thread(this);
		receiver.setName("Receiver");

		inputArea=new JPanel();
		chatArea=new JPanel();

		setLayout(new BorderLayout());
		inputArea.setLayout(new BorderLayout(5,0));
		chatArea.setLayout(new BoxLayout(chatArea,BoxLayout.PAGE_AXIS));

		chatArea.setBackground(new Color(0xdee3e2));

		chatArea.setPreferredSize(new Dimension(500,450));
		inputArea.setPreferredSize(new Dimension(500,50));
		
		add(inputArea,BorderLayout.SOUTH);
		add(chatArea,BorderLayout.CENTER);

		jta=new JTextArea(20,2);
		send=new JButton("SEND");

		inputArea.add(jta,BorderLayout.CENTER);
		inputArea.add(send,BorderLayout.EAST);
		inputArea.add(new JPanel(),BorderLayout.NORTH);
		inputArea.add(new JPanel(),BorderLayout.SOUTH);
		inputArea.add(new JPanel(),BorderLayout.WEST);
		
		send.addActionListener(this);

		repaint();

		try 
		{
			s=new Socket("localhost",8888);
			ps=new PrintStream(s.getOutputStream());
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch(Exception e)
		{

		}
		receiver.start();
	}
	
	public void run()
	{
		String msg;
		while(true)
		{
			try
			{
				msg=br.readLine();
				JLabel label=new JLabel();

				label.setText("Server : "+msg);
				label.setBackground(new Color(0xb0d9f7));
				label.setOpaque(true);
				label.setFont(new Font("Serif",Font.PLAIN,22));
				label.setHorizontalAlignment(JLabel.LEFT);
				chatArea.add(label);
			}
			catch(Exception e) {}
			finally
			{
				setSize(499,499);
			}
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
	}

	public void actionPerformed(ActionEvent ae)
	{
		JLabel label=new JLabel();	
		String msg=jta.getText().trim();

		label.setText("Me : "+msg);
		label.setBackground(new Color(0x9ee8d9));
		label.setOpaque(true);
		label.setFont(new Font("Serif",Font.PLAIN,22));
		label.setHorizontalAlignment(JLabel.LEFT);
		chatArea.add(label);

		if(msg.toLowerCase().equals("bye"))
		{
			send.setEnabled(false);
		}
			
		jta.setText("");
		setSize(500,500);
		repaint();
		
		ps.println(msg);		
	}

	public static void main(String []args)
	{
		GUIChatClient gui=new GUIChatClient();
		gui.setSize(500,500);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			gui.receiver.join();
		}
		catch(Exception e)
		{
			System.out.println(e);	
		}
	}
}