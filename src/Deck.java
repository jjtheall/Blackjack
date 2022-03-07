import java.util.ArrayList;

/**
 * Jack Theall
 * 4/21/20
 */

public class Deck {
	
	private static final int JACK = 11;
	private static final int QUEEN = 12;
	private static final int KING = 13;
	private static final int ACE = 14;
	
	private static final int CLUBS = 0;
	private static final int HEARTS = 1;
	private static final int DIAMONDS = 2;
	private static final int SPADES = 3;
	
	private CardStackV3 deck;
	
	public Deck() {
		
		deck = new CardStackV3();
		CardListV2 tempList = new CardListV2(52);
		//creates ordered list of cards
		for(int rank = 2; rank <= ACE; rank++) {
			for(int suit = CLUBS; suit <= SPADES; suit++) {
				Card card = new Card(rank, suit);
				tempList.append(card);
			}
		}
		//randomly swaps cards 
		for(int i=0; i<tempList.size(); i++) {
			int randomIndex = (int)(Math.random() * 52);
			
			tempList.swap(i, randomIndex);
		}
		//pushes all cards in the list to the deck stack
		for(int i=0; i<tempList.size(); i++) {
			Card c = tempList.get(i);
			deck.push(c);
		}
	}
	
	public CardStackV3 getDeck() {
		return deck;
	}
	
	public Card deal() throws Exception{
		return deck.pop();
	}
	
	public void printDeck() throws Exception{
		while(!deck.isEmpty()) {
			System.out.println(deck.pop());
		}
	}
}
