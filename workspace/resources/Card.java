package resources;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Card class to store the information of single card
 * @member {Suit} suit The suit of the card (Spades,Hearts,Diamonds,Clubs)
 * @member {Integer} value The value of the card (1->13)
 */
public class Card extends JPanel{
		// Members
		public int value;
		public Suit suit;
		private BufferedImage image;
		private BufferedImage backImage;
		boolean isReversed;
		Point positionOffset;
		private double rotationDegrees = 0.0;
		/**
		 * Enum to store the suit values
		 */
		public enum Suit {
			Spades(1, false),
			Hearts(2, true),
			Diamonds(3, true),
			Clubs(4, false);
			
			public int value;
			public boolean isRed;
			
			private Suit(int value, boolean isRed) {
				this.value = value;
				this.isRed = isRed;
			}
		};
		
		/**
		 * Converts the value of the card to a string
		 * @param {Integer} value The value of the card 
		 */
		public static String valueString(int value) {
								
			if(value == 11) return "j";
			if(value == 12) return "q";
			if(value == 13) return "k";
			if(value == 1) return "a";
			
			// Value between 2 and 10
			return Integer.toString(value);
		}

		/**
		 * Converts the value of the card to a int
		 * @param {String} value The value of the card 
		 */
		public static int valueInt(String value) {
			
			if(value.equals("j")) return 11;
			if(value.equals("q")) return 12;
			if(value.equals("k")) return 13;
			if(value.equals("a")) return 1;
			
			return Integer.parseInt(value);
		}
		/**
		 * toString method, eg: "K of Diamonds"
		 * @return {String} Description of the current card
		 */
		public String toString() {
			return valueString(value) + " of " + suit.name().toLowerCase();
		}
		
		/**
		 * Returns a string that can be used to re-initialize the card
		 * @return {String} Class properties, " of " separated.
		 */
		public String saveAsString() {
			return valueString(value) + " of " + suit.name() + " of " + isReversed;
		}
		
		/**
		 * Class constructor
		 * @param {Integer} value The value of the card, in [1,13]
		 * @param {Suit} suit The suit of the card
		 */
		public Card(int value, Suit suit) {
			this.value = value;
			this.suit = suit;		
			isReversed = false;
			
			try {
				// Load the image for the current file
				URL url = getClass().getResource(this.toString() +".png");
				image = ImageIO.read(url);
				//System.out.println(url);
				URL urlback = getClass().getResource("back.png"); 
				backImage = ImageIO.read(urlback);
				
				setBounds(0, 0, image.getWidth(), image.getHeight());
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			positionOffset = new Point(0,0);
			setSize(new Dimension(100, 145));
			setOpaque(false);
		}
		
		/**
		 * Turns the card with the back up
		 */
		public void hide() {
			isReversed = true;
		}
		
		/**
		 * Turns the card with the face up
		 */
		public void show() {
			isReversed = false;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			BufferedImage img = image;
			if(isReversed) img = backImage;


			Graphics2D g2 = (Graphics2D) g.create();
    		int w = getWidth();
    		int h = getHeight();

    		// rotate about the center
    		double theta = Math.toRadians(rotationDegrees);
    		g2.rotate(theta, w / 2.0, h / 2.0);

    		// draw the image scaled to the component bounds
    		g2.drawImage(img, 0, 0, w, h, null);
    		g2.dispose();


			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}


		

		public void setRotationDegrees(double degrees) {
    		this.rotationDegrees = degrees % 360.0;
    		// swap preferred size if rotated by 90 or 270 to avoid clipping (optional)
    		if ((int)Math.abs(rotationDegrees) % 180 == 90) {
        		Dimension d = getPreferredSize();
        		setPreferredSize(new Dimension(d.height, d.width));
    		}
    		revalidate();
    		repaint();
		}
	
}