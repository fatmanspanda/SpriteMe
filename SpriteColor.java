package SpriteMe;

import java.awt.Color;

import SpriteManipulator.SpriteManipulator;

public class SpriteColor {

	private String n;
	private byte[] RGB;
	private Color c;
	/**
	 * Makes a new color with a name and 3 RGB values.
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 */
	public SpriteColor(String name, byte r, byte g, byte b) {
		n = name;
		RGB = new byte[] { r, g, b };
		roundSelf();
		c = new Color(
					Byte.toUnsignedInt(RGB[0]),
					Byte.toUnsignedInt(RGB[1]),
					Byte.toUnsignedInt(RGB[2])
				);
	}

	/**
	 * Makes a new color with a name and 3 RGB values, as an array.
	 * @param name
	 * @param rgb
	 */
	public SpriteColor(String name, byte[] rgb) {
		this(name, (byte) rgb[0], (byte) rgb[1], (byte) rgb[2]);
	}

	/**
	 * Makes a new color with a name and 3 RGB values, but with integers to allow unsigned values.
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 */
	public SpriteColor(String name, int r, int g,  int b) {
		this(name, (byte) r, (byte) g, (byte) b );
	}

	/**
	 * 
	 * @return
	 */
	public Color toColor() {
		return c;
	}

	/**
	 * 
	 */
	private void roundSelf() {
		RGB[0] = (byte) SpriteManipulator.roundVal(RGB[0]);
		RGB[1] = (byte) SpriteManipulator.roundVal(RGB[1]);
		RGB[2] = (byte) SpriteManipulator.roundVal(RGB[2]);
	}

	/**
	 * 
	 */
	public String toString() {
		return n;
	}

	/**
	 * 
	 * @return
	 */
	public String toFullString() {
		return String.format("%s (RGB{%s,%s,%s})",
				n,
				Byte.toUnsignedInt(RGB[0]),
				Byte.toUnsignedInt(RGB[1]),
				Byte.toUnsignedInt(RGB[2]));
	}

	public byte[] getRGB() {
		return new byte[] { RGB[0], RGB[1], RGB[2] };
	}
	/**
	 * 
	 * @return
	 */
	public static SpriteColor makeDarker(String name, SpriteColor s) {
		byte[] RGB = darkerShade(s.getRGB());
		SpriteColor shade = new SpriteColor(name + " shaded",RGB);
		return shade;
	}

	/**
	 * 
	 * @param rgb
	 * @return
	 */
	public static byte[] darkerShade(byte[] rgb) {
		int r2 = Byte.toUnsignedInt(rgb[0]);
		int g2 = Byte.toUnsignedInt(rgb[1]);
		int b2 = Byte.toUnsignedInt(rgb[2]);
		r2 = r2 * 7 / 8;
		g2 = g2 * 7 / 8;
		b2 = b2 * 7 / 8;
		return new byte[] {
				(byte) r2,
				(byte) g2,
				(byte) b2
		};
	}

	/**
	 * 
	 * @param rgb
	 * @return
	 */
	public static byte[] lighterShade(byte[] rgb) {
		int r2 = Byte.toUnsignedInt(rgb[0]);
		int g2 = Byte.toUnsignedInt(rgb[1]);
		int b2 = Byte.toUnsignedInt(rgb[2]);
		r2 = r2 * 8 / 7;
		g2 = g2 * 8 / 7;
		b2 = b2 * 8 / 7;
		if (r2 >= 255) { r2 = 255; }
		if (g2 >= 255) { g2 = 255; }
		if (b2 >= 255) { b2 = 255; }
		return new byte[] {
				(byte) r2,
				(byte) g2,
				(byte) b2
		};
	}

	public ColorPair makeShadedPair() {
		return new ColorPair(n, this, makeDarker(n, this));
	}
	/*
	 * Some color constants
	 */

	// Skin colors
	public static final SpriteColor WHITE_SKIN_LT =
			new SpriteColor("White skin", 240, 160, 104);
	public static final SpriteColor LEIUTENANT_FACE = WHITE_SKIN_LT;
	public static final SpriteColor WHITE_SKIN_DK =
			new SpriteColor("White skin shade", 184, 104, 32);
	public static final SpriteColor PALE_SKIN =
			new SpriteColor("Pale skin", 248, 232, 168);
	public static final SpriteColor HISPANIC_SKIN =
			new SpriteColor("Hispanic skin", 216, 136, 80);
	public static final SpriteColor BROWN_SKIN =
			new SpriteColor("Brown skin", 168, 88, 24);
	public static final SpriteColor BLACK_SKIN =
			new SpriteColor("Black skin", 96, 48, 8);

	// THE BEST COLORS
	public static final SpriteColor RASPBERRY = // me
			new SpriteColor("Raspberry", 152, 0, 64);
	public static final SpriteColor MIKES_ORANGE =
			new SpriteColor("Mike's orange", 248, 176, 0);
	public static final SpriteColor NUMPTY_BLUE = // christos owen because he's cool
			new SpriteColor("Numpty blue", 56, 152, 216);
	public static final SpriteColor FEESH_PURPLE =
			new SpriteColor("Fish's purple", 176, 0, 216);
	public static final SpriteColor ZARBY_ORANGE =
			new SpriteColor("Zarby's orange", 216, 80, 24);
	public static final SpriteColor SOSUKE_BLACK =
			new SpriteColor("Sosuke's black", 16, 16, 16);
	public static final SpriteColor BAZLY_CORAL =
			new SpriteColor("Bazly's coral", 248, 128, 80);

	// A beautiful rainbow
	public static final SpriteColor RED =
			new SpriteColor("Red", 248, 0, 0);
	public static final SpriteColor ORANGE =
			new SpriteColor("Orange", 248, 128, 0);
	public static final SpriteColor YELLOW =
			new SpriteColor("Yellow", 248, 248, 0);
	public static final SpriteColor GREEN =
			new SpriteColor("Green", 0, 248, 0);
	public static final SpriteColor CYAN =
			new SpriteColor("Cyan", 0, 248, 248);
	public static final SpriteColor BLUE =
			new SpriteColor("Blue", 0, 0, 248);
	public static final SpriteColor INDIGO =
			new SpriteColor("Indigo", 128, 0, 248);

	// other colors
	public static final SpriteColor BLACK =
			new SpriteColor("Black", 72, 72, 72);
	public static final SpriteColor OFFWHITE =
			new SpriteColor("Off-white", 232, 232, 232);
	public static final SpriteColor BROWN =
			new SpriteColor("Brown", 128, 48, 0);
	public static final SpriteColor GRAY =
			new SpriteColor("Gray", 176, 176, 176);
	public static final SpriteColor BEIGE =
			new SpriteColor("Beige", 160, 160, 96);

	// List of useable constants for main colors
	public static final SpriteColor[] CONSTANTS = new SpriteColor[] {
			RED, ORANGE, YELLOW, GREEN, CYAN, INDIGO,
			BLACK, OFFWHITE, BROWN, GRAY, BEIGE,
			RASPBERRY, MIKES_ORANGE, NUMPTY_BLUE, FEESH_PURPLE,
			ZARBY_ORANGE, SOSUKE_BLACK, BAZLY_CORAL
	};
}