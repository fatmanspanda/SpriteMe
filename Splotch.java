package SpriteMe;

import java.awt.Component;
import java.awt.Graphics;

public class Splotch extends Component {

	private static final long serialVersionUID = -2349012926208164404L;
	private SpriteColor color;
	private static final int SIZE = SpriteMe.SPLOTCH_SIZE;
	public Splotch(SpriteColor c) {
		color = c;
		this.setForeground(color.toColor());
		this.setSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setMinimumSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setPreferredSize(SpriteMe.SPLOTCH_DIMENSION);
	}
	
	public void paint(Graphics g) {
		g.fillRect(0, 0, SIZE, SIZE);
	}
}
