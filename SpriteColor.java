package SpriteMe;

import java.awt.Color;

import SpriteManipulator.SpriteManipulator;

public class SpriteColor {
	private String n;
	private byte[] RGB;
	private Color c;

	/**
	 * Makes a new {@code SpriteColor} with a name and 3 RGB values.
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
	 * Makes a new {@code SpriteColor} with a name and 3 RGB values, as an array.
	 * @param name
	 * @param rgb
	 */
	public SpriteColor(String name, byte[] rgb) {
		this(name, (byte) rgb[0], (byte) rgb[1], (byte) rgb[2]);
	}

	/**
	 * Makes a new {@code SpriteColor} with a name and 3 RGB values, but with integers to allow unsigned values.
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 */
	public SpriteColor(String name, int r, int g,  int b) {
		this(name, (byte) r, (byte) g, (byte) b );
	}

	/**
	 * @return {@link Color} object associated with this SpriteColor.
	 */
	public Color toColor() {
		return c;
	}

	/**
	 * Rounds each value down to the nearest multiple of 8.
	 */
	private void roundSelf() {
		RGB[0] = (byte) SpriteManipulator.roundVal(RGB[0]);
		RGB[1] = (byte) SpriteManipulator.roundVal(RGB[1]);
		RGB[2] = (byte) SpriteManipulator.roundVal(RGB[2]);
	}

	/**
	 * @return The {@code SpriteColor}'s name.
	 */
	public String toString() {
		return n;
	}

	/**
	 * @return The {@code SpriteColor}'s name and RGB coordinates.
	 */
	public String toFullString() {
		return String.format("%s (RGB{%s,%s,%s})",
				n,
				Byte.toUnsignedInt(RGB[0]),
				Byte.toUnsignedInt(RGB[1]),
				Byte.toUnsignedInt(RGB[2]));
	}

	/**
	 * @return Color's RGB values in a byte array.
	 */
	public byte[] getRGB() {
		return new byte[] { RGB[0], RGB[1], RGB[2] };
	}

	/**
	 * @param name
	 * @param s - Lighter {@code SpriteColor}
	 * @return A new {@code SpriteColor} with a darker shade and named "{@code n < shaded>}".
	 */
	public static SpriteColor makeDarker(String name, SpriteColor s) {
		byte[] RGB = darkerShade(s.getRGB());
		SpriteColor shade = new SpriteColor(name + " shaded",RGB);
		return shade;
	}

	/**
	 * @param rgb
	 * @return A {@code byte} array for a darker shade of the color array passed.
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
	 * @param rgb
	 * @return A {@code byte} array for a lighter shade of the color array passed.
	 */
	public static byte[] lighterShade(byte[] rgb) {
		int r2 = Byte.toUnsignedInt(rgb[0]);
		int g2 = Byte.toUnsignedInt(rgb[1]);
		int b2 = Byte.toUnsignedInt(rgb[2]);
		r2 = r2 * 8 / 7;
		g2 = g2 * 8 / 7;
		b2 = b2 * 8 / 7;
		if (r2 > 248) { r2 = 248; }
		if (g2 > 248) { g2 = 248; }
		if (b2 > 248) { b2 = 248; }
		return new byte[] {
				(byte) r2,
				(byte) g2,
				(byte) b2
		};
	}

	/**
	 * @return A {@link ColorPair} consisting of this color and a darker shade.
	 */
	public ColorPair makeShadedPair() {
		return new ColorPair(n, this, makeDarker(n, this));
	}

	/*
	 * Color constants
	 */

	/*
	 * Skin colors
	 */
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

	/*
	 * BEST COLORS
	 * Added for people that have been generally helpful or are just cool
	 */
	public static final SpriteColor[] DEV_FAVORITES = new SpriteColor[] {
			new SpriteColor("Panda's raspberry", 152, 0, 64), // me
			new SpriteColor("Mike's orange", 248, 176, 0),
			new SpriteColor("Numpty blue", 56, 152, 216), // christos owen because he's cool
			new SpriteColor("Fish's purple", 176, 0, 216),
			new SpriteColor("Zarby's orange", 216, 80, 24),
			new SpriteColor("Sosuke's black", 16, 16, 16),
			new SpriteColor("Bazly's coral", 248, 128, 80),
			new SpriteColor("Veetorp's green", 72, 208, 112),
			new SpriteColor("Glan's blue", 32, 168, 248),
			new SpriteColor("Karkat's eyes", 16, 96, 48)
	};

