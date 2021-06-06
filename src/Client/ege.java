package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
//our scanner import
import java.util.Scanner;

@SuppressWarnings("serial")
public class ege extends JFrame{

	private JTextField item1;
	private JTextField kulisim;
	private JTextField item3;
	private JTextArea display;
	private JTextArea Kulisim;
	private Container c;
	CokluClient ClientThread;
	private JLabel label;
	private JLabel label1;
	private final static String newline = "\n";

	public ege(){
		super("WOW");
		
		setLayout(new FlowLayout());
		
		display = new JTextArea(30, 30);
		JScrollPane scrollPane = new JScrollPane(display); 
		display.setEditable(false);
		
		add(scrollPane);
		
		Kulisim = new JTextArea(30, 10);
		JScrollPane scrollPane3 = new JScrollPane(Kulisim); 
		Kulisim.setEditable(false);
		
		add(scrollPane3);
		
		item1 = new JTextField(20);
		item1.setEditable(false);
		add(item1);

        kulisim = new JTextField(20);
        kulisim.setEditable(true);
		add(kulisim);

		label=new JLabel("Nick gir:");
		label1=new JLabel("Kanal Sec:");
		
		c = this.getContentPane(); 
		c.setLayout(new FlowLayout());
		c.add(label1);
		c.add(kulisim);
		
		item3 = new JTextField(20);
		item3.setEditable(false);
			
		c = this.getContentPane(); 
		c.setLayout(new FlowLayout());
		c.add(label);
		c.add(item3);
		
		thehandler handler = new thehandler();
		item1.addActionListener(handler);
		kulisim.addActionListener(handler); 
		item3.addActionListener(handler);
		try {
			Socket s = new Socket("127.0.0.1",3333);
			ClientThread = new CokluClient(s,this);
			ClientThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent event){

			String string = "";

			if(event.getSource()==item1)
			{
				string=String.format("%s", event.getActionCommand());
				String text= item1.getText();
				ClientThread.ClientOutServerIn(text);
				item1.setText("");
			}
			else if(event.getSource()==kulisim) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[0-9]*"))
				{
					JOptionPane.showMessageDialog(null,"yanlis format");
					kulisim.setText("");
				}
				else
				{
					ClientThread.setName(string);
					ClientThread.SetClient("kanal0",string);
					JOptionPane.showMessageDialog(null, "isim degistirildi: "+string);
					kulisim.setText("");
					kulisim.setEditable(false);
					item1.setEditable(true);
					item3.setEditable(true);
					ClientThread.ClientOutServerIn("yeni kullanici");
					label.setVisible(false);
                                        label1.setVisible(false);
				}
			}
			else if(event.getSource()==item3) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[a-z A-Z]"))
				{
					JOptionPane.showMessageDialog(null,"yanlis format");
					item3.setText("");
				}
				else
				{
					ClientThread.c.SetChannel("kanal"+string);
					JOptionPane.showMessageDialog(null, "Kanal secildi: Kanal:"+string);
					item3.setText("");
					ClientThread.ClientOutServerIn("kanal degistir");
				}
			}
		}
	}
	public void setDisplay(String x)
	{
		display.append(x + newline); 
	}
	public void setUserInChannel(String x)
	{
		Kulisim.append(x + newline);
	}
	public void ClearDisplay()
	{
		Kulisim.setText("");
	}
}