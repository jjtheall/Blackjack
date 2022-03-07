import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Jack Theall
 * 4/21/20
 */

public class Card {
	//enum to replace suits and ranks
	private static final int JACK = 11;
	private static final int QUEEN = 12;
	private static final int KING = 13;
	private static final int ACE = 14;
	
	private static final int CLUBS = 0;
	private static final int HEARTS = 1;
	private static final int DIAMONDS = 2;
	private static final int SPADES = 3;

	private int rank;
	private int suit;
	private int value;
	private BufferedImage cardImage;
	
	private String[] ranks = {"X", "X", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	private String[] suits = {"C", "H", "D", "S"};
	
	public Card(int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
		try {
			//System.out.println(Arrays.toString(new File("CardImages").listFiles()));
			String fileName = this.toString() + ".png";
			//System.out.println(fileName);
			BufferedImage initialImage = ImageIO.read(new File("CardImages2/" + fileName));
			cardImage = initialImage;
		}
		catch(IOException e){
			System.out.println("Unable to find file.");
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getImage() {
		return cardImage;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public String rankToString() {
		return ranks[rank];
	}
	
	public String suitToString() {
		return suits[suit];
	}
	
	public int getValue() {
		int value = rank;
		if(value > 10) {
			value = 10;
		}
		if(rank == ACE) {
			value = 11;
		}
		return value;
	}
	
	public String toString() {
		return rankToString() + suitToString();
	}
}
