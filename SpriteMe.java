package SpriteMe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SpriteManipulator.SpriteManipulator;

public class SpriteMe {
	private static final String VERSION = "v0.0.0";

	public static final byte[][] VANILLAPALETTES = {
			// Green mail
			{ (byte)   0, (byte)   0, (byte)   0 }, // Transparent
			{ (byte) 248, (byte) 248, (byte) 248 }, // White
			{ (byte) 240, (byte) 216, (byte)  64 }, // Yellow
			{ (byte) 184, (byte) 104, (byte)  32 }, // Skin
			{ (byte) 240, (byte) 160, (byte) 104 }, // Lieutenant face
			{ (byte)  40, (byte)  40, (byte)  40 }, // Outline
			{ (byte) 248, (byte) 120, (byte)   0 }, // Orange
			{ (byte) 192, (byte)  24, (byte)  32 }, // Red
			{ (byte) 232, (byte)  96, (byte) 176 }, // Hair
			{ (byte)  56, (byte) 144, (byte) 104 }, // Tunic
			{ (byte)  64, (byte) 216, (byte) 112 }, // Light tunic
			{ (byte)  80, (byte) 144, (byte)  16 }, // Hat
			{ (byte) 120, (byte) 184, (byte)  32 }, // Light hat
			{ (byte) 224, (byte) 144, (byte)  80 }, // Gloves
			{ (byte) 136, (byte)  88, (byte)  40 }, // Sleeves
			{ (byte) 192, (byte) 128, (byte) 240 }, // Water
			// Blue mail
			{ (byte)   0, (byte)   0, (byte)   0 },
			{ (byte) 248, (byte) 248, (byte) 248 },
			{ (byte) 240, (byte) 216, (byte)  64 },
			{ (byte) 184, (byte) 104, (byte)  32 },
			{ (byte) 240, (byte) 160, (byte) 104 },
			{ (byte)  40, (byte)  40, (byte)  40 },
			{ (byte) 248, (byte) 120, (byte)   0 },
			{ (byte) 192, (byte)  24, (byte)  32 },
			{ (byte) 232, (byte)  96, (byte) 176 },
			{ (byte)   0, (byte)  96, (byte) 208 },
			{ (byte) 136, (byte) 160, (byte) 232 },
			{ (byte) 192, (byte) 160, (byte)  72 },
			{ (byte) 248, (byte) 216, (byte) 128 },
			{ (byte) 224, (byte) 144, (byte)  80 },
			{ (byte) 200, (byte)  96, (byte)  32 },
			{ (byte) 192, (byte) 128, (byte) 240 },
			// Red mail
			{ (byte)   0, (byte)   0, (byte)   0 },
			{ (byte) 248, (byte) 248, (byte) 248 },
			{ (byte) 240, (byte) 216, (byte)  64 },
			{ (byte) 184, (byte) 104, (byte)  32 },
			{ (byte) 240, (byte) 160, (byte) 104 },
			{ (byte)  40, (byte)  40, (byte)  40 },
			{ (byte) 248, (byte) 120, (byte)   0 },
			{ (byte) 192, (byte)  24, (byte)  32 },
			{ (byte) 232, (byte)  96, (byte) 176 },
			{ (byte) 184, (byte)  16, (byte)  32 },
			{ (byte) 240, (byte)  88, (byte) 136 },
			{ (byte) 152, (byte) 120, (byte) 216 },
			{ (byte) 200, (byte) 168, (byte) 248 },
			{ (byte) 224, (byte) 144, (byte)  80 },
			{ (byte)  56, (byte) 136, (byte)  64 },
			{ (byte) 192, (byte) 128, (byte) 240 },
			// Bunny
			{ (byte)   0, (byte)   0, (byte)   0 },
			{ (byte) 248, (byte) 248, (byte) 248 },
			{ (byte) 240, (byte) 216, (byte)  64 },
			{ (byte) 184, (byte) 104, (byte)  32 },
			{ (byte) 240, (byte) 160, (byte) 104 },
			{ (byte)  40, (byte)  40, (byte)  40 },
			{ (byte) 248, (byte) 120, (byte)   0 },
			{ (byte) 192, (byte)  24, (byte)  32 },
			{ (byte) 184, (byte)  96, (byte) 120 },
			{ (byte)  56, (byte) 144, (byte) 104 },
			{ (byte)  64, (byte) 216, (byte) 112 },
			{ (byte)  80, (byte) 144, (byte)  16 },
			{ (byte) 120, (byte) 184, (byte)  32 },
			{ (byte) 240, (byte) 152, (byte) 168 },
			{ (byte) 144, (byte)  24, (byte)  48 },
			{ (byte) 192, (byte) 128, (byte) 240 }
		};
	
	/*
	 * Combo box constants
	 */
	private static final ColorPair[] SKINCOLORS = new ColorPair[] {
			ColorPair.WHITE_SKIN,
			ColorPair.PALE_SKIN,
			ColorPair.HISPANIC_SKIN,
			ColorPair.BROWN_SKIN,
			ColorPair.BLACK_SKIN
	};

	public static void main(String[] args) {
		//try to set LaF
		try {
			UIManager.setLookAndFeel("metal");
		} catch (UnsupportedLookAndFeelException
				| ClassNotFoundException
				| InstantiationException
				| IllegalAccessException e) {
			// try to set System default
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException
					| ClassNotFoundException
					| InstantiationException
					| IllegalAccessException e2) {
					// do nothing
			} //end System
		} // end Metal
		
		// main window
		final Dimension d = new Dimension(500,500);
		final JFrame frame = new JFrame("Sprite Me " + VERSION);
		final JPanel controls = new JPanel(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		final JComboBox<ColorPair> skinPick = new JComboBox<ColorPair>(SKINCOLORS);
		final JPanel image = new JPanel();
		w.gridy = 0;
		controls.add(skinPick, w);

		
		// format frame
		final Container fullWrap = frame.getContentPane();
		SpringLayout l = new SpringLayout();
		frame.setLayout(l);
		// add image
		l.putConstraint(SpringLayout.WEST, image, 5,
				SpringLayout.WEST, fullWrap);
		l.putConstraint(SpringLayout.NORTH, image, 5,
				SpringLayout.NORTH, fullWrap);
		frame.add(image);

		// add controls
		l.putConstraint(SpringLayout.EAST, controls, -5,
				SpringLayout.EAST, fullWrap);
		l.putConstraint(SpringLayout.NORTH, controls, 5,
				SpringLayout.NORTH, fullWrap);
		frame.add(controls);
		

		// display frame
		frame.setPreferredSize(d);
		frame.setMinimumSize(d);
		setAllSizes(frame, d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}
	
	/**
	 * Set min max and preferred size for a component
	 * @param c
	 * @param d
	 */
	private static void setAllSizes(Component c, Dimension d) {
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
	}
}
