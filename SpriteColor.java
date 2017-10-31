package SpriteMe;

import SpriteManipulator.SpriteManipulator;

public class SpriteColor {
	
	private String n;
	private byte[] RGB;
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
	
	private void roundSelf() {
		RGB[0] = (byte) SpriteManipulator.roundVal(RGB[0]);
		RGB[1] = (byte) SpriteManipulator.roundVal(RGB[1]);
		RGB[2] = (byte) SpriteManipulator.roundVal(RGB[1]);
	}
	
	public String toString() {
		return n;
	}
	
	// TODO : Some formula that works well
	public ColorPair makeShadedPair() {
		int r2 = Byte.toUnsignedInt(RGB[0]);
		int g2 = Byte.toUnsignedInt(RGB[1]);
		int b2 = Byte.toUnsignedInt(RGB[2]);
		
		SpriteColor shade = new SpriteColor(n + "shaded",r2, g2, b2);
		return new ColorPair(n, this, shade);
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
	public static final SpriteColor PALE_SKIN_LT =
			new SpriteColor("Pale skin", 248, 232, 168);
	public static final SpriteColor PALE_SKIN_DK =
			new SpriteColor("Pale skin shade", 224, 208, 152);
	public static final SpriteColor HISPANIC_SKIN_LT =
			new SpriteColor("Hispanic skin", 216, 136, 80);
	public static final SpriteColor HISPANIC_SKIN_DK =
			new SpriteColor("Hispanic skin shade", 216, 136, 80);
	public static final SpriteColor BROWN_SKIN_LT =
			new SpriteColor("Hispanic skin", 168, 88, 24);
	public static final SpriteColor BROWN_SKIN_DK =
			new SpriteColor("Hispanic skin shade", 152, 72, 16);
	public static final SpriteColor BLACK_SKIN_LT =
			new SpriteColor("Hispanic skin", 96, 48, 8);
	public static final SpriteColor BLACK_SKIN_DK =
			new SpriteColor("Hispanic skin shade", 80, 32, 8);
	
	// THE BEST COLORS
	public static final SpriteColor RASPBERRY =
			new SpriteColor("Raspberry", 152, 0, 64);
	public static final SpriteColor RASPBERRY_DK =
			new SpriteColor("Raspberry shade", 112, 0, 56);
	public static final SpriteColor MIKES_ORANGE =
			new SpriteColor("Mike's orange", 248, 176, 0);

	// A beautiful rainbow
	public static final SpriteColor BRIGHT_RED =
			new SpriteColor("Bright red", 248, 0, 0);
	public static final SpriteColor BRIGHT_ORANGE =
			new SpriteColor("Bright orange", 248, 128, 0);
	public static final SpriteColor BRIGHT_YELLOW =
			new SpriteColor("Bright yellow", 248, 248, 0);
	public static final SpriteColor BRIGHT_GREEN =
			new SpriteColor("Bright green", 0, 248, 0);
	public static final SpriteColor BRIGHT_CYAN =
			new SpriteColor("Bright cyan", 0, 248, 248);
	public static final SpriteColor BRIGHT_BLUE =
			new SpriteColor("Bright blue", 0, 0, 248);
	public static final SpriteColor BRIGHT_PURPLE =
			new SpriteColor("Bright indigo", 128, 0, 248);
	
	// other colors
	public static final SpriteColor BLACK =
			new SpriteColor("Black", 72, 72, 72);
	public static final SpriteColor OFFWHITE =
			new SpriteColor("Off-white", 232, 232, 232);
}