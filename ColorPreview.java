package SpriteMe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public class ColorPreview extends Component {
	private static final long serialVersionUID = -6583423770149289548L;
	public static final int SIZE = 40;
	private static final Dimension D = new Dimension(SIZE,SIZE);
	private Color c = new Color(0,0,0);
	public ColorPreview() {
		this.setSize(D);
		this.setPreferredSize(D);
		this.setMinimumSize(D);
		this.setMaximumSize(D);
	}

	public void setColor(int[] rgb) {
		c = new Color(rgb[0], rgb[1], rgb[2]);
	}

	public void paint(Graphics g) {
		g.setColor(c);
		g.fillRect(0, 0, SIZE, SIZE);
	}
}