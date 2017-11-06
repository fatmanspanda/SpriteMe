package SpriteMe;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class PresetSplotchChooser extends JDialog {
	private static final long serialVersionUID = -7435126379055131184L;
	private static final Dimension D = new Dimension(270,400);
	private static final int SPLOTCH_SIZE = 30;
	private static final Dimension SPLOTCH_D = new Dimension(SPLOTCH_SIZE,SPLOTCH_SIZE);
	private static final int COLUMN_COUNT = 7;

	// locals
	private final PresetCategory[] presetList;
	private final SplotchPreset ORIGINAL;
	private final JComboBox<PresetCategory> CATEGORY_CHOOSER;
	private PresetCategory curCat;
	private SplotchEditor partner;
	SpringLayout l = new SpringLayout();
	public PresetSplotchChooser(JFrame frame) {
		super(frame);
		this.setSize(D);
		this.setPreferredSize(D);
		this.setMaximumSize(D);
		this.setMinimumSize(D);
		this.setResizable(false);
		ORIGINAL = new SplotchPreset(SpriteColor.GRAY, this);
		presetList = new PresetCategory[] {
				new PresetCategory(this, "Vanilla Classics", SpriteColor.VANILLA_FAVORITES),
				new PresetCategory(this, "Dev favorites", SpriteColor.DEV_FAVORITES),
				new PresetCategory(this, "Rainbow", SpriteColor.RAINBOW)
		};
		curCat = presetList[0];
		CATEGORY_CHOOSER = new JComboBox<PresetCategory>(presetList);
		initializeDisplay();
	};

	public void setPartner(SplotchEditor e) {
		partner = e;
		ORIGINAL.setColor(e.getColor());
		this.revalidate();
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
		wrap.setLayout(l);
		JLabel origLbl = new JLabel("Original color", SwingConstants.RIGHT);
		l.putConstraint(SpringLayout.NORTH, CATEGORY_CHOOSER, 2,
				SpringLayout.NORTH, wrap);
		l.putConstraint(SpringLayout.EAST, CATEGORY_CHOOSER, -4,
				SpringLayout.EAST, wrap);
		l.putConstraint(SpringLayout.WEST, CATEGORY_CHOOSER, 4,
				SpringLayout.WEST, wrap);
		wrap.add(CATEGORY_CHOOSER);

		l.putConstraint(SpringLayout.SOUTH, ORIGINAL, -2,
				SpringLayout.SOUTH, wrap);
		l.putConstraint(SpringLayout.EAST, ORIGINAL, -4,
				SpringLayout.EAST, wrap);
		wrap.add(ORIGINAL);

		l.putConstraint(SpringLayout.SOUTH, origLbl, 0,
				SpringLayout.SOUTH, ORIGINAL);		
		l.putConstraint(SpringLayout.NORTH, origLbl, 0,
				SpringLayout.NORTH, ORIGINAL);
		l.putConstraint(SpringLayout.EAST, origLbl, -4,
				SpringLayout.WEST, ORIGINAL);
		wrap.add(origLbl);

		l.putConstraint(SpringLayout.NORTH, curCat, 2,
				SpringLayout.SOUTH, CATEGORY_CHOOSER);
		l.putConstraint(SpringLayout.EAST, curCat, -4,
				SpringLayout.EAST, wrap);
		l.putConstraint(SpringLayout.WEST, curCat, 4,
				SpringLayout.WEST, wrap);
		wrap.add(curCat);

		CATEGORY_CHOOSER.addItemListener(
				arg0 -> {
					wrap.remove(curCat);
					curCat = (PresetCategory) CATEGORY_CHOOSER.getSelectedItem();
					l.putConstraint(SpringLayout.NORTH, curCat, 2,
							SpringLayout.SOUTH, CATEGORY_CHOOSER);
					l.putConstraint(SpringLayout.EAST, curCat, -4,
							SpringLayout.EAST, wrap);
					l.putConstraint(SpringLayout.WEST, curCat, 4,
							SpringLayout.WEST, wrap);
					wrap.add(curCat);
					PresetSplotchChooser.this.revalidate();
					PresetSplotchChooser.this.repaint();
				});
		this.revalidate();
	}

	/**
	 * Mini splotch presets
	 */
	@SuppressWarnings("serial")
	private static class SplotchPreset extends JComponent {
		private SpriteColor c;
		private final PresetSplotchChooser mommy;
		private SplotchPreset(SpriteColor c, PresetSplotchChooser parent) {
			setColor(c);
			mommy = parent;
			this.setSize(SPLOTCH_D);
			this.setPreferredSize(SPLOTCH_D);
			this.setMinimumSize(SPLOTCH_D);
			this.setMaximumSize(SPLOTCH_D);
			this.setToolTipText(c.toFullString());
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
			g.drawRect(0, 0, SPLOTCH_SIZE-1, SPLOTCH_SIZE-1);
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
	} // end SplotchPreset

	/**
	 * For grouping colors into categories
	 */
	@SuppressWarnings("serial")
	private static class PresetCategory extends Container {
		// locals
		private final PresetSplotchChooser mommy;
		private final SplotchPreset[] colorSet;
		private final SpriteColor[] colorList;
		private final String name;

		private PresetCategory(PresetSplotchChooser parent, String name, SpriteColor[] list) {
			colorSet = new SplotchPreset[list.length];
			colorList = list;
			this.name = name;
			mommy = parent;
			for (int i = 0; i < colorSet.length; i++) {
				colorSet[i] = new SplotchPreset(colorList[i], mommy);
			}
			initializeDisplay();
		}

		private void initializeDisplay() {
			this.setLayout(new GridBagLayout());
			GridBagConstraints w = new GridBagConstraints();
			int colorCount = 0;
			w.gridy = 0;
			w.gridx = 0;
			w.gridwidth = COLUMN_COUNT;
			this.add(new JLabel(name, SwingConstants.CENTER), w);
			w.gridwidth = 1;
			w.gridy = 1;
			w.ipady = 2;
			w.ipadx = 2;
			fullFill :
			for (int i = 0; i < 99; i++, w.gridy++) { // height grows without bounds
				w.gridx = 0;
				for (int j = 0; j < COLUMN_COUNT; j++, w.gridx++, colorCount++) {
					if (colorCount == colorSet.length) {
						break fullFill;
					}
					this.add(colorSet[colorCount], w);
				}
			}
			this.revalidate();
		}

		public String toString() {
			return name;
		}
	} // end PresetCategory
}
