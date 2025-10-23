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


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Durak game;
    public GUI(Durak game){
	   this.game= game;
        //Create and set up the window.
       setTitle("Durak");
       setSize(900,700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
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

			// SOUTH: Player One (local player) hand area
       		JPanel south = new JPanel(new BorderLayout());
       		south.setOpaque(false);
    		//south.setPreferredSize(new Dimension(0, 150));
       		JPanel playerOne = new JPanel();
       		playerOne.setOpaque(false);
       		//playerOne.setPreferredSize(new Dimension(600, 120));
       		playerOne.add(new JLabel("Player One"));
       		south.add(playerOne);
			south.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       		cp.add(south, BorderLayout.SOUTH);

			// NORTH: Player Two area (opponent)
            JPanel north = new JPanel(new BorderLayout());
            north.setOpaque(false);
            //north.setPreferredSize(new Dimension(0, 150));
            JPanel playerTwo = new JPanel();
            playerTwo.setOpaque(false);
            //playerTwo.setPreferredSize(new Dimension(600, 120));
            playerTwo.add(new JLabel("Player Two"));
            north.add(playerTwo);
			north.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(north, BorderLayout.NORTH);

			// EAST: graveyard
            JPanel east = new JPanel(new BorderLayout());
            east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
            //east.setPreferredSize(new Dimension(160, 0));
            east.add(new JLabel("Graveyard"));
			east.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(east, BorderLayout.EAST);

			// CENTER: play area - use JLayeredPane for overlapping cards
            JLayeredPane centerLayer = new JLayeredPane();
            //centerLayer.setPreferredSize(new Dimension(800, 400)); // workspace for table
            //centerLayer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			centerLayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(centerLayer, BorderLayout.CENTER);

			// EAST: deck
            JPanel west = new JPanel(new BorderLayout());
            west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
            //west.setPreferredSize(new Dimension(160, 0));
            west.add(new JLabel("Deck"));
			west.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cp.add(west, BorderLayout.WEST);
	   
	   
       
       /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */
       //Card card = new Card(2, Card.Suit.Diamonds);
       //System.out.println(card);
       //this.add(card);    

        this.setVisible(true);
    }


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
