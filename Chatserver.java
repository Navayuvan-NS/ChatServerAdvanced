import javax.swing.*;  
import java.awt.event.*;  
import java.io.*;
import java.net.*;

class Chatserver {

	public static void main(String[] args) {
		try{
			MyserverT myservert = new MyserverT();
			myservert.start();
		}
		catch(Exception e){
			System.out.println("Oops....!! Sorry. Error occured in Thread");
			System.exit(0);
		}
	}
}

class MyserverT extends Thread{

	JLabel l1,l2;
	JTextArea area,space;
	JButton b;
	DataInputStream din;
	DataOutputStream dout;
	String msgin="",msgout="";
	MyserverT(){
		JFrame f = new JFrame("Server");

		l1 = new JLabel();
		l1.setBounds(220,10,280,30);
		l1.setText("Server");
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

		space = new JTextArea();
		space.setBounds(5,540,490,50);
		space.setFont(space.getFont().deriveFont(25.0f));
		f.add(space);

		f.setSize(500,700);
		f.setLayout(null);
		f.setVisible(true);
	}

	public void run(){
		try{
			ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream());
	        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

	        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        
	        while(!msgin.equals("end")){
	               msgin = din.readUTF();
	               //System.out.println(msgin);
	               area.append("Client: "+msgin+"\n");
	               //msgout = br.readLine();
	               
	               b.addActionListener(new ActionListener(){
	               		public void actionPerformed(ActionEvent e){
	               			msgout = space.getText();
	               			call(msgout);
	               		}
	               });
	               //dout.writeUTF(msgout);    
	        }
	    }
		catch(Exception e){
			//System.out.println("Oops...!!! Problem in starting Server Socket");
			space.setText("Oops...!!! Problem in starting Server Socket");
			space.setEditable(false);
			System.exit(0);
		}
	}

	public void call(String a){
		try{
			area.append("Server: "+a+"\n");
			dout.writeUTF(a);
			dout.flush();
		}
		catch(Exception e){
			space.setText("Error");
		}
	}

}