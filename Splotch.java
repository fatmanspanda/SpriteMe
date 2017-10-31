package SpriteMe;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Splotch extends JComponent {

	private static final long serialVersionUID = -2349012926208164404L;
	private SpriteColor color;
	private static final int SIZE = SpriteMe.SPLOTCH_SIZE;
	public Splotch(SpriteColor c) {
		color = c;
		this.setForeground(color.toColor());
		this.setBackground(color.toColor());
		this.setSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setMinimumSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setPreferredSize(SpriteMe.SPLOTCH_DIMENSION);
		this.setToolTipText(color.toString());
		addMouse();
	}
	
	public void paint(Graphics g) {
		g.fillRect(0, 0, SIZE, SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, SIZE, SIZE);
	}
	
	private void addMouse() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}});
	}
}
