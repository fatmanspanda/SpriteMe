package SpriteMe;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import SpriteManipulator.SpriteManipulator;

public class IndexedSprite extends Component {
	private static final long serialVersionUID = -6792579285233025438L;
	private static final int IRASTERSIZE = SpriteManipulator.INDEXEDRASTERSIZE;

	private ArrayList<SpritePart> parts = new ArrayList<SpritePart>();
	private byte[] raster;
	private byte[] rasterABGR;
	private Palette pal;
	private int mail;
	private BufferedImage[] sheets;
	public IndexedSprite(Palette p) {
		parts.add(SpritePart.TEST); // Add body TODO: actual body
		pal = p;
		this.setPreferredSize(new Dimension(200, 448));
		mail = 1;
	}
	
	public void setMail(int m) {
		mail = m;
	}
	public void makeSprite() {
		
	}

	private void makeRaster() {
		byte[][] palette = pal.toArray();
		// make raster
		raster = new byte[IRASTERSIZE];
		// sort by z-index
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
}