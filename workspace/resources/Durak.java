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
    //Is player 1's attacking turn
	boolean isAttacking=true;
    boolean loser;

    
	//if any player hand is zero, game over
    private ArrayList<Card> hand1;
    private ArrayList<Card> hand2;


	public java.util.List<Card> getHand1() {
        return java.util.Collections.unmodifiableList(hand1);
    }

    public java.util.List<Card> getHand2() {
        return java.util.Collections.unmodifiableList(hand2);
    }


	private Card trumpCard;


    public List<Stack<Card>> getColumns() {
        return Collections.unmodifiableList(columns);
    }


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
            for (int value = 6; value <=14; value++) {
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
		return deck.peek();
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

	public void gameOver(){
		if(hand1.size()==0){
            isGameOver=true;
            loser=false;
        }else if (hand2.size()==0){
			isGameOver=true;
            loser=true;
			//something something gui game over
		}
		
	}


	private void dealInitialHands(int handSize) {
        for (int i = 0; i < handSize; i++) {
            if (!deck.isEmpty()) hand1.add(deck.poll());
            if (!deck.isEmpty()) hand2.add(deck.poll());
        }
    }


	public Card peekTopDeckCard() {
        return deck.peek();
    }



	// public java.util.List<Card> drawToHand(java.util.List<Card> hand, int count) {
    //     java.util.List<Card> drawn = new java.util.ArrayList<>();
    //     for (int i = 0; i < count && !deck.isEmpty(); i++) {
    //         Card c = deck.poll();
    //         if (c != null) {
    //             hand.add(c);
    //             drawn.add(c);
    //         }
    //     }
    //     return drawn;
    // }


	public void replenishHandsTo(int targetSize) {
        while (hand1.size() < targetSize && !deck.isEmpty()) {
            hand1.add(deck.poll());
        }
        while (hand2.size() < targetSize && !deck.isEmpty()) {
            hand2.add(deck.poll());
        }
        // If deck becomes empty, trumpCard stays whatever it was (you already pick it)
    }



    public boolean canDefend(Card attack, Card defense) {
        // if (attack == null || defense == null) return false;

        Card.Suit trump = getTrumpSuit();

        // assume Card has int field 'value' and Card.Suit field 'suit'
        int aRank = attack.value;    // change to attack.getValue() if you have a getter
        int dRank = defense.value;   // change to defense.getValue() if you have a getter

        // same suit, higher rank
        if (defense.suit == attack.suit && dRank > aRank) return true;

        // defense is trump, attack is not
        if (defense.suit == trump && attack.suit != trump) return true;

        return false;
    }


    public boolean canAttackWith(Card card){
        if(card == null) return false;
        // In Durak, any card can be used to attack
        return true;
    }
    

    public void endTurn(){
        isAttacking=!isAttacking;
    }

    public void doMove(Card c1, Card c2){
        // Determine which hand c1 came from
        boolean c1InHand1 = hand1.contains(c1);
        boolean c1InHand2 = hand2.contains(c1);
        System.out.println("c1InHand1: " + c1InHand1 + ", c1InHand2: " + c1InHand2);

        if (isAttacking && c1InHand1) {
        // Attack: place c1 on the table
        Stack<Card> column = new Stack<>();
        c1.show();
        column.push(c1);
        columns.add(column);
        hand1.remove(c1);
        System.out.println("Player 1 attacked with " + c1);
        return;
        }
        // If it's player 1's defending turn (not attacking)
        else if (!isAttacking && c1InHand1 && c2 != null) {
            // Defense: c1 defends against c2 (the attack card)
            if (canDefend(c2, c1)) {
                // Find the column with c2 and add c1 as defense
                for (Stack<Card> column : columns) {
                    if (column.peek().equals(c2)) {
                        column.push(c1);
                        hand1.remove(c1);
                        break;
                    }
                }
            }
        }

        // If it's player 2's turn
        else if (isAttacking && c1InHand2) {
            // Player 2 attacking: place c1 on table
            Stack<Card> column = new Stack<>();
            column.push(c1);
            columns.add(column);
            hand2.remove(c1);
            return;
        }
        else if (!isAttacking && c1InHand2 && c2 != null) {
            // Player 2 defending
            if (canDefend(c2, c1)) {
                for (Stack<Card> column : columns) {
                    if (column.peek().equals(c2)) {
                        column.push(c1);
                        hand2.remove(c1);
                        break;
                    }
                }
            }
        }
            //determine the type of move
            //find where the cards came from

            // c1 is in hand 1 and it's p1's turn then c2 doesn't matter and we do the move

            //move cards where they are supposed to be.
        }

    //Attack shit

    //how do i ame it so that I could move the cards to the center layerpanes.
    //Attaker can put up to 4 of the same value cards. we are making an easier version of the game


	

}