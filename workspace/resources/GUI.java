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

private JLabel trumpLabel;



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

			// EAST: Graveyard information
            east = new JPanel(new BorderLayout());
            east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
            east.setPreferredSize(new Dimension(160, 0));
            east.add(new JLabel("Graveyard"));
			east.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
            cp.add(center, BorderLayout.CENTER);

   			
	   		//cards in flow layout - 4 layered panes in center panel
			center.setLayout(new FlowLayout());
			JLayeredPane playArea1 = new JLayeredPane();
			playArea1.setPreferredSize(new Dimension(130, 150));
	   		playArea1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			center.add(playArea1);
			JLayeredPane playArea2 = new JLayeredPane();
			playArea2.setPreferredSize(new Dimension(130, 150));
	   		playArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			center.add(playArea2);
			JLayeredPane playArea3 = new JLayeredPane();
			playArea3.setPreferredSize(new Dimension(130, 150));
	   		playArea3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			center.add(playArea3);
			JLayeredPane playArea4 = new JLayeredPane();
			playArea4.setPreferredSize(new Dimension(130, 150));
	   		playArea4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			center.add(playArea4);

			Stack<Card> testStack = new Stack<Card>();
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
				pane.setPreferredSize(new Dimension(130, 150));
				pane.setBorder(BorderFactory.createLineBorder(Color.red));
				for (int i = 0; i < cards.length; i++) {
					Card c = (Card) cards[i]; // cast each element back to Card
					int x = 0;
					int y = i * 30;
					c.setBounds(x, y, 130, 150);
					pane.add(c, Integer.valueOf(i));
					//cards[i].addMouseListener(this);
				}
				
				return pane;
		}
			

	// 	private String suitToSymbol(Card.Suit s) {
    //     if (s == null) return "";
    //     switch (s) {
    //         case Hearts:   return "♥";
    //         case Spades:   return "♠";
    //         case Diamonds: return "♦";
    //         case Clubs:    return "♣";
    //         default:       return "?";
    //     }
    // }

    // private Color suitToColor(Card.Suit s) {
    //     if (s == null) return Color.BLACK;
    //     switch (s) {
    //         case Hearts: case Diamonds: return Color.RED;
    //         case Spades: case Clubs:    return Color.BLACK;
    //         default: return Color.BLACK;
    //     }
    // }
			



	   private void updateDisplay() {
    		if (game == null) return;
			west.removeAll();
			west.add(new JLabel("Deck"));
			Stack<Card> deckPile= new Stack();
			
			deckPile.add(game.getTopDeckCard());
			game.getTrumpCard().show();
	
			deckPile.add(game.getTrumpCard());
			west.add(drawPile(deckPile));
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
    public void mousePressed(MouseEvent arg0) {

        if(card1==null){

             card1 = ((Card)arg0.getComponent());

             card1.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));

         }

         else {

             card2 = ((Card)arg0.getComponent());

              //rest of your logic here including checking to make sure if the move is legal, checking if the game is over and updating the screen.
			//if(!game.isGameOver()){
				
			}
              card1 = null; // reset the card variables so you're ready for another move

              card2 = null;

    }



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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