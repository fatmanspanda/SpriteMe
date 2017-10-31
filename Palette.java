package SpriteMe;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import SpriteManipulator.SpriteManipulator;
import SpriteMe.Listeners.*;

public class Palette extends Container {

	private static final long serialVersionUID = 9036341073446768809L;

	private static final String[] VANILLA_PALETTE_NAMES = {
			"Transparent",
			"White",
			"Yellow",
			"Skin shade",
			"Skin",
			"Outline",
			"Orange",
			"Red",
			"Hair",
			"Tunic shade",
			"Tunic",
			"Hat shade",
			"Hat",
			"Gloves",
			"Sleeves",
			"Water"
	};

	public static final byte[][] VANILLA_PALETTE = {
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

	private Splotch[][] splotches = new Splotch[4][16];
	private static final int[] UNCHANGEABLE_INDICES =
		{ 0, 1, 2, 5, 6, 7, 13 };

	/**
	 * 
	 */
	public Palette() {
		initializePalette();
		initializeDisplay();
	}
	
	/**
	 * 
	 */
	private void initializePalette() {
		int palN;
		int palI;
		for (int i = 0; i < 64; i++) {
			palN = i / 16;
			palI = i % 16;
			SpriteColor vanilla = new SpriteColor(
								VANILLA_PALETTE_NAMES[palI],
								VANILLA_PALETTE[i][0],
								VANILLA_PALETTE[i][1],
								VANILLA_PALETTE[i][2]);
			splotches[palN][palI] = new Splotch(vanilla);
		}
	}
	
	/**
	 * 
	 */
	private void initializeDisplay() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();

		w.gridy = 0;
		for (int i = 0; i < 4; i++, w.gridy++) {
			w.gridx = 0;
			// TODO fix alignment?????
			this.add(
					new JLabel(SpriteMe.MAIL_NAMES[i], SwingConstants.RIGHT),
					w);
			w.gridx++;
			for (int j = 0; j < 16; j++, w.gridx++) {
					if (!editableIndex(j)) {
						splotches[i][j].setEditable(false);
					}
				this.add(splotches[i][j], w);
			}
		}
	}
	
	/**
	 * 
	 * @param m
	 * @param i
	 * @param c
	 */
	public void setColorOneMail(int m, int i, SpriteColor c) {
		if (editableIndex(i)) {
			splotches[m][i].setColor(c);
		} else {
			return; // break out to avoid firing an event
		}
		firePaletteChangeEvent();
	}

	/**
	 * 
	 * @param i
	 * @param c
	 */
	public void setColorAllMails(int i, SpriteColor c) {
		if (editableIndex(i)) {
			for (int m = 0; m < 4; m++) {
				splotches[m][i].setColor(c);
			}
		} else {
			return; // break out to avoid firing an event
		}
		firePaletteChangeEvent();
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param p
	 */
	public void setTwoColorsOneMail(int m, int i, int j, ColorPair p) {
		if (editableIndex(i) && editableIndex(j)) {
			splotches[m][i].setColor(p.color1());
			splotches[m][j].setColor(p.color2());
		} else {
			return; // break out to avoid firing an event
		}
		firePaletteChangeEvent();
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param p
	 */
	public void setTwoColorsAllMails(int i, int j, ColorPair p) {
		if (editableIndex(i) && editableIndex(j)) {
			for (int m = 0; m < 4; m++) {
				splotches[m][i].setColor(p.color1());
				splotches[m][j].setColor(p.color2());
			}
		} else {
			return; // break out to avoid firing an event
		}
		firePaletteChangeEvent();
	}
	
	/**
	 * 
	 * @param p
	 */
	public void setSkinColor(ColorPair p) {
		for (int m = 0; m < 4; m++) {
			splotches[m][4].setColor(p.color1());
			splotches[m][3].setColor(p.color2());
		}
		firePaletteChangeEvent();
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public static boolean editableIndex(int i) {
		boolean ret = true;
		for (int x : UNCHANGEABLE_INDICES) {
			if (i == x) {
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * 
	 */
	public void paint(Graphics g) {
		this.paintComponents(g);
	}
	
	/**
	 * 
	 */
	public byte[][] toArray() {
		byte[][] ret = new byte[64][3];
		for (int i = 0; i < 64; i++) {
			int mailI = i / 16;
			int colI = i % 16;
			ret[i] = splotches[mailI][colI].getColor().getRGB();
		}
		return ret;
	}
	
	/**
	 * 
	 */
	public int[] toRGB9Array() {
		byte[][] temp = toArray();
		int[] ret = new int[64];
		for (int i = 0; i < 64; i++) {
			ret[i] = SpriteManipulator.RGB9(
						Byte.toUnsignedInt(temp[i][0]),
						Byte.toUnsignedInt(temp[i][1]),
						Byte.toUnsignedInt(temp[i][2])
						);
		}
		return ret;
	}
	/*
	 * Change listeners
	 */
	private List<PaletteChangeListener> paletteListeners = new ArrayList<PaletteChangeListener>();
	public synchronized void addPaletteChangeListener(PaletteChangeListener s) {
		paletteListeners.add(s);
	}

	public synchronized void removePaletteChangeListener(PaletteChangeListener s) {
		paletteListeners.remove(s);
	}

	private synchronized void firePaletteChangeEvent() {
		PaletteChangeEvent s = new PaletteChangeEvent(this);
		Iterator<PaletteChangeListener> listening = paletteListeners.iterator();
		while(listening.hasNext()) {
			(listening.next()).eventReceived(s);
		}
	}
}