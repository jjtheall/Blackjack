
/**
 * Jack Theall

 * 4/21/20
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlackjackGame extends JFrame {

	private static final int JACK = 11;
	private static final int QUEEN = 12;
	private static final int KING = 13;
	private static final int ACE = 14;

	private static final int CLUBS = 0;
	private static final int HEARTS = 1;
	private static final int DIAMONDS = 2;
	private static final int SPADES = 3;

	private static final double STARTING_MONEY = 100.0;

	private static Hand player;
	private Hand dealer;
	private static Deck deck;
	private static int bet;
	private JButton upBet;
	private JButton downBet;
	private JLabel betText;
	private static JLabel moneyText;
	private static double money;
	private JLabel playerHandValue;
	private CardPanel playerPanel;
	private static CardPanel dealerPanel;
	private boolean playerBust;
	private JButton hitButton;
	private JButton standButton;
	private static JButton nextTurnButton;
	private static JLabel dealerValue;
	private static JLabel youWin;
	private static JLabel youLose;
	private static JLabel betReturns;
	private static JButton confirmBet;
	private static JPanel resultsPanel;
	private JButton resetGame;
	private JButton quit;

	public BlackjackGame() throws Exception {
		super("Blackjack");
		this.setSize(600, 450);
		this.setPreferredSize(new Dimension(600, 450));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		money = STARTING_MONEY;
		//create deck and hands
		deck = new Deck();
		player = new Hand();
		dealer = new Hand();
		dealer.setDealer(true);

		player.addCard(deck.deal());
		dealer.addCard(deck.deal());
		player.addCard(deck.deal());
		dealer.addCard(deck.deal());

		playerBust = false;
		//create all panels
		playerPanel = new CardPanel(player);
		dealerPanel = new CardPanel(dealer);
		JPanel controlPanel = new JPanel();
		JPanel betPanel = new JPanel();
		resultsPanel = new JPanel();
		resultsPanel.setVisible(false);
		betPanel.setSize(new Dimension(600, 25));
		controlPanel.setSize(new Dimension(600, 25));
		// on press, add a card to player hand and check if bust, repaint cardPanel
		//create all buttons and action listeners
		hitButton = new JButton("Hit");
		standButton = new JButton("Stand");
		nextTurnButton = new JButton("Next Dealer Turn");
		
		resetGame = new JButton("Next Game");
		resetGame.addActionListener(new ResetGameListener());
		
		quit = new JButton("Quit");
		quit.addActionListener(new QuitActionListener());
		
		hitButton.setEnabled(false);
		standButton.setEnabled(false);
		nextTurnButton.setEnabled(false);

		hitButton.addActionListener(new HitActionListener());
		standButton.addActionListener(new StandActionListener());
		nextTurnButton.addActionListener(new NextTurnListener());
		
		youWin = new JLabel("You Win!");
		youWin.setVisible(false);
		resultsPanel.add(youWin);
		
		youLose = new JLabel("You Lose...");
		youLose.setVisible(false);
		resultsPanel.add(youLose);
		
		betReturns = new JLabel();
		betReturns.setVisible(false);
		resultsPanel.add(betReturns);

		resultsPanel.add(resetGame);
		resultsPanel.add(quit);
		
		upBet = new JButton("+ Bet");
		downBet = new JButton("- Bet");
		confirmBet = new JButton("Confirm Bet");

		betText = new JLabel("Bet: " + bet);
		moneyText = new JLabel("Money: " + STARTING_MONEY + " ||");

		upBet.addActionListener(new IncreaseBetListener());
		downBet.addActionListener(new DecreaseBetListener());
		confirmBet.addActionListener(new ConfirmBetListener());

		playerHandValue = new JLabel("Hand Value: " + player.getValue());
		playerHandValue.setVisible(false);
		dealerValue = new JLabel("Dealer Value: ???");

		betPanel.add(upBet);
		betPanel.add(betText);
		betPanel.add(downBet);
		betPanel.add(confirmBet);
		betPanel.add(moneyText);
		betPanel.add(dealerValue);
		

		controlPanel.add(nextTurnButton);
		controlPanel.add(hitButton);
		controlPanel.add(standButton);
		controlPanel.add(playerHandValue);
		
		this.add(resultsPanel);

		this.add(betPanel);
		this.add(dealerPanel);
		this.add(playerPanel);
		this.add(controlPanel);

		this.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		new BlackjackGame();

	}
	
	//setdealer to false for printing purposes, then add a card and repaint the dealer panel
	//until dealer stands or busts
	public static void dealerTurn(Hand dealer, Deck deck) throws Exception {
		if(dealer.isDealer()) {
			dealer.setDealer(false);

			dealerPanel.updateHand(dealer);
			dealerPanel.repaint();

			dealerValue.setText("Dealer Value: " + dealer.getValue());
			if(dealer.getValue() > 17) {
				nextTurnButton.setEnabled(false);
				findWinner(player, dealer, bet);
			}
		}
		else {
			
			if (dealer.getValue() < 17) {

				Card c = deck.deal();

				dealer.addCard(c);

				dealerPanel.updateHand(dealer);
				dealerPanel.repaint();
				dealerValue.setText("Dealer Value: " + dealer.getValue());


				if (dealer.isBusted()) {
					dealerValue.setText("Dealer Busts!");
					nextTurnButton.setEnabled(false);
					findWinner(player, dealer, bet);
				}
			} 
			else {
				nextTurnButton.setEnabled(false);
				findWinner(player, dealer, bet);
			}
		}
		
	}

	//finds winner and updates youWin text or youLose text, along with the bet
	//text box with winnings, and updates the moneyText
	public static void findWinner(Hand player, Hand dealer, double bet) {
		if (playerWins(player, dealer)) {
			youWin.setVisible(true);
			if (player.hasBlackjack()) {
				double betReturn = bet * 1.5;
				money += betReturn;
				betReturns.setText("Bet returns: +" + (betReturn));
				betReturns.setVisible(true);
				moneyText.setText("Money: " + money);
				resultsPanel.setVisible(true);
			}
			else {
				double betReturn = bet;
				money += betReturn;
				betReturns.setText("Bet returns: +" + (2 * betReturn));
				betReturns.setVisible(true);
				moneyText.setText("Money: " + money);
				resultsPanel.setVisible(true);
			}
		} else if (isPush(player, dealer)) {
			youWin.setText("You push.");
			youWin.setVisible(true);
			double betReturn = 0;
			money += betReturn;
			betReturns.setText("Bet returns: " + betReturn);
			betReturns.setVisible(true);
			moneyText.setText("Money: " + (money));
			resultsPanel.setVisible(true);
		} else {
			youLose.setVisible(true);
			double betReturn = -bet;
			money -= bet;
			betReturns.setText("Bet returns: " + betReturn);
			betReturns.setVisible(true);
			moneyText.setText("Money: " + money);
			resultsPanel.setVisible(true);
		}
	}

	public static boolean playerWins(Hand player, Hand dealer) {
		if (player.isBusted()) {
			return false;
		}
		if (dealer.isBusted()) {
			return true;
		}

		return player.getValue() > dealer.getValue();
	}

	public static boolean isPush(Hand player, Hand dealer) {
		return player.getValue() == dealer.getValue();
	}

	private class HitActionListener implements ActionListener {
		//if player hasn't busted, deal a card and add it to the player hand.
		//if player has bust, update player value text to "you bust"
		public void actionPerformed(ActionEvent e) {
			Card c;
			try {
				if (!playerBust) {
					c = deck.deal();
					player.addCard(c);
					playerPanel.updateHand(player);
					playerPanel.repaint();
					playerHandValue.setText("Hand Value: " + player.getValue());
					if (player.getValue() > 21) {
						playerBust = true;
						playerHandValue.setText("You bust!");
						playerHandValue.setBackground(Color.red);
						nextTurnButton.setEnabled(true);
						hitButton.setEnabled(false);
						standButton.setEnabled(false);
					}
				}
			} catch (Exception e1) {
				System.out.println("Empty deck");
			}

		}

	}

	private class StandActionListener implements ActionListener {

		@Override
		//on stand, dealer plays and hit and stand buttons are disabled.
		//nextTurn button is enabled
		public void actionPerformed(ActionEvent e) {
			try {
				nextTurnButton.setEnabled(true);
				hitButton.setEnabled(false);
				standButton.setEnabled(false);

			} catch (Exception e1) {
				System.out.println("empty deck");
			}
		}

	}

	private class IncreaseBetListener implements ActionListener {

		@Override
		//increases bet by one and updates betText
		public void actionPerformed(ActionEvent e) {
			if (bet < money) {
				bet += 1;
				betText.setText("Bet: " + bet);
			}
		}

	}

	private class DecreaseBetListener implements ActionListener {

		@Override
		//decreases bet by one and updates betText
		public void actionPerformed(ActionEvent e) {
			if (bet > 0) {
				bet -= 1;
				betText.setText("Bet: " + bet);
			}
		}

	}

	private class NextTurnListener implements ActionListener {

		@Override
		//plays one dealer turn
		public void actionPerformed(ActionEvent e) {
			try {
				dealerTurn(dealer, deck);
			} catch (Exception e1) {
				System.out.println("empty deck");
			}
		}

	}
	
	private class ConfirmBetListener implements ActionListener{

		@Override
		//as long as current bet is less than amount of money, enable gameplay buttons and disable
		//betting buttons. Also set up playerHandValue and dealerValue labels, as well as the money label.
		//checks if player has blackjack, if so, disable all buttons except next turn
		public void actionPerformed(ActionEvent e) {
			if(bet <= money) {
				upBet.setEnabled(false);
				downBet.setEnabled(false);
				hitButton.setEnabled(true);
				standButton.setEnabled(true);
				confirmBet.setEnabled(false);
				dealerPanel.setVisible(true);
				playerPanel.setVisible(true);
				playerHandValue.setVisible(true);
				moneyText.setText("Money: " + (money - bet) + " ||");
				if(player.hasBlackjack()) {
					hitButton.setEnabled(false);
					standButton.setEnabled(false);
					nextTurnButton.setEnabled(true);
					playerHandValue.setText("Blackjack!");
				}
			}
		}
		
	}
	
	private class ResetGameListener implements ActionListener{

		@Override
		//resets all variables to what they were at the start, except for the money.
		//a new deck is created
		public void actionPerformed(ActionEvent e) {
			resultsPanel.setVisible(false);
			playerPanel.setVisible(false);
			dealerPanel.setVisible(false);
			dealer.clear();
			dealer.setDealer(true);
			player.clear();
			playerBust = false;
			deck = new Deck();
			try {
				player.addCard(deck.deal());
				dealer.addCard(deck.deal());
				player.addCard(deck.deal());
				dealer.addCard(deck.deal());
			} catch(Exception e1) {
				System.out.println("empty deck");
			}
			
			playerPanel.updateHand(player);
			dealerPanel.updateHand(dealer);
			playerPanel.repaint();
			dealerPanel.repaint();
			playerHandValue.setText("Hand Value: " + player.getValue());
			playerHandValue.setVisible(false);
			dealerValue.setText("Dealer Value: ???");
			youWin.setVisible(false);
			youWin.setText("You Win!");
			youLose.setVisible(false);
			upBet.setEnabled(true);
			downBet.setEnabled(true);
			confirmBet.setEnabled(true);
			hitButton.setEnabled(false);
			standButton.setEnabled(false);
			nextTurnButton.setEnabled(false);
			bet = 0;
			betText.setText("Bet: " + bet);
		}
		
	}
	
	private class QuitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
}
