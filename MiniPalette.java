package SpriteMe;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class MiniPalette extends Container {
	private static final long serialVersionUID = 4303748895061447018L;

	// local vars
	private final int parentIndex;
	private final SpritePartEditor mommy;
	private final Palette pal;
	private byte curIndex;
	private MultiSplotch[] splotches;

	/**
	 * Creates a new {@code MiniPalette}.
	 * @param parent
	 * @param p
	 * @param index
	 */
	public MiniPalette(SpritePartEditor parent, Palette p, int index) {
		mommy = parent;
		parentIndex = index;
		splotches = new MultiSplotch[16];
		pal = p;
		// set a new splotch with the 4 colors from parent palette
		for (int i = 0; i < 16; i++) {
			SpriteColor c1 = p.colorForMailAndIndex(0, i);
			SpriteColor c2 = p.colorForMailAndIndex(1, i);
			SpriteColor c3 = p.colorForMailAndIndex(2, i);
			SpriteColor c4 = p.colorForMailAndIndex(3, i);
			splotches[i] = new MultiSplotch(this, (byte) i, c1, c2, c3, c4);
		}
		initializeDisplay();
	}

	/**
	 * Set the selected index and unselect the previously selected one.
	 * @param i
	 */
	public void setIndex(byte i) {
		// unselect current splotch
		splotches[curIndex].setSelected(false);

		// select new splotch
		curIndex = i;
		splotches[curIndex].setSelected(true);
		mommy.setPaletteColor(parentIndex, curIndex);
	}

	/**
	 * Revalidates the colors of the palette.
	 */
	public void refreshPalette() {
		for (int i = 0; i < 16; i++) {
			SpriteColor c1 = pal.colorForMailAndIndex(0, i);
			SpriteColor c2 = pal.colorForMailAndIndex(1, i);
			SpriteColor c3 = pal.colorForMailAndIndex(2, i);
			SpriteColor c4 = pal.colorForMailAndIndex(3, i);
			splotches[i].setColors(c1, c2, c3, c4);
		}
	}

	/**
	 * Sets up GUI.
	 */
	private void initializeDisplay() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		w.gridy = 0;
		w.gridx = 0;
		w.ipadx = 2;
		w.ipady = 2;
		for (int i = 0; i < 16; i++, w.gridx++) {
				this.add(splotches[i], w);
		}
	}
}