package resources;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
private Card card1;
private Card card2;
Durak game;
	
JPanel south;
JPanel north;
JPanel east;
JPanel west; //deck area
JPanel center;

JPanel playerOne;
JPanel playerTwo;


private static final int CARD_WIDTH = 120;
private static final int CARD_HEIGHT = 150;
private static final int HAND_OVERLAP = CARD_WIDTH / 4;



private JLabel trumpLabel;
private JLabel endGame;

private JToggleButton viewHandsButtonSouth;
private JToggleButton viewHandsButtonNorth;
private JButton endTurn;





    public GUI(Durak game){
	   this.game= game;
        //Create and set up the window.
       setTitle("Durak");
       setSize(900,550);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setResizable(false);
       
       // this supplies the background
       try {
		System.out.println(getClass().toString());
		Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
		setContentPane(new ImagePanel(blackImg));

       }catch(IOException e) {
    	   e.printStackTrace();
       }
	   
	   for(Card c: game.deck){
		c.addMouseListener(this);
	   }
	
    		Container cp = getContentPane();
   			cp.setLayout(new BorderLayout());

			// SOUTH: Player One (local player) hand area information
       		south = new JPanel(new BorderLayout());
       		south.setOpaque(false);
    		south.setPreferredSize(new Dimension(0, 150));
       		playerOne = new JPanel();
       		playerOne.setOpaque(false);
       		playerOne.setPreferredSize(new Dimension(600, 120));
       		playerOne.add(new JLabel("Player One"));
       		south.add(playerOne);
			south.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       		cp.add(south, BorderLayout.SOUTH);


	   		viewHandsButtonSouth = new JToggleButton("Show Hands");
            viewHandsButtonSouth.setToolTipText("Toggle to show/hide both players' hands");
            viewHandsButtonSouth.addActionListener(this);
            south.add(viewHandsButtonSouth, BorderLayout.EAST);


			// NORTH: Player Two area (opponent) information
            north = new JPanel(new BorderLayout());
            north.setOpaque(false);
            north.setPreferredSize(new Dimension(0, 150));
            playerTwo = new JPanel();
            playerTwo.setOpaque(false);
            playerTwo.setPreferredSize(new Dimension(600, 120));
            playerTwo.add(new JLabel("Player Two"));
            north.add(playerTwo);
			north.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(north, BorderLayout.NORTH);

	   		viewHandsButtonNorth = new JToggleButton("Show Hands");
            viewHandsButtonNorth.setToolTipText("Toggle to show/hide both players' hands");
            viewHandsButtonNorth.addActionListener(this);
            north.add(viewHandsButtonNorth, BorderLayout.EAST);


			// EAST: Graveyard information
            east = new JPanel(new BorderLayout());
            east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
            east.setPreferredSize(new Dimension(160, 0));
            east.add(new JLabel("Graveyard"));
			east.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			endTurn = new JButton("End Turn");
			endTurn.setOpaque(false);
			endTurn.setPreferredSize(new Dimension(20,20));
			endTurn.addActionListener(this);
			east.add(endTurn, BorderLayout.SOUTH);
            cp.add(east, BorderLayout.EAST);

			// West: Deck information
            west = new JPanel(new BorderLayout());
            west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
            west.setPreferredSize(new Dimension(160, 0));
            west.add(new JLabel("Deck"));
			west.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(west, BorderLayout.WEST);
			trumpLabel = new JLabel("", SwingConstants.CENTER);
            trumpLabel.setPreferredSize(new Dimension(160, 60));
            trumpLabel.setFont(new Font("Serif", Font.BOLD, 36));
            west.add(trumpLabel);
			


			// CENTER: play area information
            center = new JPanel();
            center.setPreferredSize(new Dimension(800, 400)); // workspace for table
            center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			endGame=new JLabel();
			center.add(endGame);
            cp.add(center, BorderLayout.CENTER);

   			
	   		//cards in flow layout - 4 layered panes in center panel
			center.setLayout(new FlowLayout());
			
			// JLayeredPane playArea2 = new JLayeredPane();
			// playArea2.setPreferredSize(new Dimension(120, 170));
	   		// playArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			// center.add(playArea2);
			// JLayeredPane playArea3 = new JLayeredPane();
			// playArea3.setPreferredSize(new Dimension(120, 170));
	   		// playArea3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			// center.add(playArea3);
			// JLayeredPane playArea4 = new JLayeredPane();
			// playArea4.setPreferredSize(new Dimension(120, 170));
	   		// playArea4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			// center.add(playArea4);

			//Stack<Card> testStack = new Stack<Card>();
			// testStack.add(new Card(2, Card.Suit.Diamonds));
			// testStack.add(new Card(5, Card.Suit.Diamonds));
			// playArea1.setLayout(new FlowLayout());
			// playArea1.add(drawPile(testStack));

			this.setVisible(true);
			updateDisplay();
		}




		public JLayeredPane drawPile(Stack<Card> stackIn) {
				JLayeredPane pane = new JLayeredPane();
				Object[] cards = stackIn.toArray(); 
				pane.setPreferredSize(new Dimension(120, 170));
				pane.setBorder(BorderFactory.createLineBorder(Color.red));
				for (int i = 0; i < cards.length; i++) {
					Card c = (Card) cards[i]; // cast each element back to Card
					int x = 0;
					int y = i * 35;
					
					c.setBounds(x, y, 120, 170);
					pane.add(c, Integer.valueOf(i));
			
				}
				
				return pane;
		}
			
			
		private void renderPlayerHand(java.util.List<Card> hand, JPanel panel, boolean faceUp) {
			panel.removeAll();
			// Use null layout or FlowLayout with gaps; simplest: FlowLayout with small horizontal gap
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, -HAND_OVERLAP, 0));

			for (Card c : hand) {
				if (faceUp) c.show(); else c.hide();
				// Ensure the Card component uses the consistent size
				c.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
				c.setSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
				// Add mouse listener so user can interact
				c.addMouseListener(this);
				panel.add(c);
			}
			panel.revalidate();
			panel.repaint();
		}



	   private void updateDisplay() {
    		if (game == null) {
				
				return;
	 		}
			west.removeAll();
			west.add(new JLabel("Deck"));
			Stack<Card> deckPile = new Stack();
			game.getTrumpCard().show();
			deckPile.add(game.getTrumpCard());
			deckPile.add(game.getTopDeckCard());
			west.add(drawPile(deckPile));

			boolean southFaceUp = viewHandsButtonSouth != null && viewHandsButtonSouth.isSelected();
            boolean northFaceUp = viewHandsButtonNorth != null && viewHandsButtonNorth.isSelected();
			
            renderPlayerHand(game.getHand1(), playerOne, southFaceUp);
            renderPlayerHand(game.getHand2(), playerTwo, northFaceUp);
			center.removeAll();
			for(Stack<Card> stack:game.getColumns()){
				JLayeredPane playArea = new JLayeredPane();
				playArea.setPreferredSize(new Dimension(120, 170));
				playArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				int yoffset=0;
				for(Card c: stack){
					c.setBounds(0, yoffset, 120, 170);
					playArea.add(c);
					yoffset+=50;
				}
				
				center.add(playArea);
			}
			
    	}
    
        
    
			


       /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */
       //Card card = new Card(2, Card.Suit.Diamonds);
       //System.out.println(card);
       //this.add(card);    

        
    
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	@Override
    public void mouseReleased(MouseEvent arg0) {
		Card selectedCard = (Card)arg0.getComponent();

        if(card1==null){
             card1 = selectedCard;
             card1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
			 repaint();
			 return;
         }

         else {
			
		
             card2 = selectedCard;

			game.doMove(card1, card2);
			System.out.println("do move");
				updateDisplay();
				
			}
              card1 = null; // reset the card variables so you're ready for another move
              card2 = null;

    }



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
    public void actionPerformed(ActionEvent arg0) {
        // south toggle controls only playerOne
        if (arg0.getSource() == viewHandsButtonSouth) {
            boolean on = viewHandsButtonSouth.isSelected();
            String text;
			if (on) {
				text = "Hide Hand2";
			} else {
				text = "Show Hand2";
			}
			viewHandsButtonSouth.setText(text);
            updateDisplay();
            return;
        }

        // north toggle controls only playerTwo
        if (arg0.getSource() == viewHandsButtonNorth) {
            boolean on = viewHandsButtonNorth.isSelected();
            String text;
			if (on) {
				text = "Hide Hand1";
			} else {
				text = "Show Hand1";
			}
			viewHandsButtonNorth.setText(text);
            updateDisplay();
            return;
        }



		
		if(arg0.getSource()==endTurn){
			
			}

        // other actions...
    }
	// private void update(){
	// 	columns.removeAll();
	// 	topColumns.removeAll();

	// 	ArrayList<Stack<Card>> allColumns = game.getColumns();
	// 	for(Stack<Card> stack:allColumns){
	// 		topColumns.add(drawPile(stack,false));
	// 	}
	// 	columns.add(drawDeck(game.getDeck()));

	// 	columns.add(drawPile(game.getPile(), true));

	// 	columns.add(drawFinal(game.hearts, "hearts"));

	// 	columns.add(drawFinal(game.spades, "spades"));

	// 	columns.add(drawFinal(game.diamonds, "diamonds"));

	// 	columns.add(drawFinal(game.clubs, "clubs"));

	// 	System.out.println("updating");
	// 	this.revalidate();
    // 	this.repaint();
	// }
}
