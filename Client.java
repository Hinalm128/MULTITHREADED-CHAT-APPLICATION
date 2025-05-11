import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Client
{
	private Socket soc;
	private BufferedReader br;
	private BufferedWriter bw;
	private String uname;
	private JTextPane chatArea;
	private JButton send;
	private JFrame f;
	private JTextField msgField;
	private JPanel header,footer,p1,p2,p3;
	private JLabel backButton,title,callButton,videoCallButton,moreButton;
	private StyledDocument doc;
	private SimpleAttributeSet meStyle,otherStyle;

	public Client(Socket soc,String uname)
	{
		try
		{
			this.soc=soc;
			this.br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
			this.bw=new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
			this.uname=uname;
			
			//Writing the Username onto th BurrferedWriter So the the other classes and method can access the username until the socket is connected. 
			bw.write(uname);
			bw.newLine();
			bw.flush();
		
			//Creating components And Styling them by setting thier color and postion.
			f=new JFrame("Client - "+uname);
			f.setLayout(new BorderLayout());   
			
			header=new JPanel(new BorderLayout());

			p1=new JPanel(new FlowLayout(FlowLayout.LEFT));
			p1.setBackground(new Color(44, 62, 80));
			
			p2=new JPanel(new FlowLayout());
			p2.setBackground(new Color(44, 62, 80));
			
			p3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
			p3.setBackground(new Color(44, 62, 80));
					
			backButton = new JLabel("←");
			backButton.setForeground(new Color(26, 188, 156));
			backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            
			title = new JLabel(uname);
			title.setForeground(new Color(26, 188, 156));
            title.setFont(new Font("SansSerif", Font.BOLD, 16));
			
            callButton = new JLabel("📞");
            callButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
            callButton.setForeground(new Color(26, 188, 156));
            
            videoCallButton = new JLabel("📹");
            videoCallButton.setForeground(new Color(26, 188, 156));
            videoCallButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            moreButton = new JLabel("⋮");
            moreButton.setForeground(new Color(26, 188, 156));
            moreButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        
            chatArea=new JTextPane();
			chatArea.setEditable(false);
			chatArea.setBackground(new Color(236, 240, 241));
			
			//Creating a styled Document. So that the JTextPane can have Multiple color anf font styles.
			doc = chatArea.getStyledDocument();
						
			//Creating SimpleAttributeSet() to provide specified atyles to text in the JTextpane.
			meStyle = new SimpleAttributeSet();
			StyleConstants.setForeground(meStyle, new Color(142, 68, 173));
			StyleConstants.setBold(meStyle, true);
			StyleConstants.setAlignment(meStyle, StyleConstants.ALIGN_RIGHT);

			otherStyle = new SimpleAttributeSet();
			StyleConstants.setForeground(otherStyle, new Color(52, 73, 94));
			StyleConstants.setItalic(otherStyle, true);
			
			JScrollPane sp=new JScrollPane(chatArea);
			
			msgField= new JTextField(50); 
			msgField.setBackground(new Color(232, 240, 242));
			msgField.setForeground(new Color(44, 62, 80));
						
			send=new JButton("➤"); 
			send.addActionListener(e -> sendmsg());  //Calling the sendmsg() method when the JButton is pressed.
			send.setBackground(new Color(22, 160, 133));
			
			footer=new JPanel(new BorderLayout());
			
			//Adding the Components to thier respective JPanel.
			p1.add(backButton);
			
			p2.add(title);
			
			p3.add(callButton);
			p3.add(videoCallButton);
			p3.add(moreButton);
			
			header.add(p1,BorderLayout.WEST);
			header.add(p2,BorderLayout.CENTER);
			header.add(p3,BorderLayout.EAST);
			
			footer.add(msgField,BorderLayout.CENTER);
			footer.add(send,BorderLayout.EAST); 		
			
			//Adding to the JFrame. 
			f.add(header,BorderLayout.NORTH);
			f.add(sp,BorderLayout.CENTER);
			f.add(footer,BorderLayout.SOUTH);
			
			f.setSize(380,500); 
			f.setVisible(true);
			
			//Also calling the recievemsg() method in the contrustor itself.
			recievemsg();
		}
		catch(IOException e)
		{
			closeEveryThing(soc,br,bw);
		}
	}
	
	public void sendmsg()
	{
		try
		{
			//Storing the User Entered msg in the variable 'msgs'.
			String msgs=msgField.getText();
		
			//If the Socket Connection is established and the msgs variable is not null we will be contiune to write msgs in the Bufferedwriter.
			if(soc.isConnected() && msgs != null)
			{
				bw.write(uname+": "+msgs);
				bw.newLine();
				bw.flush();
				
				/*This try block is for Self Msg .. i.e. when the send button is clicked the msg should be sent to the Bufferedwriter
				  and also on the JTextPane with the specified Alignment.  */
				try
				{
		            doc.insertString(doc.getLength(), "Me : "+msgField.getText()+"\n", meStyle);
		            
		            /*Especially for Right-Align we have to call setParagraphAttributes() to impliment the rightAlignment.
		             offset : doc.getLength() - 1  ,Here i am calculating the n-1 index to insert at the end.*/
		            doc.setParagraphAttributes(doc.getLength() - 1, 1, meStyle, false);

		        } catch (BadLocationException e)
				{
		            e.printStackTrace();
		        }
				
				//We are Revalidating so that the inserted String will appear on to the JFrame with the Specified Styling.
				f.revalidate();
				
				//Clearing the JTextfield.
				msgField.setText("");
			}
		}
		catch(IOException e)
		{
			closeEveryThing(soc,br,bw);
		}
	}
	
	public void recievemsg()
	{
		/*
		 * Since This Application is a Grup Chat , Multiple Users can be connected and send msg simultaneously.
		 * To achieve this we use MultiThreading.
		 * Each instance of Client Class is associated with a new Thread, which i have done using the Runnable Interface.
		 */
		new Thread(new Runnable()
		{
			public void run()
			{
				String msg;
				//If the Socket Connection is established, we will be contiune to recive msgs.
				while(soc.isConnected())
				{
					try
					{
						//Reading the message and Storing in the 'msg' variable.
						msg=br.readLine();
						
						if(msg != null)
						{
							try
							{
								//Simply Inserting the Text in the JTextPane.
					            doc.insertString(doc.getLength(), msg+"\n", otherStyle);

					        } catch (BadLocationException e)
							{
					            e.printStackTrace();
					        }
						}

					}
					catch(IOException e)
					{
						closeEveryThing(soc,br,bw);
					}
				}
			}
		}).start();
		//Calling the start() method so that , each thread can be executed simuulaneously.
		
	}
	public void closeEveryThing(Socket soc,BufferedReader br,BufferedWriter bw)
	{
		//If any Exception occur closing all the components i.e socket, BufferedReader and Bufferedwriter.
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
	public static void main(String[] args) throws IOException {
		
		//Taking input of the Username .
		String uname = JOptionPane.showInputDialog(null, "Enter your Username for the group chat:");
		
		//If the username is entered and it does contain only spaces then , Creating Socket connection and calling the constructor.
        if (uname != null && !uname.trim().isEmpty())
        {
        	//Socket socket = new Socket("127.0.0.1", 1234);  This will also work.
            Socket socket = new Socket("localhost", 1234);
            new Client(socket, uname.trim());
        }
	}

}