	// A beautiful rainbow
	public static final SpriteColor[] RAINBOW = new SpriteColor[] {
			new SpriteColor("Red", 248, 0, 0),
			new SpriteColor("Orange", 248, 128, 0),
			new SpriteColor("Yellow", 248, 248, 0),
			new SpriteColor("Green", 0, 248, 0),
			new SpriteColor("Cyan", 0, 248, 248),
			new SpriteColor("Blue", 0, 0, 248),
			new SpriteColor("Indigo", 128, 0, 248),
			// dark
			new SpriteColor("Dark red", 208, 0, 0),
			new SpriteColor("Dark orange", 208, 104, 0),
			new SpriteColor("Dark yellow", 208, 208, 0),
			new SpriteColor("Dark green", 0, 208, 0),
			new SpriteColor("Dark cyan", 0, 208, 208),
			new SpriteColor("Dark blue", 0, 0, 208),
			new SpriteColor("Dark indigo", 104, 0, 208),
			new SpriteColor("Darker red", 176, 0, 0),
			// darker
			new SpriteColor("Darker orange", 176, 88, 0),
			new SpriteColor("Darker yellow", 176, 176, 0),
			new SpriteColor("Darker green", 0, 176, 0),
			new SpriteColor("Darker cyan", 0, 176, 176),
			new SpriteColor("Darker blue", 0, 0, 176),
			new SpriteColor("Darker indigo", 88, 0, 176),
			// even darker
			new SpriteColor("Even darker red", 144, 0, 0),
			new SpriteColor("Even darker orange", 144, 72, 0),
			new SpriteColor("Even darker yellow", 144, 144, 0),
			new SpriteColor("Even darker green", 0, 144, 0),
			new SpriteColor("Even darker cyan", 0, 144, 144),
			new SpriteColor("Even darker blue", 0, 0, 144),
			new SpriteColor("Even darker indigo", 72, 0, 144),
			// even darkerer
			new SpriteColor("Even darkerer red", 104, 0, 0),
			new SpriteColor("Even darkerer orange", 104, 32, 0),
			new SpriteColor("Even darkerer yellow", 104, 104, 0),
			new SpriteColor("Even darkerer green", 0, 104, 0),
			new SpriteColor("Even darkerer cyan", 0, 104, 104),
			new SpriteColor("Even darkerer blue", 0, 0, 104),
			new SpriteColor("Even darkerer indigo", 32, 0, 104)
	};

	// Miscellaneous colors
	public static final SpriteColor[] MISC = new SpriteColor[] {
			new SpriteColor("Black", 8, 8, 8),
			new SpriteColor("Off-white", 240, 240, 240),
			new SpriteColor("Brown", 128, 48, 0),
			new SpriteColor("Gray", 72, 72, 72),
			new SpriteColor("Light gray", 144, 144, 144),
			new SpriteColor("Beige", 160, 160, 96),
			new SpriteColor("Garbage", 96, 88, 0),
			new SpriteColor("Pink", 248, 88, 248),
			new SpriteColor("Magenta", 248, 0, 128),
			new SpriteColor("Periwinkle", 200, 200, 248),
			new SpriteColor("Bronze", 136, 72, 48),
			new SpriteColor("Silver", 192, 192, 192),
			new SpriteColor("Gold", 240, 184, 0),
			new SpriteColor("Sand", 240, 240, 144),
			new SpriteColor("Turquoise", 64, 208, 192),
			new SpriteColor("Blilver", 128, 144, 160),
			new SpriteColor("Gravel", 144, 136, 96)
	};

