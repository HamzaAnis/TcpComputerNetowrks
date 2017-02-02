package source.main;


import java.io.IOException;
import java.util.Scanner;

import tcp.Client.Client;

public class Source {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("<<--- Welcome this is client !! --->>");
		
		Scanner ConsoleReader=new Scanner(System.in);
//		System.out.print("Enter the Server Ip Adres: ");
//		String ServerIp=ConsoleReader.nextLine();
		System.out.print("\nEnter the Server Port: ");
		String ServerPort=ConsoleReader.nextLine();
		Client obj=new Client("localhost", Integer.parseInt(ServerPort));
		try {
			obj.doTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConsoleReader.close();
		}

}
