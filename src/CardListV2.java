/**
 * Jack Theall
 * 5/3/20 
 *
 */
public class CardListV2 {

	private Card[] cardArray;
	private int size;
	
	public CardListV2(){
		//initializes cardArray to size 10 by default
		this(10);
	}
	
	public CardListV2(int length) {
		cardArray = new Card[length];
		size = 0;
	}
	
	public void append(Card card) {
		//if size is greater than length of cardArray, create new array that is 10 longer
		//than previous cardArray
		if(size > cardArray.length) {
			cardArray = new Card[size + 10];
		}
		cardArray[size] = card;
		size++;
	}
	
	public int size() {
		return size;
	}
	
	public Card get(int i) {
		//if index is greater than size return null, else return cardArray at index
		if(i > size) {
			return null;
		}
		else {
			return cardArray[i];
		}
	}
	//set temp card variable to card at index 2, then set cardArray at index 2 to card at index 1, and then
	//set cardArray at index 1 to temp card variable
	public void swap(int index1, int index2) {
		Card temp = cardArray[index2];
		cardArray[index2] = cardArray[index1];
		cardArray[index1] = temp;
	}
	
	public void clear() {
		size = 0;
	}
}
