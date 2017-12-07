package spriteme;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import spritemanipulator.*;

public class IndexedSprite extends Component {
	private static final long serialVersionUID = -6792579285233025438L;

	private static final int IRASTERSIZE = SpriteManipulator.INDEXED_RASTER_SIZE;
	private static final Dimension d = new Dimension(200, 520);

	// local vars
	private ArrayList<SpritePart> parts = new ArrayList<SpritePart>();
	private byte[] raster;
	private byte[] rasterABGR;
	private Palette pal;
	private int mail;
	private BufferedImage[] sheets;

	// Specific body parts
	private final SpritePart body = SpritePart.BODY;
	private SpritePart hair = SpritePart.BALD;
	private SpritePart acc1;
	private SpritePart acc2;
	private SpritePart acc3;

	/**
	 * Creates a new editable sprite object connected to a palette.
	 * @param p - Palette
	 */
	public IndexedSprite(Palette p) {
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
	 * Checks if an accessory {@code SpritePart} is currently being used.
	 * @param acc
	 * @return Accessory # or 0 if unused/blank sheet
	 */
	public int checkAccessoryUse(SpritePart acc) {
		if (acc.isBlankSheet) {
			return 0;
		} else if (acc1 == acc) {
			return 1;
		} else if (acc2 == acc) {
			return 2;
		} else if (acc3 == acc) {
			return 3;
		} else {
			return 0;
		}
	}

	/**
	 * Set raster based on each parts' index mapping
	 */
	private void makeRaster() {
		byte[][] palette = pal.toArray();
		// make raster
		raster = new byte[IRASTERSIZE];

		// reset parts
		parts.clear();
		SpritePart[] allParts = new SpritePart[] {
				body, hair, acc1, acc2, acc3
		};
		for (int i = 0; i < allParts.length; i++) {
			SpritePart p = allParts[i];
			if (p != null && !p.isBlankSheet) {
				parts.add(p);
			}
		}

		// sort by z-index
		Collections.sort(parts);

		// create index wrapper
		// just paste over old values, nothing fancy
		for (SpritePart p : parts) {
			byte[] partRaster = p.getRaster();
			for (int i = 0; i < IRASTERSIZE; i++) {
				if (partRaster[i] != 0) {
					raster[i] = partRaster[i];
				}
			}
		}

		// unindex the raster
		rasterABGR = new byte[SpriteManipulator.ABGR_RASTER_SIZE];
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
	 * Remakes the raster and images
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
		int xOffset = 33;
		BufferedImage body;
		BufferedImage head;
		// draw 3 mails
		for (int i = 0; i < 3; i++) {
			cur = sheets[i];
			body = cur.getSubimage(48, 16, 16, 16);
			head = cur.getSubimage(16, 0, 16, 16);
			int h = i * 24;
			g2.drawImage(body, xOffset, h+8, null);
			g2.drawImage(head, xOffset, h+0, null);
		}
		// draw bunny
		int h = 3 * 24;
		cur = sheets[3];
		body = cur.getSubimage(0, 16 * 26, 16, 16);
		head = cur.getSubimage(16 * 5, 16 * 25, 16, 16);
		g2.drawImage(body, xOffset, h+8, null);
		g2.drawImage(head, xOffset, h+0, null);
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