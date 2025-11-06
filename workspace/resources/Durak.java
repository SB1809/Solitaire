package resources;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Durak {
	ArrayList<Stack <Card>> columns;
	Queue<Card> deck;
	boolean isGameOver=false;
	boolean isTurn=true;

	//if any player hand is zero, game over
    private ArrayList<Card> hand1;
    private ArrayList<Card> hand2;


	private Card trumpCard;



	//Constructor
	public Durak() {
        columns = new ArrayList<>();
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        deck = new LinkedList<>();

        Deck();
        shuffleDeck();
        pickTrump();          // sets trumpCard (bottom card of deck)
        dealInitialHands(6);
		
    }


	private void Deck() {
        ArrayList<Card> temp = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int value = 1; value <= 13; value++) {
                // Create card images will be loaded in Card constructor
				Card c = new Card(value, suit);
				c.hide();
                temp.add(c);
            }
        }
        // Place into the queue 
        deck = new LinkedList<>(temp);
    }

	private void shuffleDeck() {
		ArrayList<Card> temp = new ArrayList<>(deck);
		Collections.shuffle(temp);
		deck = new LinkedList<>(temp);
	}

	public Card getTopDeckCard(){
		return deck.poll();
	}

	public Card drawCard() {
        return deck.poll();
    }

	private void pickTrump() {
		if (deck.isEmpty()) {
			trumpCard = null;
			return;
		}
		// Convert queue to list to access the last element
		ArrayList<Card> list = new ArrayList<>(deck);
		trumpCard = list.get(list.size() - 1); // bottom card is trump (visible)
	}

	private void dealInitialHands(int handSize) {
        for (int i = 0; i < handSize; i++) {
            if (!deck.isEmpty()) hand1.add(deck.poll());
            if (!deck.isEmpty()) hand2.add(deck.poll());
        }
    }

	public Card getTrumpCard() {
        return trumpCard;
    }



	public Card.Suit getTrumpSuit() {
    	if (trumpCard == null) {
        	return null;
    	} else {
        	return trumpCard.suit;
    	}
	}

	public java.util.List<Card> getDeckAsList() {
        return new ArrayList<>(deck);
    }

	//the part of your program that's in charge of game rules goes here.

	public void update(){
		if(hand1.size()==0||hand2.size()==0){
			isGameOver=true;
			//something something gui game over
		}
		
	}

	

}