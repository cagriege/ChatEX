package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {

	ServerSocket ss;
	boolean quite=false;
	ArrayList<cokluBaglanti> Bnmbaglanti=new ArrayList<cokluBaglanti>();
	
	public static void main(String[] args) {
		new server();

	}
	public server() {
		try {
			ss=new ServerSocket(3333);
			while(!quite)
			{
				Socket s=ss.accept();
				cokluBaglanti OurConnection = new cokluBaglanti(s,this);
				OurConnection.start();
				Bnmbaglanti.add(OurConnection);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}