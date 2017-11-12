package SpriteMe;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import SpriteManipulator.SpriteManipulator;

public class Palette extends Container {
	// class constants
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
			"Hands",
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

	private static final SpriteColor GLOVES = new SpriteColor("Gloves", 181, 186, 165);
	private static final SpriteColor MITTS = new SpriteColor("Mitts", 181, 219, 0);

	private static final int[] UNCHANGEABLE_INDICES =
			{ 0, 1, 2, 3, 4, 5, 6, 7 };

	public static final String[] INDEX_NAMES =  new String[] {
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"10", "11", "12", "13", "14", "15", "G", "M"
	};

	private static final Border rightPad = BorderFactory.createEmptyBorder(0,0,0,5);

	// local vars
	private Splotch[][] splotches = new Splotch[4][18];
	private ColorEditor editInterface;
	private int lastSelectedIndex;

	/**
	 * Makes a new palette with Link's vanilla colors.
	 */
	public Palette() {
		initializePalette();
		initializeDisplay();
	}

	/**
	 * Sets the palette to Link's vanilla colors.
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
			splotches[palN][palI] = new Splotch(this, palI, vanilla);
		}
		// set gloves and mitts
		for (int i = 0; i < 4; i++) {
			splotches[i][16] = new Splotch(this, 16, GLOVES);
			splotches[i][17] = new Splotch(this, 17, MITTS);
		}
	}

	/**
	 * Sets up GUI
	 */
	private void initializeDisplay() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		w.fill = GridBagConstraints.HORIZONTAL;

		w.gridy = 0;
		w.gridx = 0;
		JLabel indexWord = new JLabel("Index #", SwingConstants.RIGHT);
		indexWord.setBorder(rightPad);
		this.add(indexWord, w);

		w.gridx = 1;
		for (int i = 0; i < 18; i++, w.gridx++) {
			this.add(new JLabel(INDEX_NAMES[i], SwingConstants.CENTER), w);
		}

		w.gridy = 1;
		w.ipady = 2;
		w.ipadx = 2;
		for (int i = 0; i < 4; i++, w.gridy++) {
			w.gridx = 0;
			JLabel mailName = new JLabel(SpriteMe.MAIL_NAMES[i], SwingConstants.RIGHT);
			mailName.setBorder(rightPad);

			this.add(
					mailName,
					w);
			w.gridx++;
			for (int j = 0; j < 18; j++, w.gridx++) {
					if (!editableIndex(j)) {
						splotches[i][j].setEnabled(false);
					}
				this.add(splotches[i][j], w);
			}
		}
	}

	/**
	 * Sets indices 3 and 4 to the shaded and base colors, respectively,
	 * of a desired skin color.
	 * @param p
	 */
	public void setSkinColor(ColorPair p) {
		for (int m = 0; m < 4; m++) {
			splotches[m][4].setColor(p.color1());
			splotches[m][3].setColor(p.color2());
		}
		fireSpriteChangeEvent();
		refreshColorEditor(3,4);
	}

	/**
	 * @param i - index to check
	 * @return Whether or not the index can be edited directly by the user.
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
	 * @param m - Palette set
	 * @param i - Color index
	 * @return The current {@code SpriteColor} object for the {@code Splotch} desired.
	 */
	public SpriteColor colorForMailAndIndex(int m, int i) {
		return splotches[m][i].getColor();
	}

	/**
	 * @param i
	 * @return An array of all {@code Splotch} objects at desired index.
	 */
	public Splotch[] splotchesForIndex(int i) {
		return new Splotch[] {
				splotches[0][i],
				splotches[1][i],
				splotches[2][i],
				splotches[3][i]
		};
	}

	/**
	 * Attaches a {@link ColorEditor} for communication.
	 * @param edit - {@code ColorEditor}
	 */
	public void attachEditor(ColorEditor edit) {
		editInterface = edit;
	}

	/**
	 * Notifies this {@code Palette} that an index has been selected.
	 * To be called by a child {@code Splotch} component.
	 * @param i
	 */
	public void indexClicked(int i) {
		lastSelectedIndex = i;
		editInterface.editNewColor(i);
		editInterface.repaint();
	}

	/**
	 * Refreshes the palette for the attached {@code ColorEditor}
	 * if any index passed has been changed and is selected.
	 * @param i
	 */
	public void refreshColorEditor(int... i) {
		for (int n : i) {
			if (n == lastSelectedIndex) {
				editInterface.editNewColor(lastSelectedIndex);
			}
		}
	}

	/**
	 * @return A 2 dimension {@code byte} of all colors in this palette as RGB values.
	 */
	public byte[][] toArray() {
		byte[][] ret = new byte[66][3];
		for (int i = 0; i < 64; i++) {
			int mailI = i / 16;
			int colI = i % 16;
			ret[i] = splotches[mailI][colI].getColor().getRGB();
		}

		// set gloves and mitts to palette
		ret[64] = splotches[0][16].getColor().getRGB();
		ret[65] = splotches[0][17].getColor().getRGB();

		return ret;
	}

	/**
	 * @return All colors as an array of integers formatted with digits RRRGGGBBB.
	 */
	public int[] toRGB9Array() {
		byte[][] temp = toArray();
		int[] ret = new int[66];
		for (int i = 0; i < 66; i++) {
			ret[i] = SpriteManipulator.toRGB9(
						Byte.toUnsignedInt(temp[i][0]),
						Byte.toUnsignedInt(temp[i][1]),
						Byte.toUnsignedInt(temp[i][2])
					);
		}
		return ret;
	}

	/**
	 * Informs this palette that a specific {@code Splotch} has been updated.
	 * @param child - {@code Splotch} firing the event.
	 */
	public void colorChanged(Splotch child) {
		fireSpriteChangeEvent();
	}

	/*
	 * Change listeners
	 */
	private List<SpriteChangeListener> spriteListeners = new ArrayList<SpriteChangeListener>();

	public synchronized void addSpriteChangeListener(SpriteChangeListener s) {
		spriteListeners.add(s);
	}

	public synchronized void removeSpriteChangeListener(SpriteChangeListener s) {
		spriteListeners.remove(s);
	}

	private synchronized void fireSpriteChangeEvent() {
		SpriteChangeEvent s = new SpriteChangeEvent(this);
		Iterator<SpriteChangeListener> listening = spriteListeners.iterator();
		while(listening.hasNext()) {
			(listening.next()).eventReceived(s);
		}
	}
}