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

	Durak game;
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
       		JPanel south = new JPanel(new BorderLayout());
       		south.setOpaque(false);
    		south.setPreferredSize(new Dimension(0, 150));
       		JPanel playerOne = new JPanel();
       		playerOne.setOpaque(false);
       		playerOne.setPreferredSize(new Dimension(600, 120));
       		playerOne.add(new JLabel("Player One"));
       		south.add(playerOne);
			south.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       		cp.add(south, BorderLayout.SOUTH);

			// NORTH: Player Two area (opponent) information
            JPanel north = new JPanel(new BorderLayout());
            north.setOpaque(false);
            north.setPreferredSize(new Dimension(0, 150));
            JPanel playerTwo = new JPanel();
            playerTwo.setOpaque(false);
            playerTwo.setPreferredSize(new Dimension(600, 120));
            playerTwo.add(new JLabel("Player Two"));
            north.add(playerTwo);
			north.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(north, BorderLayout.NORTH);

			// EAST: Graveyard information
            JPanel east = new JPanel(new BorderLayout());
            east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
            east.setPreferredSize(new Dimension(160, 0));
            east.add(new JLabel("Graveyard"));
			east.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(east, BorderLayout.EAST);

			// West: Deck information
            JPanel west = new JPanel(new BorderLayout());
            west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
            west.setPreferredSize(new Dimension(160, 0));
            west.add(new JLabel("Deck"));
			west.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(west, BorderLayout.WEST);

			// CENTER: play area information
            JPanel center = new JPanel();
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
		this.setVisible(true);

		}

		    public JLayeredPane drawPile(Stack<Card> stackIn) {
				JLayeredPane pane = new JLayeredPane();
				final int CARD_W = 100;
				final int CARD_H = 145;
				final int OFFSET_Y = 30; // vertical overlap offset

				Object[] cards = stackIn.toArray(); // no extra '{' here
				// bottom-of-stack is index 0 for a Stack (Vector)FSET_Y * (cards.length - 1);
				pane.setPreferredSize(new Dimension(CARD_W, CARD_H + OFFSET_Y * (cards.length - 1)));
				pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				for (int i = 0; i < cards.length; i++) {
					Card c = (Card) cards[i]; // cast each element back to Card
					int x = 0;
					int y = i * OFFSET_Y;
					c.setBounds(x, y, CARD_W, CARD_H);
					pane.add(c, Integer.valueOf(i));
				}

				return pane;
			
		}
	   
			


       /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */
       //Card card = new Card(2, Card.Suit.Diamonds);
       //System.out.println(card);
       //this.add(card);    

        this.setVisible(true);
    


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

