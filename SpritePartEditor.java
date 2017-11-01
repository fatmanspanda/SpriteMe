package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class SpritePartEditor extends Container {
	private static final long serialVersionUID = 3880283257828608241L;

	private byte[] originalColorMap = {};
	private byte[] colorMap = {};
	private Palette pal;
	private SpritePart curPart;
	private int colors = 0;
	private MiniPalette[] palettes;
	private JPanel paletteArea;
	private static final Dimension prefDim = new Dimension(350,200);

	public SpritePartEditor(Palette p) {
		super();
		pal = p;
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
		editNewPart(SpritePart.TEST);
		initializeDisplay();
	}

	/**
	 * 
	 * @param p
	 */
	public void editNewPart(SpritePart p) {
		curPart = p;
		colors = p.colorCount();
		originalColorMap = new byte[colors];
		colorMap = new byte[colors];
		for (int i = 0; i < colors; i++) {
			originalColorMap[i] = p.colorIndex(i);
			colorMap[i] = p.colorIndex(i);
		}

		setPalette();
	}

	/**
	 * 
	 */
	public void setPalette() {
		palettes = new MiniPalette[colors];
		for (int i = 0; i < colors; i++) {
			palettes[i] = new MiniPalette(this, pal, i);
			palettes[i].setIndex(curPart.colorIndex(i));
		}
	}

	/**
	 * 
	 */
	public void initializeDisplay() {
		// TODO : label for part name
		final Container fullWrap = this;
		SpringLayout l = new SpringLayout();
		this.setLayout(l);

		paletteArea = new JPanel(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();
		for (int i = 0; i < colors; i++) {
			w.gridy = i;
			w.gridx = 0;
			paletteArea.add(new JLabel(curPart.colorName(i)), w);
			w.gridx = 1;
			paletteArea.add(palettes[i], w);
		}
		// add palette area
		l.putConstraint(SpringLayout.EAST, paletteArea, 0,
				SpringLayout.EAST, fullWrap);
		l.putConstraint(SpringLayout.SOUTH, paletteArea, 0,
				SpringLayout.SOUTH, fullWrap);
		this.add(paletteArea);
	}

	/**
	 * 
	 * @param palX
	 * @param palI
	 */
	public void setPaletteColor(int palX, byte palI) {
		curPart.remapColor(palX, palI);
		fireSpriteChangeEvent();
	}

	/**
	 * 
	 */
	public void refreshPalette() {
		for (MiniPalette m : palettes) {
			m.refreshPalette();
		}
	}
	/**
	 * 
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
