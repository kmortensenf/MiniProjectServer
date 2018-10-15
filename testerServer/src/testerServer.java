	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class testerServer {
	
	public static void main(String [] args) throws IOException {
			new Thread( () -> {
				int clientNo = 0;
			try {
				ServerSocket serverSocket = new ServerSocket(8086);
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
	int [] cardArray = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
	private Socket socket;
	Random random = new Random();
	
	
	public handleClient(Socket socket) {	
		this.socket = socket;
	}
	public void run() {
		
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			for (int i = 1; i < cardArray.length; i++) {
				int shuffle = random.nextInt(cardArray.length-i) + i;
				int tempNo = cardArray[shuffle];
				cardArray[shuffle] = cardArray[i];
				cardArray[i] = tempNo;
			}
			System.out.println("Run once");
			while(true) {
				if (in.readInt() == 1) {
					System.out.println("Dealing cards");
					out.writeInt(cardArray[0]);
					out.writeInt(cardArray[1]);
					out.writeInt(cardArray[2]);
					out.writeInt(cardArray[3]);
					
				}
				if (in.readInt() == 2) {
					System.out.println("Hit requested");
					out.writeInt(cardArray[4]);
				}
				if (in.readInt() == 2) {
					System.out.println("Hit requested");
					out.writeInt(cardArray[5]);
				}
				if (in.readInt() == 3) {
					System.out.println("Hit requested 3");
					out.writeInt(cardArray[6]);
				}
			}	
		 
		}catch (IOException ex) {
			System.out.println(ex);
		}
	}
}
