import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class Server  
{		
	private ServerSocket ss;
	private JTextArea ta;
	private JFrame f;
	private JScrollPane sp;
	public Server(ServerSocket ss)
	{
		//Passing the ServerSocket to the current instance.
		this.ss=ss;
		
		//Creting the Components.
		f=new JFrame("Server");
		
		ta= new JTextArea(10,10);
		ta.setEditable(false);
		ta.setBackground(new Color(44, 62, 80));
		ta.setForeground(Color.white);
		
		sp= new JScrollPane(ta);
		
		//Adding to the JFrame.
		f.add(sp,BorderLayout.CENTER);
		f.setSize(400, 400);
		f.setVisible(true);
	}
	
	public void startServer()
	{
		try
		{
			//Till the ServerSocket is Opened or On , we can continue to accept new users.
			while(!ss.isClosed())
			{
				Socket soc=ss.accept();
				ta.append("A new Client Has Connected .\n");
		
				//Creating a Seperate ClientHandler Class ,to handle clients who are Connected. 
				ClientHandler c1=new ClientHandler(soc);
			
				//Passing the instances of ClientHandler class to th thread class to achieve Multithreading(i.e. handling the processes concurrently).
				Thread thread = new Thread(c1);
				
				//To begin the execution.
				thread.start(); 
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void closeSS()
	{
		//Closing the ServerSocket.
		try
		{
			if(ss != null)
			{
				ss.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException 
	{
		//Creating a ServerSocket with the port:1234.
		ServerSocket ss=new ServerSocket(1234);
		
		//Creatin a object ,and passing ServerSocket to the construstor.
		Server server=new Server(ss);
		
		//Calling the startServer() method.
		server.startServer();
	}
}
