package tcp.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

//import com.sun.javafx.collections.MappingChange.Map;

public class Client {

	private String ServerIP;
	private int ServerPort;
	private Socket ClientSocket;
	private String ChatBuffer;
	
	
	LinkedList<String> clientDetails;
	public Client(String ServerIp, int ServerPort)
	{
		this.ServerIP=ServerIp;
		this.ServerPort=ServerPort;
	}
	
	
	public void doTasks() throws UnknownHostException, IOException, ClassNotFoundException
	{
		ClientSocket=new Socket(this.ServerIP,this.ServerPort);
		DataInputStream receive=new DataInputStream(ClientSocket.getInputStream());
		
		DataOutputStream sender=new DataOutputStream(ClientSocket.getOutputStream());
		
		Scanner ConsoleReader=new Scanner(System.in);
		
		System.out.println("Enter your Nick Name");
		String nickName=ConsoleReader.nextLine();
		sender.writeUTF(nickName);
		
		
		
		//showing active users
		System.out.print("Active users ");
	    ObjectInputStream mapInputStream = new ObjectInputStream(receive);
	    clientDetails= (LinkedList<String>) mapInputStream.readObject();
	   
	    System.out.println("\nThere are "+clientDetails.size()+" active users");
		int i=1;
	    for (Iterator<String> iterator = clientDetails.iterator(); iterator.hasNext();) {
	    	String string = (String) iterator.next();
			System.out.println(i+" : "+string);
			i++;
		}
	    
	    String name = null;
	    if(clientDetails.size()>1)
	    {
	    System.out.println("Whom do you want to chat to(Send Request)");
	    name=ConsoleReader.nextLine();
	    sender.writeUTF(name);
	    }
		
	    
	    //all this to do the sending order
	    System.out.println("You want to send(1) ");
		String choice=ConsoleReader.nextLine();
		boolean flag=false;
		if(choice.equals("1"))
		{
			flag=true;
		}
		
		while(true)
		{
			if(flag)
			{
			System.out.print ("\n\nTo "+nickName+" ==> "+name+"\n\t\t\t");
			ChatBuffer=ConsoleReader.nextLine();
			sender.writeUTF(ChatBuffer);
			}

		
			ChatBuffer=receive.readUTF();
			System.out.print("\n\nFrom "+name+" ==> "+nickName+"\n\t\t\t");
			System.out.println(ChatBuffer);
			flag=true;
		}
//		ClientSocket.close();
//		ConsoleReader.close();
		
	}
	
}
