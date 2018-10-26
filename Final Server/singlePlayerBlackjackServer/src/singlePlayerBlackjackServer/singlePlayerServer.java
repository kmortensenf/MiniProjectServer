package singlePlayerBlackjackServer;

	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.util.*;

	public class singlePlayerServer {

		public static void main(String [] args) throws IOException {
			new Thread( () -> {
	            int clientNo = 0;
			try {
				ServerSocket serverSocket = new ServerSocket(8087);
				while(true) {
					if (clientNo < 10) {
						Socket socket = serverSocket.accept();
						clientNo++;
						System.out.println("Player " + clientNo + " joined the game\nPlayer " + clientNo + " IP address: " + socket.getInetAddress().getHostAddress());
						new DataOutputStream(socket.getOutputStream()).writeInt(clientNo);
						new Thread(new handleClient(socket)).start();
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
		private int [] cardNumber = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
		private int [] cardValue = {2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11};
		private Socket socket;
		private int [] playerCards = new int[26];
		private int [] dealerCards = new int[26];
		private int playerCardsValue;
		private int dealerCardsValue;
		private Random random = new Random();
		private boolean[] taken = new boolean[52];
		private int hits = 0;
		private int dealerHits = 0;


		public handleClient(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			shuffleDeck(taken);
			assignCards(playerCards,dealerCards);
			while(true) {
				int response = in.readInt();
				
					// If receive 0 from client
					if (response == 0) {
						System.out.println("Reset");
						dealerCardsValue = 0;
						playerCardsValue = 0;
						hits = 0;
						dealerHits = 0;
						boolean[] taken = new boolean[52];
						shuffleDeck(taken);
						assignCards(playerCards,dealerCards);
					}
					// If receive 1 from client
					if (response == 1) {
						System.out.println("Deal cards");
						dealCards(out);
					}
					// If receive 2 from client
					if (response == 2) {
						System.out.println("Hit");
						hitRequest(out);
					}
					// If receive 3 from client
					if (response == 3) {
						System.out.println("Stand");
						standRequest(out);
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
		
		public void assignCards(int[] array1,int[] array2) throws IOException {
			int j = 0;
			for (int i = 0; i < 26; i++) {
				array1[i] = cardNumber[j++];
				array2[i] = cardNumber[j++];
			}
		}
		
		public void dealCards(DataOutputStream out) throws IOException {
			playerCardsValue = cardValue[playerCards[0]] + cardValue[playerCards[1]];
			dealerCardsValue = cardValue[dealerCards[0]] + cardValue[dealerCards[1]];
			
			// If playerCardsValue or dealerCardsValue == 22 there are two aces in the starting hand
			if (playerCardsValue == 22) {
				playerCardsValue = 12;
			}
			if (dealerCardsValue == 22) {
				dealerCardsValue = 12;
			}
			
			out.writeInt(playerCards[0]);
			out.writeInt(playerCards[1]);
			out.writeInt(playerCardsValue);
			
			out.writeInt(dealerCards[0]);
			out.writeInt(dealerCards[1]);
			out.writeInt(cardValue[dealerCards[0]]);
			out.writeInt(dealerCardsValue);
		}
		
		public void hitRequest(DataOutputStream out) throws IOException {
			if (playerCardsValue < 21 && hits == 0) {
				hits++;
				playerCardsValue = playerCardsValue + cardValue[playerCards[2]];
				/**
				 *  If a playerCard has cardNumber 12, 25, 38 or 51 it is an ace and aces++;
				 *  If playerCardsValue > 21 and aces > 0 it will minus 10 from playerCardsValue so ace = 1
				 */
				int aces = 0;
				if (playerCards[2] == 12 || playerCards[2] == 25 || playerCards[2] == 38 || playerCards[2] == 51) {
					aces++;
				}
				if (playerCardsValue > 21 && aces > 0) {
					playerCardsValue = playerCardsValue -10;
					aces--;
					out.writeInt(playerCards[2]);
					out.writeInt(playerCardsValue);
				} else {
					out.writeInt(playerCards[2]);
					out.writeInt(playerCardsValue);
				}
			} else if (playerCardsValue < 21 && hits == 1) {
				hits++;
				playerCardsValue = playerCardsValue + cardValue[playerCards[3]];
				int aces = 0;
				if (playerCards[3] == 12 || playerCards[3] == 25 || playerCards[3] == 38 || playerCards[3] == 51) {
					aces++;
				}
				if (playerCardsValue > 21 && aces > 0) {
					playerCardsValue = playerCardsValue -10;
					aces--;
					out.writeInt(playerCards[3]);
					out.writeInt(playerCardsValue);
				} else {
					out.writeInt(playerCards[3]);
					out.writeInt(playerCardsValue);
				}
			} else if (playerCardsValue < 21 && hits == 2) {
				hits++;
				playerCardsValue = playerCardsValue + cardValue[playerCards[4]];
				int aces = 0;
				if (playerCards[4] == 12 || playerCards[4] == 25 || playerCards[4] == 38 || playerCards[4] == 51) {
					aces++;
				}
				if (playerCardsValue > 21 && aces > 0) {
					playerCardsValue = playerCardsValue -10;
					aces--;
					out.writeInt(playerCards[4]);
					out.writeInt(playerCardsValue);
				} else {
					out.writeInt(playerCards[4]);
					out.writeInt(playerCardsValue);
				}
			} else if ( playerCardsValue < 21 && hits == 3) {
				hits++;
				playerCardsValue = playerCardsValue + cardValue[playerCards[5]];
				int aces = 0;
				if (playerCards[5] == 12 || playerCards[5] == 25 || playerCards[5] == 38 || playerCards[5] == 51) {
					aces++;
				}
				if (playerCardsValue > 21 && aces > 0) {
					playerCardsValue = playerCardsValue -10;
					aces--;
					out.writeInt(playerCards[5]);
					out.writeInt(playerCardsValue);
				} else {
					out.writeInt(playerCards[5]);
					out.writeInt(playerCardsValue);
				}
			}
		}
	
		public void standRequest(DataOutputStream out) throws IOException {
			if (dealerCardsValue < 17 && dealerHits == 0) {
				dealerHits++;
				dealerCardsValue = dealerCardsValue + cardValue[dealerCards[2]];
				/**
				 *  If a dealerCard has cardNumber 12, 25, 38 or 51 it is an ace and aces++;
				 *  If dealerCardsValue > 21 and aces > 0 it will minus 10 from dealerCardsValue so ace = 1
				 */
				int aces = 0;
				if (dealerCards[2] == 12 || dealerCards[2] == 25 || dealerCards[2] == 38 || dealerCards[2] == 51) {
					aces++;
				}
				if (dealerCardsValue > 21 && aces > 0) {
					dealerCardsValue = dealerCardsValue - 10;
					aces--;
					out.writeInt(dealerCards[2]);
					out.writeInt(dealerCardsValue);
				} else {
					out.writeInt(dealerCards[2]);
					out.writeInt(dealerCardsValue);
				}
			} else if (dealerCardsValue < 17 && dealerHits == 1) {
				dealerHits++;
				dealerCardsValue = dealerCardsValue + cardValue[dealerCards[3]];
				int aces = 0;
				if (dealerCards[3] == 12 || dealerCards[3] == 25 || dealerCards[3] == 38 || dealerCards[3] == 51) {
					aces++;
				}
				if (dealerCardsValue > 21 && aces > 0) {
					dealerCardsValue = dealerCardsValue - 10;
					aces--;
					out.writeInt(dealerCards[3]);
					out.writeInt(dealerCardsValue);
				} else {
					out.writeInt(dealerCards[3]);
					out.writeInt(dealerCardsValue);
				}
				} else if (dealerCardsValue < 17 && dealerHits == 2) {
					dealerHits++;
					dealerCardsValue = dealerCardsValue + cardValue[dealerCards[4]];
					int aces = 0;
					if (dealerCards[4] == 12 || dealerCards[4] == 25 || dealerCards[4] == 38 || dealerCards[4] == 51) {
						aces++;
					}
					if (dealerCardsValue > 21 && aces > 0) {
						dealerCardsValue = dealerCardsValue - 10;
						aces--;
						out.writeInt(dealerCards[4]);
						out.writeInt(dealerCardsValue);
					} else {
						out.writeInt(dealerCards[4]);
						out.writeInt(dealerCardsValue);
				}
				} else if (dealerCardsValue < 17 && dealerHits == 3) {
					dealerHits++;
					dealerCardsValue = dealerCardsValue + cardValue[dealerCards[5]];
					int aces = 0;
					if (dealerCards[5] == 12 || dealerCards[5] == 25 || dealerCards[5] == 38 || dealerCards[5] == 51) {
						aces++;
					}
					if (dealerCardsValue > 21 && aces > 0) {
						dealerCardsValue = dealerCardsValue - 10;
						aces--;
						out.writeInt(dealerCards[5]);
						out.writeInt(dealerCardsValue);
					} else {
						out.writeInt(dealerCards[5]);
						out.writeInt(dealerCardsValue);
				}
				}
		}
	}