package SpriteMe;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

/**
 * Like the {@link Splotch} class, but displays multiple colors at once and
 * with 0 functionality for actually changing colors.
 *
 */
public class MultiSplotch extends JComponent {
	// Class constants
	private static final long serialVersionUID = -2349012926208164404L;
	private static final int SIZE = SpriteMe.SPLOTCH_SIZE;
	private static final int ROWSIZE = SIZE/4;

	// local vars
	private SpriteColor[] colors;
	private final byte index;
	private final MiniPalette mommy;
	private boolean selected = false;

	/**
	 * Creates a new {@code MultiSplotch}.
	 * @param parent - {@code MiniPalette} holding this splotch
	 * @param i - Index of this spotch
	 * @param c1 - Palette 1 color
	 * @param c2 - Palette 2 color
	 * @param c3 - Palette 3 color
	 * @param c4 - Palette 4 color
	 */
	public MultiSplotch(MiniPalette parent, byte i, SpriteColor c1, SpriteColor c2, SpriteColor c3, SpriteColor c4) {
		mommy = parent;
		index = i;
		colors = new SpriteColor[] { c1, c2, c3, c4 };
		this.setSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setMinimumSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setPreferredSize(SpriteMe.SPLOTCH_DIMENSION);
		addMouse();
	}

	/**
	 * Sets four colors for this splotch.
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 */
	public void setColors(SpriteColor c1, SpriteColor c2, SpriteColor c3, SpriteColor c4) {
		colors = new SpriteColor[] { c1, c2, c3, c4 };
	}

	/**
	 * 
	 */
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

	/**
	 * Adds mouse listeners.
	 */
	private void addMouse() {
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {
				mommy.setIndex(index);
			}

			public void mouseReleased(MouseEvent arg0) {}

		});
	} // end addMouse()
}