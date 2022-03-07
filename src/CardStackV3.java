/**
 * Jack Theall
 * 5/3/20
 */
public class CardStackV3 {

	private Card[] backingArray;
	private int topIndex;
	
	public CardStackV3() {
		backingArray = new Card[10];
		topIndex = -1;
	}
	
	public boolean isEmpty() {
		return topIndex == -1;
	}
	
	public void push(Card card) {
		if(topIndex+1 == backingArray.length) {
			doubleArray();
		}
		topIndex++;
		backingArray[topIndex] = card;
	}
	
	public void doubleArray() {
		Card[] newArray = new Card[backingArray.length + 10];
		for(int i=0; i<backingArray.length; i++) {
			newArray[i] = backingArray[i];
		}
		backingArray = newArray;
	}
	
	public Card pop() throws Exception{
		if(isEmpty()) {
			throw new Exception();
		}
		else {
			Card card = backingArray[topIndex];
			topIndex--;
			return card;
		}
	}
}