	// Vanilla colors
	public static final SpriteColor[] VANILLA_FAVORITES = new SpriteColor[] {
			// green mail
			new SpriteColor("Vanilla white", 248, 248, 248),
			new SpriteColor("Vanilla yellow", 240, 216, 64),
			new SpriteColor("Vanilla skin shade", 184, 104, 32),
			new SpriteColor("Lieutenant face", 240, 160, 104),
			new SpriteColor("Vanilla outline black", 40, 40, 40),
			new SpriteColor("Vanilla orange", 248, 120, 0),
			new SpriteColor("Vanilla red", 192, 24, 32),
			new SpriteColor("Vanilla Link's pink hair", 232, 96, 176),
			new SpriteColor("Vanilla tunic shade", 56, 144, 104),
			new SpriteColor("Vanilla tunic", 64, 216, 112),
			new SpriteColor("Vanilla hat shade", 80, 144, 16),
			new SpriteColor("Vanilla hat", 120, 184, 32),
			new SpriteColor("Vanilla hands", 224, 144, 80),
			new SpriteColor("Vanilla sleeves", 136, 88, 40),
			new SpriteColor("Vanilla water purple", 192, 128, 240),
			// blue mail
			new SpriteColor("Vanilla blue mail tunic shade", 0, 96, 208),
			new SpriteColor("Vanilla blue mail tunic", 136, 160, 232),
			new SpriteColor("Vanilla blue mail hat shade", 192, 160, 72),
			new SpriteColor("Vanilla blue mail hat", 248, 216, 128),
			new SpriteColor("Vanilla blue mail sleeves", 200, 96, 32),
			// red mail
			new SpriteColor("Vanilla red mail tunic shade", 184, 16, 32),
			new SpriteColor("Vanilla red mail tunic", 240, 88, 136),
			new SpriteColor("Vanilla red mail hat shade", 152, 120, 216),
			new SpriteColor("Vanilla red mail hat", 200, 168, 248),
			new SpriteColor("Vanilla red mail sleeves", 56, 136, 64),
			// other
			new SpriteColor("Vanilla bunny skin", 144, 24, 48),
			new SpriteColor("Vanilla power gloves", 184, 184, 168),
			new SpriteColor("Vanilla mitts", 176, 216, 0)
	};

	// Boss colors
	public static final SpriteColor[] BOSS_COLORS = new SpriteColor[] {
			// Armos
			new SpriteColor("Armos Knight blue", 80, 112, 200),
			new SpriteColor("Angry Armos Knight red", 184, 64, 72),
			new SpriteColor("Armos' shield bronze", 176, 96, 40),
			// Lanmolas
			new SpriteColor("Lanmolas green", 64, 136, 88),
			new SpriteColor("Lanmolas' burrow brown", 72, 64, 24),
			// Moldorm
			new SpriteColor("Moldorm yellow", 168, 152, 24),
			new SpriteColor("Moldorm's buggy eyes red", 232, 128, 104),
			// Agahnim
			new SpriteColor("Agahnim's robe green", 144, 208, 56),
			new SpriteColor("Agahnim's robe trim purple", 192, 64, 128),
			new SpriteColor("Blue ball blue", 120, 144, 248),
			// Helmasaur
			new SpriteColor("Helmasaur orange", 208, 56, 40),
			new SpriteColor("Helmasaur's mask blue", 120, 112, 232),
			// Arrghus
			new SpriteColor("Arrghus red", 176, 64, 56),
			new SpriteColor("Arrghus puff orange", 216, 88, 0),
			// Mothula
			new SpriteColor("Mothula blue", 120, 112, 232),
			new SpriteColor("Mothula orange", 216, 88, 32),
			// Blind
			new SpriteColor("Blind red", 144, 48, 24),
			new SpriteColor("Blind laser orange", 248, 112, 48),
			// Kholdstare
			new SpriteColor("Kholdstare pink", 240, 160, 216),
			new SpriteColor("Kholdstare's shell blue", 88, 176, 232),
			new SpriteColor("Falling ice blue", 160, 200, 248),
			// Vitreous
			new SpriteColor("Vitreous green", 80, 192, 144),
			new SpriteColor("Vitreous goo green", 72, 152, 88),
			// Trinexx
			new SpriteColor("Fire nexx red", 160, 0, 40),
			new SpriteColor("Ice nexx blue", 72, 80, 208),
			new SpriteColor("Stone nexx gray", 120, 136, 144),
			// Ganon
			new SpriteColor("Ganon blue", 80, 136, 168),
			new SpriteColor("Ganon cape red", 184, 48, 16),
			new SpriteColor("Ganon tunic brown", 136, 96, 8),
			new SpriteColor("Ganon's stunning blue", 96, 184, 192)
	};

	// end color constants
}