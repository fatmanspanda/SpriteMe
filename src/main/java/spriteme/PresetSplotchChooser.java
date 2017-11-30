package spriteme;

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

	private static final Dimension D = new Dimension(270,250);
	private static final int SPLOTCH_SIZE = 30;
	private static final Dimension SPLOTCH_D = new Dimension(SPLOTCH_SIZE,SPLOTCH_SIZE);
	private static final int COLUMN_COUNT = 7;

	// locals
	private final PresetCategory[] presetList;
	private final JComboBox<PresetCategory> CATEGORY_CHOOSER;
	private final SpringLayout l = new SpringLayout();
	private PresetCategory curCat;
	private SplotchEditor partner;

	public PresetSplotchChooser(JFrame frame) {
		super(frame);
		setSize(D);
		setPreferredSize(D);
		setMaximumSize(D);
		setMinimumSize(D);
		setResizable(false);

		presetList = new PresetCategory[] {
				new PresetCategory(this, "Vanilla classics", SpriteColor.VANILLA_FAVORITES),
				new PresetCategory(this, "Dev favorites", SpriteColor.DEV_FAVORITES),
				new PresetCategory(this, "Bossy colors", SpriteColor.BOSS_COLORS),
				new PresetCategory(this, "Rainbow", SpriteColor.RAINBOW),
				new PresetCategory(this, "Other colors", SpriteColor.MISC)
		};

		curCat = presetList[0];
		CATEGORY_CHOOSER = new JComboBox<PresetCategory>(presetList);
		initializeDisplay();
	};

	public void setPartner(SplotchEditor e) {
		partner = e;
		revalidate();
		repaint();
	}

	public boolean checkPartner(SplotchEditor test) {
		return partner == test;
	}

	public void setColor(SpriteColor c) {
		partner.setColor(c);
		setVisible(false);
	}

	private final void initializeDisplay() {
		Container wrap = this.getContentPane();
		setTitle("Choose a color");

		wrap.setPreferredSize(D);
		wrap.setMaximumSize(D);
		wrap.setMinimumSize(D);
		wrap.setLayout(l);

		l.putConstraint(SpringLayout.NORTH, CATEGORY_CHOOSER, 2,
				SpringLayout.NORTH, wrap);
		l.putConstraint(SpringLayout.EAST, CATEGORY_CHOOSER, -4,
				SpringLayout.EAST, wrap);
		l.putConstraint(SpringLayout.WEST, CATEGORY_CHOOSER, 4,
				SpringLayout.WEST, wrap);
		wrap.add(CATEGORY_CHOOSER);

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

		revalidate();
	}

	/**
	 * Mini splotch presets
	 */
	@SuppressWarnings("serial")
	private class SplotchPreset extends JComponent {
		private SpriteColor c;
		private final PresetSplotchChooser mommy;
		private SplotchPreset(SpriteColor c, PresetSplotchChooser parent) {
			setSize(SPLOTCH_D);
			setPreferredSize(SPLOTCH_D);
			setMinimumSize(SPLOTCH_D);
			setMaximumSize(SPLOTCH_D);

			setColor(c);
			mommy = parent;
			setToolTipText(c.toFullString());
			addMouse();
		}

		public final void setColor(SpriteColor c) {
			this.c = c;
			setForeground(c.toColor());
			setBackground(c.toColor());
		}

		public void paint(Graphics g) {
			g.fillRect(0, 0, SPLOTCH_SIZE, SPLOTCH_SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, SPLOTCH_SIZE-1, SPLOTCH_SIZE-1);
		}

		/**
		 * Used to add mouse listeners
		 */
		private final void addMouse() {
			this.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
					mommy.setColor(c);
				}

				public void mousePressed(MouseEvent arg0) {
					mommy.setColor(c);
				}

				// unused
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
	private class PresetCategory extends Container {
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

		private final void initializeDisplay() {
			setLayout(new GridBagLayout());
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
			for (int i = 0; i < 99; i++, w.gridy++) { // height grows "without" bounds
				w.gridx = 0;
				for (int j = 0; j < COLUMN_COUNT; j++, w.gridx++, colorCount++) {
					if (colorCount == colorSet.length) {
						break fullFill;
					}
					this.add(colorSet[colorCount], w);
				}
			}
		}

		public String toString() {
			return name;
		}
	} // end PresetCategory
}