package SpriteMe;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class SpritePartEditor extends JFrame {
	private static final long serialVersionUID = 3880283257828608241L;

	private byte[] originalColorMap = {};
	private byte[] colorMap = {};
	private Palette pal;
	private SpritePart curPart;
	private int colors = 0;

	public SpritePartEditor(Palette p) {
		super();
		pal = p;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			revertAllChanges();
			}
		});
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
	}
	
	public void setPaletteColor(int palX, byte palI) {
		curPart.remapColor(palX, palI);
	}
	/**
	 * 
	 */
	private void revertAllChanges() {
		for (int i = 0; i < colors; i++) {
			colorMap[i] = originalColorMap[i];
		}
	}
}
