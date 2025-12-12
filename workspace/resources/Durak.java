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
	ArrayList<Card> graveyard;
	public int isGameOver = 0;
    //Is player 1 attacking? When true, P1 attacks and P2 defends. When false, P2 attacks and P1 defends.
	boolean isPlayer1Attacking = true;
    boolean turn;
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

	public java.util.List<Card> getGraveyard() {
        return java.util.Collections.unmodifiableList(graveyard);
    }

	public boolean isPlayer1Attacking() {
		return isPlayer1Attacking;
	}

	public int getAttackingPlayer() {
		return isPlayer1Attacking ? 1 : 2;
	}

	public int getDefendingPlayer() {
		return isPlayer1Attacking ? 2 : 1;
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
        graveyard = new ArrayList<>();
        deck = new LinkedList<>();

        Deck();
        shuffleDeck();
        pickTrump();          // sets trumpCard (bottom card of deck)
        dealInitialHands(6);
		shortCutEnd();
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

    public void shortCutEnd(){
        for(int i=0; i<20; i++){
            deck.poll();
        }
    }

	private void pickTrump() {
		if (deck.isEmpty()) {
			trumpCard = null;
			return;
		}
		
		ArrayList<Card> list = new ArrayList<>(deck);
		trumpCard = list.get(list.size() - 1); // bottom card is trump 
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
		if(hand1.size()==0&&deck.isEmpty()){
            isGameOver=1;
            
        }else if (hand2.size()==0&&deck.isEmpty()){
			isGameOver=2;
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
        int aRank = attack.value;    
        int dRank = defense.value;   

        // same suit, higher rank
        if (defense.suit == attack.suit && dRank > aRank) return true;

        // defense is trump, attack is not
        if (defense.suit == trump && attack.suit != trump) return true;

        return false;
    }


    public boolean isAttacking(Card card){
        if(card == null) return false;
        
        // In Durak, any card can be used to attack
        return true;
    }

    public boolean canEndTurn(){
            for (Stack<Card> column : columns) {
                if(column.size() != 2){
                    return false;
                }
            }
            return true;
    }

    
    public void endTurn(){
        if(!canEndTurn()){
            return;
        }

        // Move all cards from table to graveyard
        for (Stack<Card> column : columns) {
            while (!column.isEmpty()) {
                graveyard.add(column.pop());
            }
        }

      

        // Clear the table
        columns.clear();
        // Switch turns
        isPlayer1Attacking = !isPlayer1Attacking;
        // Replenish hands
        replenishHandsTo(6);
        // Check for game over
        gameOver();
    }

    public void defenderTakesCards() {
        // Defender takes all cards from the table into their hand
        ArrayList<Card> defendingHand = isPlayer1Attacking ? hand2 : hand1;
        
        for (Stack<Card> column : columns) {
            while (!column.isEmpty()) {
                defendingHand.add(column.pop());
            }
        }
        columns.clear();
        // Switch turns
        isPlayer1Attacking = !isPlayer1Attacking;
        // Replenish hands
        replenishHandsTo(6);
        // Check for game over
        gameOver();
    }

    public boolean isValidAttackCard(Card card) {
        // First card can be anything, subsequent cards must match values on table
        if (columns.isEmpty()) {
            return true;  // First attack card
        }
        
        // Get all values currently on the table
        ArrayList<Integer> tableValues = new ArrayList<>();
        for (Stack<Card> column : columns) {
            if (!column.isEmpty()) {
                tableValues.add(column.peek().value);  // Get top card value
            }
        }
        
        // Attack card must match one of the values on the table
        return tableValues.contains(card.value);
    }

    // public void endTurn(){
    //     isAttacking =! isAttacking;
    // }


    public void doMove(Card c1, Card c2) {
        // Determine which hand c1 came from
        boolean c1InHand1 = hand1.contains(c1);
        boolean c1InHand2 = hand2.contains(c1);

        // Determine if c2 is an attack card on the table
        boolean isCard2OnTable = false;
        if (c2 != null) {
            for (Stack<Card> column : columns) {
                if (column.contains(c2)) {
                    isCard2OnTable = true;
                    break;
                }
            }
            System.out.println("c1InHand1: " + c1InHand1 + ", c1InHand2: " + c1InHand2);
        }
        

        // if (isAttacking(c1) && c1InHand1) {
        // // Attack: place c1 on the table
        // Stack<Card> column = new Stack<>();
        // c1.show();
        // column.push(c1);
        // columns.add(column);
        // hand1.remove(c1);
        // System.out.println("Player 1 attacked with " + c1);
        // return;
        // }
        // // If it's player 1's defending turn (not attacking)
        // else if (!isAttacking(c2) && c1InHand1 && c2 != null) {
        //     // Defense: c1 defends against c2 (the attack card)
        //     if (canDefend(c2, c1)) {
        //         // Find the column with c2 and add c1 as defense
        //         for (Stack<Card> column : columns) {
        //             if (column.peek().equals(c2)) {
        //                 column.push(c1);
        //                 hand1.remove(c1);
        //                 break;
        //             }
        //         }
        //     }
        // }

        // If Player 1 is attacking
        if (isPlayer1Attacking && c1InHand1) {
            // Validate that attack card is valid
            if (!isValidAttackCard(c1)) {
                System.out.println("Invalid attack! Card value must match cards on table.");
                return;
            }
            // Player 1 attacks with c1 (c2 will be null for new attacks)
            Stack<Card> column = new Stack<>();
            column.push(c1);
            columns.add(column);
            hand1.remove(c1);
            System.out.println("Player 1 attacked with " + c1);
            return;
        }
        
        // If Player 1 is attacking, Player 2 must defend
        if (isPlayer1Attacking && c1InHand2 && c2 != null && isCard2OnTable) {
            // Player 2 defends against c2 (the attack card) with c1
            if (canDefend(c2, c1)) {
                for (Stack<Card> column : columns) {
                    if (column.peek().equals(c2)) {
                        column.push(c1);
                        hand2.remove(c1);
                        System.out.println("Player 2 defended with " + c1);
                        return;
                    }
                }
            } else {
                System.out.println("Invalid defense!");
            }
            return;
        }
        
        // If Player 2 is attacking
        if (!isPlayer1Attacking && c1InHand2) {
            // Validate that attack card is valid
            if (!isValidAttackCard(c1)) {
                System.out.println("Invalid attack! Card value must match cards on table.");
                return;
            }
            // Player 2 attacks with c1 (c2 will be null for new attacks)
            Stack<Card> column = new Stack<>();
            column.push(c1);
            columns.add(column);
            hand2.remove(c1);
            System.out.println("Player 2 attacked with " + c1);
            return;
        }
        
        // If Player 2 is attacking, Player 1 must defend
        if (!isPlayer1Attacking && c1InHand1 && c2 != null && isCard2OnTable) {
            // Player 1 defends against c2 (the attack card) with c1
            if (canDefend(c2, c1)) {
                for (Stack<Card> column : columns) {
                    if (column.peek().equals(c2)) {
                        column.push(c1);
                        hand1.remove(c1);
                        System.out.println("Player 1 defended with " + c1);
                        return;
                    }
                }
            } else {
                System.out.println("Invalid defense!");
            }
            return;
        }
    }


    
	

}