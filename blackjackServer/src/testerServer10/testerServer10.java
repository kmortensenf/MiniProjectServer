package testerServer10;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class testerServer10 {

	public static void main(String [] args) throws IOException {
		new Thread( () -> {
            int clientNo = 0;
		try {
			ServerSocket serverSocket = new ServerSocket(8087);
			while(true) {
				if (clientNo < 3) {
					Socket socket = serverSocket.accept();
					clientNo++;
					System.out.println("Player " + clientNo + " joined the game\nPlayer " + clientNo + " IP address: " + socket.getInetAddress().getHostAddress());
					new DataOutputStream(socket.getOutputStream()).writeInt(clientNo);
					new Thread(new handleClient(socket,clientNo)).start();
				} else {
					serverSocket.close();
				}
			}
		} catch(IOException ex) {
			System.out.println(ex);
		}
		}).start();
	}
}

class handleClient implements Runnable {
	int [] cardNumber = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
	int [] cardValue = {2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11};
	private Socket socket;
	private int clientNo;
	Random random = new Random();
	int [] player1Cards = new int[13];
	int [] player2Cards = new int[13];
	int [] player3Cards = new int[13];
	int [] dealerCards1 = new int[13];
	int player1CardsValue;
	int player2CardsValue;
	int player3CardsValue;
	int dealerCardsValue;
	int playerCard1;
	int playerCard2;
	int playerCard3;
	int playerCard4;
	int playerCard5;
	int playerCards;
	int dealerCard1;
	int dealerCard2;
	int dealerCard3;
	int dealerCard4;
	int dealerCard5;
	int dealerCards;
	int dealerCard1Value;
	int x = 0;
	int hits = 0;
	boolean taken[] = new boolean[52];

	public handleClient(Socket socket, int clientNo) {
	this.socket = socket;
	this.clientNo = clientNo;
	}
	
