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

class handleClient extends clientServer implements Runnable{
	private Socket socket;
	private int clientNo;

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
		while(true) {
				int response = in.readInt();
					if (response == 0) {
						hits1 = 0;
						hits2 = 0;
						hits3 = 0;
						Arrays.fill(player1Cards, -1);
						Arrays.fill(player2Cards, -1);
						Arrays.fill(player3Cards, -1);
						Arrays.fill(dealerCards1, -1);
						/*playerCard1 = 0;
						playerCard2 = 0;
						playerCard3 = 0;
						playerCard4 = 0;
						playerCard5 = 0;
						playerCards = 0;
						dealerCard1 = 0;
						dealerCard2 = 0;
						dealerCard3 = 0;
						dealerCards = 0;*/
					}

					if (response == 1) {
						player1Ready = in.readBoolean();
						System.out.println("1 " + player1Ready);
						System.out.println("2 " + player2Ready);
						System.out.println("3 " + player3Ready);
						if (player2Ready == false && player3Ready == false) {
							shuffleDeck(taken);
							assignCards(player1Cards,player2Cards,player3Cards,dealerCards1);
							dealCards(out);
						} else {
							dealCards(out);
						}
					} else if (response == 2) {
						player2Ready = in.readBoolean();
						System.out.println("1 " + player1Ready);
						System.out.println("2 " + player2Ready);
						System.out.println("3 " + player3Ready);
						if (player1Ready == false && player3Ready == false) {
							shuffleDeck(taken);
							assignCards(player1Cards,player2Cards,player3Cards,dealerCards1);
							dealCards(out);
						} else {
							dealCards(out);
						}
					} else if (response == 3) {
						player3Ready = in.readBoolean();
						System.out.println("1 " + player1Ready);
						System.out.println("2 " + player2Ready);
						System.out.println("3 " + player3Ready);
						if (player1Ready == false && player2Ready == false) {
							shuffleDeck(taken);
							assignCards(player1Cards,player2Cards,player3Cards,dealerCards1);
							dealCards(out);
						} else {
							dealCards(out);
						}
					}
					if (response == 4) {
						hitRequest1(out);
					}
					if (response == 5) {
						hitRequest2(out);
					}if (response == 6) {
						hitRequest3(out);
					}
					/*if (response == 4 && player1CardsValue < 21 && hits1 == 0) {
						hits1++;
						player1CardsValue = player1CardsValue + cardValue[player1Cards[2]];
						int aces = 0;
						if (player1Cards[2] == 12 || player1Cards[2] == 25 || player1Cards[2] == 38 || player1Cards[2] == 51) {
							aces++;
						}
						if (player1CardsValue > 21 && aces > 0) {
							player1CardsValue = player1CardsValue -10;
							aces--;
							out.writeInt(player1Cards[2]);
							out.writeInt(player1CardsValue);
						} else {
							player1CardsValue = player1CardsValue;
							out.writeInt(player1Cards[2]);
							out.writeInt(player1CardsValue);
						}
					} else if (response == 4 && player1CardsValue < 21 && hits1 == 1) {
						hits1++;
						player1CardsValue = player1CardsValue + cardValue[player1Cards[3]];
						int aces = 0;
						if (player1Cards[3] == 12 || player1Cards[3] == 25 || player1Cards[3] == 38 || player1Cards[3] == 51) {
							aces++;
						}
						if (player1CardsValue > 21 && aces > 0) {
							player1CardsValue = player1CardsValue -10;
							aces--;
							out.writeInt(player1Cards[3]);
							out.writeInt(player1CardsValue);
						} else {
							player1CardsValue = player1CardsValue;
							out.writeInt(player1Cards[3]);
							out.writeInt(player1CardsValue);
						}
					} else if (response == 4 && player1CardsValue < 21 && hits1 == 2) {
						hits1++;
						player1CardsValue = player1CardsValue + cardValue[player1Cards[4]];
						int aces = 0;
						if (player1Cards[4] == 12 || player1Cards[4] == 25 || player1Cards[4] == 38 || player1Cards[4] == 51) {
							aces++;
						}
						if (player1CardsValue > 21 && aces > 0) {
							player1CardsValue = player1CardsValue -10;
							aces--;
							out.writeInt(player1Cards[4]);
							out.writeInt(player1CardsValue);
						} else {
							player1CardsValue = player1CardsValue;
							out.writeInt(player1Cards[4]);
							out.writeInt(player1CardsValue);
						}
					}*/

					

				/*if (response == 1 && playerCard1 == 0 && playerCard2 == 0 && playerCards == 0 && dealerCard1 == 0 && dealerCard2 == 0 && dealerCards == 0) {
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
				}*/
		}
	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int drawCard() throws IOException {
		int i = random.nextInt(52);
		while(taken[i]) {
			i = random.nextInt(52);
		}
		taken[i] = true;
		return i;
	}
	
