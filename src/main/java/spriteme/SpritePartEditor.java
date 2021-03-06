package spriteme;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import static javax.swing.SpringLayout.*;

public class SpritePartEditor extends Container {
	// class constants
	private static final long serialVersionUID = 3880283257828608241L;
	private static final Border rightPad = BorderFactory.createEmptyBorder(0,0,0,5);
	private static final Dimension prefDim = new Dimension(400,20);

	// local vars
	private byte[] colorMap = {};
	private Palette pal;
	private final String partName;
	private SpritePart curPart;
	private int colors = 0;
	private MiniPalette[] palettes;
	private JPanel paletteArea;
	private final Picker pick;
	private final Picker unpick;
	private SpringLayout l = new SpringLayout();
	private final JComboBox<SpritePart> selector;

	/**
	 * Creates a new {@code SpritePartEditor}.
	 * attached to a {@link Palette} and {@link JComboBox} for communication.
	 * @param name - Name of section edited
	 * @param p - palette to communicate with
	 * @param selector - {@code JComboBox} that chooses this part.
	 * @param pick - a defined {@link Picker} interface containing the function that
	 * controls what happens when the combobox is changed
	 * @param unpick - optional {@link Picker} for controls related to other SpritePartEditors
	 */
	public SpritePartEditor(String name, Palette p,
			JComboBox<SpritePart> selector, Picker pick, Picker unpick) {
		super();
		pal = p;
		this.setLayout(l);
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);

		setPalette();
		newPaletteSet();

		this.selector = selector;
		this.pick = pick;
		this.unpick = unpick;

		partName = name;
		editNewPart(null);
		initializeDisplay();
	}

	/**
	 * Attaches a new {@code SpritePart} to edit.
	 * @param p
	 */
	public final void editNewPart(SpritePart p) {
		if (p == null || p.isBlankSheet) { // remove null and empty parts
			this.remove(paletteArea);
			this.setSize(prefDim);
			this.setPreferredSize(prefDim);
			this.revalidate();
			this.repaint();
			return;
		}
		curPart = p;
		colors = p.remappableColorCount();
		colorMap = new byte[colors];
		for (int i = 0; i < colors; i++) {
			colorMap[i] = p.getNthRemappableIndex(i);
		}
		setPalette();
		newPaletteSet();
		Dimension f = new Dimension(400, colors * SpriteMeGUI.SPLOTCH_SIZE + 40);
		this.setSize(f);
		this.setPreferredSize(f);
	}

	/**
	 * Removes remapper for current part.
	 */
	public void clearPart() {
		editNewPart(null);
	}

	/**
	 * Sets a new array of {@link MiniPalette} objects to be used
	 * to remap {@code SpritePart} indices.
	 */
	public final void setPalette() {
		palettes = new MiniPalette[colors];
		for (int i = 0; i < colors; i++) {
			palettes[i] = new MiniPalette(this, pal, i);
			palettes[i].setIndex(curPart.getNthRemappableIndex(i));
		}
	}

	/**
	 * Sets up GUI
	 */
	public final void initializeDisplay() {
		final JLabel nameLbl = new JLabel(partName + ":");
		l.putConstraint(EAST, nameLbl, -6, WEST, selector);
		l.putConstraint(VERTICAL_CENTER, nameLbl, 0, VERTICAL_CENTER, selector);
		this.add(nameLbl);

		l.putConstraint(EAST, selector, 0, EAST, this);
		l.putConstraint(NORTH, selector, 0, NORTH, this);
		this.add(selector);

		selector.addItemListener(
			arg0 -> {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					SpritePart picked = (SpritePart) selector.getSelectedItem();
					if (unpick != null) {
						unpick.pickThis(picked);
					}
					pick.pickThis(picked);
					this.editNewPart(picked);
					fireSpriteChangeEvent();
				}
			});
		newPaletteSet();
	} // end display initialization

	/**
	 * Creates a new palette area in the GUI for user control.
	 */
	private final void newPaletteSet() {
		if (paletteArea != null) {
				this.remove(paletteArea);
		}

		paletteArea = new JPanel(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		w.fill = GridBagConstraints.HORIZONTAL;
		w.gridy = 0;
		w.gridx = 0;

		if (colors > 0) {
			JLabel indexWord = new JLabel("Index", SwingConstants.RIGHT);
			indexWord.setBorder(rightPad);
			paletteArea.add(indexWord, w);
		}

		w.gridheight = 2;
		for (int i = 0; i < colors; i++, w.gridy++) {
			w.gridx = 1;
			paletteArea.add(palettes[i], w);
			if (i == 0) { // extra jump for the next part
				w.gridy++;
				w.gridheight = 1;
			}
			w.gridx = 0;

			JLabel partColorName = new JLabel(curPart.getNthRemappableColorName(i), SwingConstants.RIGHT);
			partColorName.setBorder(rightPad);
			paletteArea.add(partColorName, w);
		}

		// add palette area
		l.putConstraint(EAST, paletteArea, 0, EAST, this);
		l.putConstraint(NORTH, paletteArea, 0, SOUTH, selector);
		this.add(paletteArea);

		this.revalidate();
	}

	/**
	 * Remaps a {@code SpritePart} color to a color in the main palette.
	 * @param palX
	 * @param palI
	 */
	public void setPaletteColor(int palX, byte palI) {
		curPart.remapNTHRemappableColor(palX, palI);
		fireSpriteChangeEvent();
	}

	/**
	 * Refreshes each individual {@code MiniPalette}.
	 */
	public void refreshPalette() {
		try {
			for (MiniPalette m : palettes) {
				m.refreshPalette();
			}
		} catch (Exception e) {}
	}

	/*
	 * Change listeners
	 */
	private List<SpriteChangeListener> spriteListeners = new ArrayList<SpriteChangeListener>();

	public synchronized void addSpriteChangeListener(SpriteChangeListener s) {
		spriteListeners.add(s);
	}

	public synchronized void removeSpriteChangeListener(SpriteChangeListener s) {
		spriteListeners.remove(s);
	}

	private synchronized void fireSpriteChangeEvent() {
		SpriteChangeEvent s = new SpriteChangeEvent(this);
		Iterator<SpriteChangeListener> listening = spriteListeners.iterator();
		while(listening.hasNext()) {
			(listening.next()).eventReceived(s);
		}
	}
}