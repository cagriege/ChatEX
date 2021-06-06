package Client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
//our scanner import
import java.util.Scanner;

import javax.swing.JFrame;


public class client{
	
	CokluClient ClientThread;
	public static void main(String[] args) {
		new client();
	}
	public client()
	{	
		ege crape = new ege();
		crape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crape.setSize(500,600);
		crape.setVisible(true);
		
	}

	public void ListenForInput()
	{
		
		@SuppressWarnings("resource")
		Scanner console=new Scanner(System.in);
		while(true)
		{
			
			while(!console.hasNextLine())
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String input=console.nextLine();
			if(input.toLowerCase().equals("çıkış"))
			{
				break;
			}
			if(input.toLowerCase().equals("kanal degistir"))
			{
				input=console.nextLine();
				ClientThread.c.SetChannel(input);
			}
			else
			{
				ClientThread.ClientOutServerIn(input);
			}
		}
		ClientThread.CloseClient();
	}
}