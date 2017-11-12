package SpriteMe;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MiniPalette extends Container {
	private static final long serialVersionUID = 4303748895061447018L;

	private static final int SIZE = SpriteMe.SPLOTCH_SIZE; // used for multi splotches
	private static final int ROWSIZE = SIZE/4;

	// local vars
	private final int parentIndex;
	private final SpritePartEditor mommy;
	private final Palette pal;
	private byte curIndex;
	private MultiSplotch[] splotches;

	/**
	 * Creates a new {@code MiniPalette}
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
	 * Sets up GUI
	 */
	private final void initializeDisplay() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		w.gridy = 0;
		w.gridx = 0;

		// index labels for first palette
		if (parentIndex == 0) {
			for (int i = 0; i < 16; i++, w.gridx++) {
				this.add(new JLabel(Palette.INDEX_NAMES[i], SwingConstants.CENTER), w);
			}
			w.gridx = 0;
			w.gridy++;
		}

		w.ipady = 2;
		w.ipadx = 2;
		for (int i = 0; i < 16; i++, w.gridx++) {
				this.add(splotches[i], w);
		}
	}

	/**
	 * Like the {@link Splotch} class, but displays multiple colors at once and
	 * with 0 functionality for actually changing colors.
	 *
	 */
	@SuppressWarnings("serial")
	private static class MultiSplotch extends JComponent {
		// local vars
		private SpriteColor[] colors;
		private final byte index;
		private final MiniPalette mommy;
		private boolean selected = false;

		public MultiSplotch(MiniPalette parent, byte i,
				SpriteColor c1, SpriteColor c2, SpriteColor c3, SpriteColor c4) {
			setSize(SpriteMe.SPLOTCH_DIMENSION);
			setMinimumSize(SpriteMe.SPLOTCH_DIMENSION);
			setPreferredSize(SpriteMe.SPLOTCH_DIMENSION);

			mommy = parent;
			index = i;
			colors = new SpriteColor[] { c1, c2, c3, c4 };

			addMouse();
		}

		public void setColors(SpriteColor c1, SpriteColor c2, SpriteColor c3, SpriteColor c4) {
			colors = new SpriteColor[] { c1, c2, c3, c4 };
		}

		public void paint(Graphics g) {
			// fill all colors evenly
			for (int i = 0; i < 4; i++) {
				g.setColor(colors[i].toColor());
				g.fillRect(0, ROWSIZE * i, SIZE, ROWSIZE);
			}

			// draw blue if selected
			g.setColor(selected ? Color.BLUE: Color.BLACK);
			g.drawRect(0, 0, SIZE, SIZE);
			if (selected) {
				g.drawRect(1, 1, SIZE-2, SIZE-2);
			}
		}

		/**
		 * Sets the selection status of this object.
		 * @param b
		 */
		public void setSelected(boolean b) {
			selected = b;
		}

		private final void addMouse() {
			this.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
					mommy.setIndex(index);
				}

				public void mousePressed(MouseEvent arg0) {
					mommy.setIndex(index);
				}

				// unused
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			});
		} // end addMouse()
	} // end MultiSplotch class
} // end MiniPalette