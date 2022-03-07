/**
 * Jack Theall
 * 4/21/20
 */

import java.util.ArrayList;

public class Hand {
	
	private static final int JACK = 11;
	private static final int QUEEN = 12;
	private static final int KING = 13;
	private static final int ACE = 14;
	
	private static final int CLUBS = 0;
	private static final int HEARTS = 1;
	private static final int DIAMONDS = 2;
	private static final int SPADES = 3;
	
	private CardListV2 cards;
	private boolean dealer;
	
	public Hand() {
		cards = new CardListV2(10);
	}
	
	public void addCard(Card card) {
		cards.append(card);
	}
	
	public void setDealer(boolean b) {
		dealer = b;
	}
	
	public boolean isDealer() {
		return dealer;
	}
	
	public CardListV2 getCardList() {
		return cards;
	}
	
	//sums all cards in the hand
	public int getValue() {
		int sum = 0;
		int aces = 0;
		for(int i=0; i<cards.size(); i++) {
			Card c = cards.get(i);
			sum += c.getValue();
			if (c.getRank() == ACE) {
				aces++;
			}
		}
		//adjust sum for aces if hand value is greater than 21
		while(sum > 21 && aces > 0) {
			sum -= 10;
			aces--;
		}
		
		return sum;
	}
	
	public boolean isBusted() {
		return getValue()>21;
	}
	
	public void clear() {
		cards.clear();
	}
	
	public boolean hasBlackjack() {
		return getValue() == 21 && cards.size() == 2;
	}
	
	//if hand is a dealer hand, add first card as an 'X'. Else, add cards normally
	public String toString() {
		String result = "";
		if(!dealer) {
			for(int i=0; i<cards.size(); i++) {
				Card c= cards.get(i);
				result += c + " ";
			}
			result += "(" + getValue() + ")";
			return result;
		}
		else {
			for(int i=0; i<cards.size(); i++) {
				Card c = cards.get(i);
				if(i==0) {
					result += "X ";
				}
				else {
					result += c + " ";
				}
			}
			return result;
		}
	}
	
	
}
