package SpriteMe;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import SpriteManipulator.*;

public class IndexedSprite extends Component {
	private static final long serialVersionUID = -6792579285233025438L;
	private static final int IRASTERSIZE = SpriteManipulator.INDEXED_RASTER_SIZE;

	private ArrayList<SpritePart> parts = new ArrayList<SpritePart>();
	private byte[] raster;
	private byte[] rasterABGR;
	private Palette pal;
	private int mail;
	private BufferedImage[] sheets;

	// Specific body parts
	private final SpritePart body = SpritePart.BODY;
	private SpritePart hair;
	private SpritePart acc1;
	private SpritePart acc2;
	private SpritePart acc3;
	private static final Dimension d = new Dimension(200, 448);

	/**
	 * Creates a new editable sprite object connected to a palette.
	 * @param p - Palette
	 */
	public IndexedSprite(Palette p) {
		parts.add(body);
		pal = p;
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setSize(d);
		setMail(0);
	}

	/**
	 * @param m - Palette level to use
	 */
	public void setMail(int m) {
		mail = m;
		fireSpriteChangeEvent();
	}

	/**
	 * Makes an ZSPR data file
	 */
	public ZSPRFile makeSprite() {
		makeRaster();
		byte[][][] ebe = SpriteManipulator.get8x8(raster);
		byte[] sprData = SpriteManipulator.export8x8ToSPR(ebe);
		int[] RGB9 = pal.toRGB9Array();
		byte[] palData = SpriteManipulator.getPalDataFromArray(RGB9);
		byte[] glovesData = SpriteManipulator.getGlovesDataFromArray(RGB9);
		ZSPRFile mySprite = new ZSPRFile(sprData, palData, glovesData);
		return mySprite;
	}

	/**
	 * @param hairChoice - hair object
	 */
	public void setHair(SpritePart hairChoice) {
		hair = hairChoice;
		fireSpriteChangeEvent();
	}

	/**
	 * @param acc - accessory object
	 * @param x - accessory spot
	 */
	public void setAccessory(SpritePart acc, int x) {
		switch (x) {
			case 1 : acc1 = acc;
				break;
			case 2 : acc2 = acc;
				break;
			case 3 : acc3 = acc;
				break;
		}
		fireSpriteChangeEvent();
	}

	/**
	 * Set raster based on each parts' index mapping
	 */
	private void makeRaster() {
		byte[][] palette = pal.toArray();
		// make raster
		raster = new byte[IRASTERSIZE];
		// sort by z-index
		parts.clear();
		parts.add(body);
		parts.add(acc1);
		for (Object e : parts) {
			if (e == null) {
				parts.remove(e);
			}
		}
		Collections.sort(parts);

		// create index wrapper
		// just paste over old values, nothing fancy
		for (SpritePart p : parts) {
			if (p != null) {
				byte[] partRaster = p.getRaster();
				for (int i = 0; i < IRASTERSIZE; i++) {
					if (partRaster[i] != 0) {
						raster[i] = partRaster[i];
					}
				}
			}
		}

		// unindex the raster
		rasterABGR = new byte[SpriteManipulator.RASTER_SIZE];
		for (int i = 0; i < IRASTERSIZE; i++) {
			int pos = 4 * i;
			int index = raster[i];
			byte a;
			// hide trans pixels
			if (index == 0) {
				a = (byte) 0;
			} else {
				a = (byte) 255;
			}
			rasterABGR[pos + 0] = a; // alpha
			rasterABGR[pos + 1] = palette[index][2]; // blue
			rasterABGR[pos + 2] = palette[index][1]; // green
			rasterABGR[pos + 3] = palette[index][0]; // red
		}
	} // end make raster

	/**
	 * Remakes the raster and images.
	 */
	private void refreshImage() {
		makeRaster();
		byte[][][] ebe = SpriteManipulator.indexAnd8x8(rasterABGR, pal.toRGB9Array());
		sheets = SpriteManipulator.makeAllMails(ebe, pal.toArray());
	}

	/**
	 * 
	 */
	public void paint(Graphics g) {
		refreshImage();
		// draw main sheet
		BufferedImage cur = sheets[mail];
		g.drawImage(cur, 0, 0, null);

		// draw big preview
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(4, 4);
		BufferedImage body = cur.getSubimage(48, 16, 16, 16);
		BufferedImage head = cur.getSubimage(16, 0, 16, 16);
		g2.drawImage(body, 33, 8, null);
		g2.drawImage(head, 33, 0, null);
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