package tcp.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

//import com.sun.javafx.collections.MappingChange.Map;

class ConcurrentServer implements Runnable {

	public Socket ServiceSocket;
	public Socket socketChatR;
	public String nickName;
	private String ChatBuffer;
	HashMap<String, ConcurrentServer> clientDetails; // this will have the
														// details of the
														// clients
	private LinkedList<String> activeSend;

	public ConcurrentServer(Socket sock, String name, HashMap<String, ConcurrentServer> clientDetails) {
		// TODO Auto-generated constructor stub
		ServiceSocket = sock;
		socketChatR = sock;
		this.nickName = name;
		this.clientDetails = clientDetails;
		new Thread(this).start();

	}

	@Override
	public void run() {
		Scanner ConsoleReader = new Scanner(System.in);

		// displaying the information
		System.out.println("New cient connected");
		System.out.println("Port = " + ServiceSocket.getPort());
		System.out.println("IP = " + ServiceSocket.getLocalAddress());
		System.out.println("NickName = " + nickName);

		// sending the names of the active client
		activeSend = new LinkedList<String>();
		for (Map.Entry<String, ConcurrentServer> entry : clientDetails.entrySet()) {
			String key = entry.getKey();
			activeSend.add(key);
		}

		// a thread to receive the data
		Thread input = new Thread(new Runnable() {
			public void run() {
				// data received from one client
				receiveInout();
			}
		});
		input.start();

		// a thread to send the data
//		Thread output = new Thread(new Runnable() {
//			public void run() {
//				sendOutput();
//			}
//		});
//		output.start();
	}

	public void receiveInout() {
		DataInputStream reciever = null;

		while (true) {
			try {
				reciever = new DataInputStream(ServiceSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ChatBuffer = reciever.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Data Received from " + nickName + " : " + ChatBuffer);
		}

	}

	

}

public class MainServer {

	private int ServerPort;
	private ServerSocket HandShakingSocket;
	HashMap<String, ConcurrentServer> cliendData;

	public MainServer(int ServerPort) throws IOException {
		this.ServerPort = ServerPort;
		HandShakingSocket = new ServerSocket(this.ServerPort);
		cliendData = new HashMap<String, ConcurrentServer>();
	}

	public void StartServer() throws IOException {
		
		// A thread wihich will say when to give the quiz back
		Thread output = new Thread(new Runnable() {
			public void run() {
				sendOutput();
			}
		});
		output.start();
		
		while (true) {

			System.out.println("Waiting for client");
			Socket temp = HandShakingSocket.accept();
			DataInputStream reciever = new DataInputStream(temp.getInputStream());

			// after making connection the first thing received would be
			// nickname
			String nickName = reciever.readUTF();

			cliendData.put(nickName, new ConcurrentServer(temp, nickName, cliendData));
		}
	}
	
	// a function to write the data to the socket
		public void sendOutput() {
			while (true) {

				Scanner ConsoleReader = new Scanner(System.in);
				DataOutputStream sender = null;
				
				System.out.println("Hi! Teacher Enter 1 anytime for the file writing alarm ");
				String ChatBuffer = ConsoleReader.nextLine();
				try {
					for (Map.Entry<String, ConcurrentServer> entry : cliendData.entrySet()) {
						sender = new DataOutputStream(entry.getValue().ServiceSocket.getOutputStream());
						sender.writeUTF(ChatBuffer);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
}
