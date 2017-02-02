package tcp.Server;

import java.io.IOException;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("<<---Hamza Welcome this is Server !! --->>");
		Scanner ConsoleReader=new Scanner(System.in);

		System.out.print("Enter the Server Port: ");
		String ServerPort=ConsoleReader.nextLine();
		try {
			MainServer obj=new MainServer(Integer.parseInt(ServerPort));
			obj.StartServer();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