	public void shuffleDeck(boolean [] taken) throws IOException {
		for (int i = 0; i < cardNumber.length; i++) {
			cardNumber[i] = drawCard();
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
	
	public void dealCards(DataOutputStream out) throws IOException {
		player1CardsValue = cardValue[player1Cards[0]] + cardValue[player1Cards[1]];
		player2CardsValue = cardValue[player2Cards[0]] + cardValue[player2Cards[1]];
		player3CardsValue = cardValue[player3Cards[0]] + cardValue[player3Cards[1]];
		dealerCardsValue = cardValue[dealerCards1[0]] + cardValue[dealerCards1[1]];
		
		out.writeInt(player1Cards[0]);
		out.writeInt(player1Cards[1]);
		out.writeInt(player1CardsValue);

		out.writeInt(player2Cards[0]);
		out.writeInt(player2Cards[1]);
		out.writeInt(player2CardsValue);
		
		out.writeInt(player3Cards[0]);
		out.writeInt(player3Cards[1]);
		out.writeInt(player3CardsValue);
		
		out.writeInt(dealerCards1[0]);
		out.writeInt(dealerCards1[1]);
		out.writeInt(cardValue[dealerCards1[0]]);
		out.writeInt(dealerCardsValue);
	}
	
	public void hitRequest1(DataOutputStream out) throws IOException {
		if (player1CardsValue < 21 && hits1 == 0) {
			hits1++;
			player1CardsValue = player1CardsValue + cardValue[player1Cards[2]];
			int aces = 0;
			if (player1Cards[2] == 12 || player1Cards[2] == 25 || player1Cards[2] == 38 || player1Cards[2] == 51) {
				aces++;
			}
			if (player1CardsValue > 21 && aces > 0) {
				player1CardsValue = player1CardsValue -10;
				aces--;
				out.writeInt(player1Cards[2]);
				out.writeInt(player1CardsValue);
			} else {
				player1CardsValue = player1CardsValue;
				out.writeInt(player1Cards[2]);
				out.writeInt(player1CardsValue);
			}
		} else if (player1CardsValue < 21 && hits1 == 1) {
			hits1++;
			player1CardsValue = player1CardsValue + cardValue[player1Cards[3]];
			int aces = 0;
			if (player1Cards[3] == 12 || player1Cards[3] == 25 || player1Cards[3] == 38 || player1Cards[3] == 51) {
				aces++;
			}
			if (player1CardsValue > 21 && aces > 0) {
				player1CardsValue = player1CardsValue -10;
				aces--;
				out.writeInt(player1Cards[3]);
				out.writeInt(player1CardsValue);
			} else {
				player1CardsValue = player1CardsValue;
				out.writeInt(player1Cards[3]);
				out.writeInt(player1CardsValue);
			}
		} else if (player1CardsValue < 21 && hits1 == 2) {
			hits1++;
			player1CardsValue = player1CardsValue + cardValue[player1Cards[4]];
			int aces = 0;
			if (player1Cards[4] == 12 || player1Cards[4] == 25 || player1Cards[4] == 38 || player1Cards[4] == 51) {
				aces++;
			}
			if (player1CardsValue > 21 && aces > 0) {
				player1CardsValue = player1CardsValue -10;
				aces--;
				out.writeInt(player1Cards[4]);
				out.writeInt(player1CardsValue);
			} else {
				player1CardsValue = player1CardsValue;
				out.writeInt(player1Cards[4]);
				out.writeInt(player1CardsValue);
			}
		}
	}
	public void hitRequest2(DataOutputStream out) throws IOException {
		if (player2CardsValue < 21 && hits2 == 0) {
			hits2++;
			player2CardsValue = player2CardsValue + cardValue[player2Cards[2]];
			int aces = 0;
			if (player2Cards[2] == 12 || player2Cards[2] == 25 || player2Cards[2] == 38 || player2Cards[2] == 51) {
				aces++;
			}
			if (player2CardsValue > 21 && aces > 0) {
				player2CardsValue = player2CardsValue -10;
				aces--;
				out.writeInt(player2Cards[2]);
				out.writeInt(player2CardsValue);
			} else {
				player2CardsValue = player2CardsValue;
				out.writeInt(player2Cards[2]);
				out.writeInt(player2CardsValue);
			}
		} else if (player2CardsValue < 21 && hits2 == 1) {
			hits2++;
			player2CardsValue = player2CardsValue + cardValue[player2Cards[3]];
			int aces = 0;
			if (player2Cards[3] == 12 || player2Cards[3] == 25 || player2Cards[3] == 38 || player2Cards[3] == 51) {
				aces++;
			}
			if (player2CardsValue > 21 && aces > 0) {
				player2CardsValue = player2CardsValue -10;
				aces--;
				out.writeInt(player2Cards[3]);
				out.writeInt(player2CardsValue);
			} else {
				player2CardsValue = player2CardsValue;
				out.writeInt(player2Cards[3]);
				out.writeInt(player2CardsValue);
			}
		} else if (player2CardsValue < 21 && hits2 == 2) {
			hits2++;
			player2CardsValue = player2CardsValue + cardValue[player2Cards[4]];
			int aces = 0;
			if (player2Cards[4] == 12 || player2Cards[4] == 25 || player2Cards[4] == 38 || player2Cards[4] == 51) {
				aces++;
			}
			if (player2CardsValue > 21 && aces > 0) {
				player2CardsValue = player2CardsValue -10;
				aces--;
				out.writeInt(player2Cards[4]);
				out.writeInt(player2CardsValue);
			} else {
				player2CardsValue = player2CardsValue;
				out.writeInt(player2Cards[4]);
				out.writeInt(player2CardsValue);
			}
		}
	}
	public void hitRequest3(DataOutputStream out) throws IOException {
		if (player3CardsValue < 21 && hits3 == 0) {
			hits3++;
			player3CardsValue = player3CardsValue + cardValue[player3Cards[2]];
			int aces = 0;
			if (player3Cards[2] == 12 || player3Cards[2] == 25 || player3Cards[2] == 38 || player3Cards[2] == 51) {
				aces++;
			}
			if (player3CardsValue > 21 && aces > 0) {
				player3CardsValue = player3CardsValue -10;
				aces--;
				out.writeInt(player3Cards[2]);
				out.writeInt(player3CardsValue);
			} else {
				player3CardsValue = player3CardsValue;
				out.writeInt(player3Cards[2]);
				out.writeInt(player3CardsValue);
			}
		} else if (player3CardsValue < 21 && hits3 == 1) {
			hits3++;
			player3CardsValue = player3CardsValue + cardValue[player3Cards[3]];
			int aces = 0;
			if (player3Cards[3] == 12 || player3Cards[3] == 25 || player3Cards[3] == 38 || player3Cards[3] == 51) {
				aces++;
			}
			if (player3CardsValue > 21 && aces > 0) {
				player3CardsValue = player3CardsValue -10;
				aces--;
				out.writeInt(player3Cards[3]);
				out.writeInt(player3CardsValue);
			} else {
				player3CardsValue = player3CardsValue;
				out.writeInt(player3Cards[3]);
				out.writeInt(player3CardsValue);
			}
		} else if (player3CardsValue < 21 && hits3 == 2) {
			hits3++;
			player3CardsValue = player3CardsValue + cardValue[player3Cards[4]];
			int aces = 0;
			if (player3Cards[4] == 12 || player3Cards[4] == 25 || player3Cards[4] == 38 || player3Cards[4] == 51) {
				aces++;
			}
			if (player3CardsValue > 21 && aces > 0) {
				player3CardsValue = player3CardsValue -10;
				aces--;
				out.writeInt(player3Cards[4]);
				out.writeInt(player3CardsValue);
			} else {
				player3CardsValue = player3CardsValue;
				out.writeInt(player3Cards[4]);
				out.writeInt(player3CardsValue);
			}
		}
	}
}