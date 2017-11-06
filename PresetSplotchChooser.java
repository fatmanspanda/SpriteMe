package SpriteMe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class PresetSplotchChooser extends JFrame {
	private static final long serialVersionUID = -7435126379055131184L;
	private static final Dimension D = new Dimension(300,300);
	private static final int SPLOTCH_SIZE = 30;
	private static final Dimension SPLOTCH_D = new Dimension(SPLOTCH_SIZE,SPLOTCH_SIZE);
	private static final SpriteColor[] CONSTANTS = SpriteColor.CONSTANTS;
	private final SplotchPreset[] COLORS;
	private SplotchEditor partner;
	private PresetSplotchChooser() {
		this.setSize(D);
		this.setPreferredSize(D);
		this.setMaximumSize(D);
		this.setMinimumSize(D);
		COLORS = new SplotchPreset[CONSTANTS.length+1];
		COLORS[0] = new SplotchPreset(SpriteColor.BEIGE, this);
		for (int i = 1; i < COLORS.length; i++) {
			COLORS[i] = new SplotchPreset(CONSTANTS[i-1], this);
		}
		initializeDisplay();
	};

	public void setPartner(SplotchEditor e) {
		partner = e;
		COLORS[0].setColor(e.getColor());
		repaint();
	}

	public boolean checkPartner(SplotchEditor test) {
		return partner == test;
	}

	public void setColor(SpriteColor c) {
		partner.setColor(c);
	}

	private void initializeDisplay() {
		Container wrap = this.getContentPane();
		this.setTitle("Choose a color");
		wrap.setSize(D);
		wrap.setPreferredSize(D);
		wrap.setMaximumSize(D);
		wrap.setMinimumSize(D);
		wrap.setLayout(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		int colorCount = 0;
		w.ipadx = 2;
		w.ipady = 2;
		fullFill :
		for (int i = 0; i < 7; i++, w.gridy++) {
			w.gridx = 0;
			for (int j = 0; j < 7; j++, w.gridx++, colorCount++) {
				if (colorCount == COLORS.length) {
					break fullFill;
				}
				wrap.add(COLORS[colorCount], w);
			}
		}
	}

	public static final PresetSplotchChooser PRESETS = new PresetSplotchChooser();

	/**
	 * Mini splotch presets
	 */
	@SuppressWarnings("serial")
	private static class SplotchPreset extends Component {
		private SpriteColor c;
		private final PresetSplotchChooser mommy;
		public SplotchPreset(SpriteColor c, PresetSplotchChooser parent) {
			setColor(c);
			mommy = parent;
			this.setSize(SPLOTCH_D);
			this.setPreferredSize(SPLOTCH_D);
			this.setMinimumSize(SPLOTCH_D);
			this.setMaximumSize(SPLOTCH_D);
			addMouse();
		}

		public void setColor(SpriteColor c) {
			this.c = c;
			this.setForeground(c.toColor());
			this.setBackground(c.toColor());
		}

		/**
		 * 
		 */
		public void paint(Graphics g) {
			g.fillRect(0, 0, SPLOTCH_SIZE, SPLOTCH_SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, SPLOTCH_SIZE, SPLOTCH_SIZE);
		}

		/**
		 * Used to add mouse listeners
		 */
		private void addMouse() {
			this.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent arg0) {
					mommy.setColor(c);
				}

				public void mousePressed(MouseEvent arg0) {
					mommy.setColor(c);
				}

				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			});
		} // end addMouse
	}
}
