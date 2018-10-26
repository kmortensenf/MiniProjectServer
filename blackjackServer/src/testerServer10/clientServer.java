package testerServer10;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class clientServer {
	public static int [] cardNumber = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
	public static int [] cardValue = {2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11,2,3,4,5,6,7,8,9,10,10,10,10,11};
	
	public static int [] player1Cards = new int[13];
	public static int [] player2Cards = new int[13];
	public static int [] player3Cards = new int[13];
	public static int [] dealerCards1 = new int[13];
	
	public static int player1CardsValue;
	public static int player2CardsValue;
	public static int player3CardsValue;
	public static int dealerCardsValue;
	public static int dealerCard1Value;
	
	public static boolean player1Ready;
	public static boolean player2Ready;
	public static boolean player3Ready;
	
	public static int hits1 = 0;
	public static int hits2 = 0;
	public static int hits3 = 0;
	public static int dealerHits = 0;
	
	public static Random random = new Random();
	public static boolean taken[] = new boolean[52];
	
	public static int clientsConnected = 0;
	
}