	public void run() {
	try {
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Run once");
		System.out.println(clientNo);
		out.writeInt(clientNo);
		
		while(true) {
				int response = in.readInt();
				if (response == 0) {
					x = 0;
					hits = 0;
					playerCard1 = 0;
					playerCard2 = 0;
					playerCard3 = 0;
					playerCard4 = 0;
					playerCard5 = 0;
					playerCards = 0;
					dealerCard1 = 0;
					dealerCard2 = 0;
					dealerCard3 = 0;
					dealerCards = 0;
					System.out.println("Reset");
					boolean taken[] = new boolean[52];
					shuffleDeck(taken);
				}
				if (response == 1 && playerCard1 == 0 && playerCard2 == 0 && playerCards == 0 && dealerCard1 == 0 && dealerCard2 == 0 && dealerCards == 0) {
					playerCard1 = cardNumber[x++];
					playerCard2 = cardNumber[x++];
					playerCards = cardValue[playerCard1] + cardValue[playerCard2];
					dealerCard1 = cardNumber[x++];
					dealerCard1Value = cardValue[dealerCard1];
					dealerCard2 = cardNumber[x++];
					dealerCards = cardValue[dealerCard1] + cardValue[dealerCard2];
				
					if (playerCards == 22) {
					playerCards = playerCards - 10;
					out.writeInt(playerCard1);
					out.writeInt(playerCard2);
					out.writeInt(playerCards);
					} else if (playerCards == 21) {
					out.writeInt(playerCard1);
					out.writeInt(playerCard2);
					out.writeInt(playerCards);
					} else {
					playerCards = playerCards;
					out.writeInt(playerCard1);
					out.writeInt(playerCard2);
					out.writeInt(playerCards);
				
					} if (dealerCards == 22) {
					dealerCards = dealerCards - 10;
					out.writeInt(dealerCard1);
					out.writeInt(dealerCard1Value);
					out.writeInt(dealerCard2);
					out.writeInt(dealerCards);
					} else if (playerCards == 21) {
					out.writeInt(dealerCard1);
					out.writeInt(dealerCard1Value);
					out.writeInt(dealerCard2);
					out.writeInt(dealerCards);
					} else {
					dealerCards = dealerCards;
					out.writeInt(dealerCard1);
					out.writeInt(dealerCard1Value);
					out.writeInt(dealerCard2);
					out.writeInt(dealerCards);
					}
				}
				
				if (response == 2 && playerCards < 21 && hits == 0) {
					hits++;
					playerCard3 = cardNumber[x++];
					playerCards = playerCards + cardValue[playerCard3];
					int aces = 0;
					if (playerCard3 == 12 || playerCard3 == 25 || playerCard3 == 38 || playerCard3 == 51) {
					aces++;
					}
					if (playerCards > 21 && aces > 0) {
					playerCards = playerCards -10;
					aces--;
					out.writeInt(playerCard3);
					out.writeInt(playerCards);
					} else {
					playerCards = playerCards;
					out.writeInt(playerCard3);
					out.writeInt(playerCards);
					}
					System.out.println("Hit requested 1");
				}
			
				else if (response == 2 && playerCards < 21 && hits == 1) {
					hits++;
					playerCard4 = cardNumber[x++];
					playerCards = playerCards + cardValue[playerCard4];
					int aces = 0;
					if (playerCard4 == 12 || playerCard4 == 25 || playerCard4 == 38 || playerCard4 == 51) {
						aces++;
					}
					if (playerCards > 21 && aces > 0) {
						playerCards = playerCards -10;
						aces--;
						out.writeInt(playerCard4);
						out.writeInt(playerCards);
					} else {
						playerCards = playerCards;
						out.writeInt(playerCard4);
						out.writeInt(playerCards);
					}
					System.out.println("Hit requested 2");
				}
				
				else  if (response == 2 && playerCards < 21 && hits == 2) {
					hits++;
					playerCard5 = cardNumber[x++];
					playerCards = playerCards + cardValue[playerCard5];
					int aces = 0;
					if (playerCard5 == 12 || playerCard5 == 25 || playerCard5 == 38 || playerCard5 == 51) {
						aces++;
					}
					if (playerCards > 21 && aces > 0) {
						playerCards = playerCards -10;
						aces--;
						out.writeInt(playerCard5);
						out.writeInt(playerCards);
					} else {
						playerCards = playerCards;
						out.writeInt(playerCard5);
						out.writeInt(playerCards);
					}
					System.out.println("Hit requested 3");
				}
			
				if (response == 3 && dealerCards < 17) {
					if (dealerCard3 == 0) {
					dealerCard3 = cardNumber[x++];
					System.out.println("total value dealer before adding: " + dealerCards);
					dealerCards = dealerCards + cardValue[dealerCard3];
					System.out.println("after 3rd dealer card: " + dealerCards);
					int aces = 0;
					if (dealerCard3 == 12 || dealerCard3 == 25 || dealerCard3 == 38 || dealerCard3 == 51) {
						aces++;
					}
					if (dealerCards > 21 && aces > 0) {
						dealerCards = dealerCards - 10;
						aces--;
						out.writeInt(dealerCard3);
						out.writeInt(dealerCards);
					} else {
						dealerCards = dealerCards;
						out.writeInt(dealerCard3);
						out.writeInt(dealerCards);
					}
				} else if (dealerCard3 != 0 && dealerCard4 == 0) {
					dealerCard4 = cardNumber[x++];
					System.out.println("total value dealer before adding: " + dealerCards);
					dealerCards = dealerCards + cardValue[dealerCard4];
					System.out.println("after 4th dealer card: " + dealerCards);
					int aces = 0;
					if (dealerCard4 == 12 || dealerCard4 == 25 || dealerCard4 == 38 || dealerCard4 == 51) {
						aces++;
					}
					if (dealerCards > 21 && aces > 0) {
						dealerCards = dealerCards - 10;
						aces--;
						out.writeInt(dealerCard4);
						out.writeInt(dealerCards);
					} else {
						dealerCards = dealerCards;
						out.writeInt(dealerCard4);
						out.writeInt(dealerCards);
					}
				} else {
					dealerCard5 = cardNumber[x++];
					System.out.println("total value dealer before adding: " + dealerCards);
					dealerCards = dealerCards + cardValue[dealerCard5];
					System.out.println("after 5th dealer card: " + dealerCards);
					int aces = 0;
					if (dealerCard5 == 12 || dealerCard5 == 25 || dealerCard5 == 38 || dealerCard5 == 51) {
						aces++;
					}
					if (dealerCards > 21 && aces > 0) {
						dealerCards = dealerCards - 10;
						aces--;
						out.writeInt(dealerCard5);
						out.writeInt(dealerCards);
					} else {
						dealerCards = dealerCards;
						out.writeInt(dealerCard5);
						out.writeInt(dealerCards);
					}
				}
				}
		}
	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int drawCard(boolean [] taken) throws IOException {
		int i = random.nextInt(52);
		while(taken[i]) {
			i = random.nextInt(52);
		}
		taken[i] = true;
		return i;
	}
	
	public void shuffleDeck(boolean [] taken) throws IOException {
		for (int i = 0; i < cardNumber.length; i++) {
			cardNumber[i] = drawCard(taken);
		}
		System.out.println(Arrays.toString(cardNumber));
	}
	
	public void assignCards(int[] array1,int[] array2,int[] array3,int[] array4) throws IOException {
		int j = 0;
		for (int i = 0; i < 13; i++) {
			array1[i] = cardNumber[j++];
			array2[i] = cardNumber[j++];
			array3[i] = cardNumber[j++];
			array4[i] = cardNumber[j++];
		}
		System.out.println(Arrays.toString(player1Cards));
		System.out.println(Arrays.toString(player2Cards));
		System.out.println(Arrays.toString(player3Cards));
		System.out.println(Arrays.toString(dealerCards1));
	}
}