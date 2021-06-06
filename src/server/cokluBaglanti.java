package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class cokluBaglanti extends Thread {
	
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	server ss;
	boolean quite=false;
	
	public cokluBaglanti(Socket OurSocket,server OurServer)
	{
		super("MultiServerConnection");
		this.s=OurSocket;
		this.ss=OurServer;
	}
	
	public void ServerOutClientIn(String OutText)
	{
		try {
			long ThreadID=this.getId();
			dout.writeUTF(OutText);
			dout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ServerOutAllClientIn(String OutText)
	{
		for(int i=0;i<ss.Bnmbaglanti.size();i++)
		{
			cokluBaglanti Connection=ss.Bnmbaglanti.get(i);
			Connection.ServerOutClientIn(OutText);
		}
	}
	
	public void run()
	{
		try {
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			
			while(!quite)
			{
				while(din.available()==0)
				{
					try {
						Thread.sleep(1);//sleep if there is not data coming
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String ComingText=din.readUTF();
				ServerOutAllClientIn(ComingText);
			}
			din.close();
			dout.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}