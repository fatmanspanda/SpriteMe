package SpriteMe;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class MiniPalette extends Container {
	private static final long serialVersionUID = 4303748895061447018L;
	
	private Palette pal;
	private final int parentIndex;
	private byte curIndex;
	private final SpritePartEditor mommy;
	private MultiSplotch[] splotches;
	
	/**
	 * 
	 * @param parent
	 * @param p
	 * @param index
	 */
	public MiniPalette(SpritePartEditor parent, Palette p, int index) {
		mommy = parent;
		pal = p;
		parentIndex = index;
		splotches = new MultiSplotch[16];
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
	 * 
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

	private void initializeDisplay() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		w.gridy = 0;
		w.gridx = 0;
		for (int i = 0; i < 16; i++, w.gridx++) {
				this.add(splotches[i], w);
		}
	}
}
