package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import SpriteAnimator.GUIHelpers;

// TODO: Revert all changes
public class SpritePartEditor extends Container {
	// class constants
	private static final long serialVersionUID = 3880283257828608241L;
	private static final Border rightPad = BorderFactory.createEmptyBorder(0,0,0,5);
	private static final Dimension prefDim = new Dimension(400,250);
	private static String[] INSTRUCTION_STYLE = {
			"padding: 10px 10px 10px 0px",
			"width: 230px"
	};

	// local vars
	private byte[] originalColorMap = {};
	private byte[] colorMap = {};
	private Palette pal;
	private SpritePart curPart;
	private int colors = 0;
	private MiniPalette[] palettes;
	private JPanel paletteArea;
	private SpringLayout l = new SpringLayout();
	private final JLabel partLbl = new JLabel("Nothing");

	/**
	 * Creates a new {@code SpritePartEditor}
	 * attached to a {@link Palette} for communication.
	 * @param p
	 */
	public SpritePartEditor(Palette p) {
		super();
		pal = p;
		this.setLayout(l);
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
		setPalette();
		newPaletteSet();
		editNewPart(null);
		initializeDisplay();
	}

	/**
	 * Attaches a new {@code SpritePart} to edit.
	 * @param p
	 */
	public void editNewPart(SpritePart p) {
		if (p == null) { // remove null parts
			partLbl.setText("Nothing");
			this.remove(paletteArea);
			this.revalidate();
			this.repaint();
			return;
		}
		curPart = p;
		colors = p.colorCount();
		originalColorMap = new byte[colors];
		colorMap = new byte[colors];
		for (int i = 0; i < colors; i++) {
			originalColorMap[i] = p.colorIndex(i);
			colorMap[i] = p.colorIndex(i);
		}
		partLbl.setText(p.toString());
		setPalette();
		newPaletteSet();
	}

	/**
	 * Sets a new array of {@link MiniPalette} objects to be used
	 * to remap {@code SpritePart} indices.
	 */
	public void setPalette() {
		palettes = new MiniPalette[colors];
		for (int i = 0; i < colors; i++) {
			palettes[i] = new MiniPalette(this, pal, i);
			palettes[i].setIndex(curPart.colorIndex(i));
		}
	}

	/**
	 * Sets up GUI.
	 */
	public void initializeDisplay() {
		final JLabel editingPart = new JLabel("Editing map for : ");
		l.putConstraint(SpringLayout.EAST, editingPart, 0,
				SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.SOUTH, editingPart, 0,
				SpringLayout.VERTICAL_CENTER, this);
		this.add(editingPart);
		
		l.putConstraint(SpringLayout.WEST, partLbl, 0,
				SpringLayout.EAST, editingPart);
		l.putConstraint(SpringLayout.SOUTH, partLbl, 0,
				SpringLayout.VERTICAL_CENTER, this);
		this.add(partLbl);
		
		final JLabel helpText = new JLabel();

		helpText.setText("<html>" +
				"<div style=\"" + GUIHelpers.join(INSTRUCTION_STYLE, ";") + "\">" +
				"Use this area to pick the colors of each part of an item. " +
				"<br /><br />" +
				"Note that anything mapped to index 13 may change when gloves or mitts are obtained." +
				"<br />" +
				"Anything mapped to index 0 will be fully transparent." +
				"</div>" +
				"</html>");
//		l.putConstraint(SpringLayout.WEST, helpText, 0,
//				SpringLayout.WEST, this);
		l.putConstraint(SpringLayout.EAST, helpText, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.NORTH, helpText, 0,
				SpringLayout.NORTH, this);
		this.add(helpText);
	
		newPaletteSet();
	} // end display initialization

	/**
	 * Creates a new palette area in the GUI for user control.
	 */
	private void newPaletteSet() {
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
			JLabel partColorName = new JLabel(curPart.colorName(i), SwingConstants.RIGHT);
			partColorName.setBorder(rightPad);
			paletteArea.add(partColorName, w);
		}

		// add palette area
		l.putConstraint(SpringLayout.EAST, paletteArea, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.NORTH, paletteArea, 0,
				SpringLayout.VERTICAL_CENTER, this);
		this.add(paletteArea);
		this.revalidate();
	}

	/**
	 * Remaps a {@code SpritePart} color to a color in the main palette.
	 * @param palX
	 * @param palI
	 */
	public void setPaletteColor(int palX, byte palI) {
		curPart.remapColor(palX, palI);
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

	/**
	 * Reverts all remappings to the default indices of the object.
	 */
	private void revertAllChanges() {
		for (int i = 0; i < colors; i++) {
			colorMap[i] = originalColorMap[i];
		}
		fireSpriteChangeEvent();
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