package cardGameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class cardGameServer {
	
	public static void main(String [] args) throws IOException {
			new Thread( () -> {
				int clientNo = 0;
			try {
				ServerSocket serverSocket = new ServerSocket(8085);
				while(true) {
					Socket socket = serverSocket.accept();
					
					clientNo++;
					System.out.println(clientNo);
				
			new Thread(new handleClient(socket)).start();
				}
			} catch(IOException ex) {
				System.out.println(ex);
			}
			}).start();
	}
}

class handleClient implements Runnable {
	int [] cardArray = {1,1,1,1,2,2,2,2,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,11,11,11,11};
	private Socket socket;
	Random random = new Random();
	
	public handleClient(Socket socket) {	
		this.socket = socket;
	}
	public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			for (int i = 0; i < cardArray.length; i++) {
				int shuffle = random.nextInt(cardArray.length-i) + i;
				int tempNo = cardArray[shuffle];
				cardArray[shuffle] = cardArray[i];
				cardArray[i] = tempNo;
			}
			while(true) {
				int playerCards = 0;
				int requestCards = in.readInt();
				if (requestCards == 1) {
					for (int i = 1; i < 2; i++) {
					playerCards = cardArray[i];
					cardArray.splice(start, i);
					i--;
					
					}
			}
		} 
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
}
}