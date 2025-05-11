import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable 
{
	//Creating a ArrayList to handle all the Users Connected.
	public static ArrayList<ClientHandler> arrClient =new ArrayList<>();
	
	private Socket soc;
	private BufferedReader br;
	private BufferedWriter bw;
	private String uname;
	
	public ClientHandler(Socket soc)
	{
		try
		{
			//Storing to the current instances.
			this.soc=soc;
			this.bw=new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
			this.br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
		
			//Reading the Username from the BufferedReader.
			this.uname=br.readLine();
			
			//Adding the Usernames to th ArrayList.
			arrClient.add(this);
			
			//Passing the a String to the SendMessage() method.
			SendMessage("Server : "+uname+" has entered the chat!");
		}
		catch(IOException e)
		{
			closeEveryThing(soc,br,bw);
		}
	}
	
	public void run()
	{
		String msg;
		//While the Socket is Connected , Read the msg and passing it to the SendMessage() method.
		while(soc.isConnected())
		{
			try
			{
				msg=br.readLine();
				SendMessage(msg);
			}
			catch(IOException e)
			{
				closeEveryThing(soc,br,bw);
				break;
			}
		}
	}
	
	public void SendMessage(String str)
	{
		//for every 'i' in 'arrclient' .
		for(ClientHandler i : arrClient)
		{
			try
			{
				//This method will write the msg to all the user expect itself.
				if(!i.uname.equals(uname))
				{
					i.bw.write(str);
					i.bw.newLine();
					i.bw.flush();
				}
			}
			catch(IOException e)
			{
				closeEveryThing(soc,br,bw);
			}
		}
	}
	//If a user leaves or losses the socket connention so removing it from the ArrayList.
	public void removeClient()
	{
		arrClient.remove(this);
		SendMessage("Server : "+uname+" has left the chat!");
	}
	
	public void closeEveryThing(Socket soc,BufferedReader br,BufferedWriter bw)
	{
		//If any Exception occur closing all the components i.e socket, BufferedReader and Bufferedwriter.
		removeClient();
		try
		{
			if(soc != null)
				soc.close();
			if(bw != null)
				bw.close();
			if(br != null)
				br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
