package SpriteMe;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

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

	private SpriteColor[][] pal = new SpriteColor[4][16];
	private Splotch[][] splotches = new Splotch[4][16];
	private static final int[] UNCHANGEABLE_INDICES =
		{ 0, 1, 2, 5, 6, 7, 13 };

	public Palette() {
		initializePalette();
		initializeDisplay();
	}
	
	private void initializePalette() {
		int palN;
		int palI;
		for (int i = 0; i < 64; i++) {
			palN = i / 16;
			palI = i % 16;
			pal[palN][palI] = new SpriteColor(
								VANILLA_PALETTE_NAMES[palI],
								VANILLA_PALETTE[i][0],
								VANILLA_PALETTE[i][1],
								VANILLA_PALETTE[i][2]);
			splotches[palN][palI] = new Splotch(pal[palN][palI]);
		}
	}
	
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
	public void paint(Graphics g) {
		this.paintComponents(g);
	}
}
