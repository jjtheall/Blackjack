/**
 * Jack Theall
 * 4/28/20
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CardPanel extends JPanel {
	
	private Hand hand;
	private CardListV2 handCardList;
	private int x,y;
	
	//sets hand and handCardList, as well as starting positions for the cards
	public CardPanel(Hand hand) {
		this.setPreferredSize(new Dimension(600,200));
		this.hand = hand;
		this.handCardList = hand.getCardList();
		this.setVisible(false);
		x=50;
		y=40;
	}
	
	public void updateHand(Hand hand) {
		this.hand = hand;
	}
	
	//if the hand is a dealer, draw first card as a "back" of a card
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D)g;
		if(!hand.isDealer()) {
			for(int i=0; i<handCardList.size(); i++) {
				//System.out.println(handCardList.get(i));
				brush.drawImage(handCardList.get(i).getImage(), x, y, 68,110,null);
				x+=70;
			}
			x=50;
		}
		//else, draw first card in hand, update x, and draw next card then repeat.
		else {
			Image cardBack = new ImageIcon("CardImages2/blue_back.png").getImage();
			brush.drawImage(cardBack, x, y, 68, 110, null);
			x+=70;
			for(int i=1; i<handCardList.size(); i++) {
				brush.drawImage(handCardList.get(i).getImage(), x,y,68,110,null);
				x+=70;
			}
			x=50;
		}
	}
}
