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

import SpriteManipulator.SpriteManipulator;
import SpriteMe.Listeners.*;

public class IndexedSprite extends Component {
	private static final long serialVersionUID = -6792579285233025438L;
	private static final int IRASTERSIZE = SpriteManipulator.INDEXEDRASTERSIZE;

	private ArrayList<SpritePart> parts = new ArrayList<SpritePart>();
	private byte[] raster;
	private byte[] rasterABGR;
	private Palette pal;
	private int mail;
	private BufferedImage[] sheets;
	// Specific body parts
	private SpritePart head = SpritePart.TEST;
	private SpritePart body;
	private SpritePart hair;
	private SpritePart acc1;
	private SpritePart acc2;
	private SpritePart acc3;
	private static final Dimension d = new Dimension(200, 448);
	public IndexedSprite(Palette p) {
		parts.add(head);
		pal = p;
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setSize(d);
		setMail(0);
	}

	/**
	 * 
	 * @param m
	 */
	public void setMail(int m) {
		mail = m;
		fireSpriteChangeEvent();
	}

	/**
	 * 
	 */
	public void makeSprite() {

	}

	/**
	 * 
	 * @param hairChoice
	 */
	public void setHair(SpritePart hairChoice) {
		hair = hairChoice;
		fireSpriteChangeEvent();
	}

	/**
	 * 
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
	 * 
	 */
	private void makeRaster() {
		byte[][] palette = pal.toArray();
		// make raster
		raster = new byte[IRASTERSIZE];
		// sort by z-index
		parts.clear();
		parts.add(head);
		parts.add(acc1);
		for (Object e : parts.toArray()) {
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
		rasterABGR = new byte[SpriteManipulator.RASTERSIZE];
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

	private void refreshImage() {
		makeRaster();
		byte[][][] ebe = SpriteManipulator.get8x8(rasterABGR, pal.toRGB9Array());
		sheets = SpriteManipulator.makeAllMails(ebe, pal.toArray());
	}

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