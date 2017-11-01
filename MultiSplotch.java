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

	private static final long serialVersionUID = -2349012926208164404L;
	private static final int SIZE = SpriteMe.SPLOTCH_SIZE;
	private static final int ROWSIZE = SIZE/4;
	private SpriteColor[] colors;
	private final byte index;
	private final MiniPalette mommy;
	private boolean selected = false;
	/**
	 * 
	 * @param c
	 */
	public MultiSplotch(MiniPalette parent, byte i, SpriteColor c1, SpriteColor c2, SpriteColor c3, SpriteColor c4) {
		mommy = parent;
		index = i;
		colors = new SpriteColor[] { c1, c2, c3, c4 };
		this.setSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setMinimumSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setPreferredSize(SpriteMe.SPLOTCH_DIMENSION);
		setToolTip();
		addMouse();
	}
	/**
	 * 
	 */
	private void setToolTip() {
		this.setToolTipText(this.toString());
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
		g.setColor(selected ? Color.BLUE : Color.BLACK);
		g.drawRect(0, 0, SIZE, SIZE);
	}
	
	/**
	 * 
	 */
	public void setSelected(boolean s) {
		selected = s;
	}
	/**
	 * 
	 */
	private void addMouse() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				mommy.setIndex(index);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
}