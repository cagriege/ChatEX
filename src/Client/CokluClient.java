package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CokluClient extends Thread {
	
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	boolean quite=false;
	public ClientData c;
	public ege GUI;
	
	public CokluClient(Socket OurMultiSocket, ege gui)
	{
		s=OurMultiSocket;
		c=new ClientData();
		GUI=gui;
	}
	public void ClientOutServerIn(String Text)
	{
		//write the line from console to server
		try {
			if(Text.equals("kanal degistir"))
			{
				System.out.print("kanal degistirme gönder: "+Text+"\n");
				dout.writeUTF(Text);
				dout.flush();
			}
			else if(Text.equals("yeni kullanıcı"))
			{
				System.out.print("yeni kullanıcı gönder: "+ Text+"\n");
				dout.writeUTF(Text+":"+c.GetName()+"="+c.GetChannel());
				dout.flush();
			}
			else
			{
				dout.writeUTF(c.GetChannel()+"="+this.getName()+": "+Text);
				dout.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void SetClient(String channel,String Name)
	{
		c.SetName(Name);
		c.SetChannel(channel);
	}
	public void run()
	{
		try {
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			while(!quite)
			{
				try {
					while(din.available()==0)
					{
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					String reply=din.readUTF();
					String Chan=ExtractChannel(reply);
					String name=ExtractName(reply);
					
					if(name.equals("yeni kullancı"))
					{
						System.out.print("yeni kullanıcı body: "+reply+"\n");
						
						setChannel(reply);
					}
					else
					{
						PrintReply(Chan,reply);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						din.close();
						dout.close();
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				din.close();
				dout.close();
				s.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
			}
		}
	}
	public void CloseClient()
	{
		try {
			din.close();
			dout.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ExtractName(String x)
	{
		String[]Y=x.split(":");
		return Y[0];
	}
	public String ExtractChannel(String X)
	{
		String[]Y=X.split("=");
		return Y[0];
	}
	public void PrintReply(String Chan,String Rep)
	{
		if(c.GetChannel().equals(Chan))
		{
			String []Y=Rep.split("=");
			GUI.setDisplay(Y[1]);
		}
		
	}
	public void setChannel(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		System.out.print("ayarlar "+Z[0]+" kanala "+Z[1]+"\n");
		GUI.setUserInChannel(Z[0]);
	}
	public void setChangedChannel()
	{
		GUI.setUserInChannel(c.GetName()+": "+c.GetChannel());
	}
	class ClientData
	{
		public String ClientName;
		public String channel;
		
		public void SetChannel(String Chan)
		{
			channel=Chan;
		}
		public void SetName(String name)
		{
			ClientName=name;
		}
		public String GetChannel()
		{
			return channel;
		}
		public String GetName()
		{
			return ClientName;
		}
	}
	
}