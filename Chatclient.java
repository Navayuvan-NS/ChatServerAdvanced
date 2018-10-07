import javax.swing.*;  
import java.awt.event.*; 
import java.io.*;
import java.net.*;

class Chatclient {

	public static void main(String[] args) {
		try{
			MyclientT myclientt = new MyclientT();
			myclientt.start();
		}
		catch(Exception e){
			System.out.println("Oops....!! Sorry. Error occured in Thread");
			System.exit(0);
		}
	}
}

class MyclientT extends Thread{

	JLabel l1,l2;
	JTextArea area;
	JTextField space;
	JButton b;
	DataInputStream din;
	DataOutputStream dout;
	String msgin="",msgout="";
	MyclientT(){
		JFrame f = new JFrame("Client");

		l1 = new JLabel();
		l1.setBounds(220,10,280,30);
		l1.setText("Client");
		f.add(l1);

		area = new JTextArea();
		area.setBounds(5,50,490,480);
		area.setVisible(true);
		area.setFont(area.getFont().deriveFont(20.0f));
		area.setEditable(false);
		f.add(area);	

		b = new JButton("Send");
		b.setBounds(200,600,100,40);
		f.add(b);

		space = new JTextField(20);
		space.setBounds(5,540,490,50);
		space.setFont(space.getFont().deriveFont(25.0f));
		f.add(space);

		f.setSize(500,700);
		f.setLayout(null);
		f.setVisible(true);
	}

	public void run(){
		try{
			Socket s = new Socket("localhost",6666);
			DataInputStream din = new DataInputStream(s.getInputStream());
        	DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        	//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        
	        while(!msgin.equals("end")){
	              
	               msgout = space.getText();
	               //msgout = br.readLine();
	               area.append("You: "+msgout+"\n"); 
	               b.addActionListener(new ActionListener(){
	               		public void actionPerformed(ActionEvent e){
	               			call();
	               		}
	               });
	               msgin = din.readUTF();
	               //System.out.println(msgin);
	               area.append("Server: "+msgin+"\n");            
	           }
		}
		catch(Exception e){
			//System.out.println("Oops...!!! Problem in starting Client Socket.");
			space.setText("Oops...!!! Problem in starting Client Socket");
			space.setEditable(false);
			System.exit(0);
		}
	}

	public void call(){
		try{
			area.append("You: "+msgout+"\n"); 
		    dout.writeUTF(msgout);
		}
		catch(Exception e){
			space.setText("Error");
		}
	}
}