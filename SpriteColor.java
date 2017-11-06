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
	public static final SpriteColor RASPBERRY = // me
			new SpriteColor("Panda's raspberry", 152, 0, 64);
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
	public static final SpriteColor VT_GREEN =
			new SpriteColor("Veetorp's green", 72, 208, 112);

	public static final SpriteColor[] DEV_FAVORITES = new SpriteColor[] {
			RASPBERRY, MIKES_ORANGE, NUMPTY_BLUE, FEESH_PURPLE,
			ZARBY_ORANGE, SOSUKE_BLACK, BAZLY_CORAL, VT_GREEN
	};

	/*
	 * A beautiful rainbow
	 */
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

	public static final SpriteColor[] RAINBOW = new SpriteColor[] {
			RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, INDIGO,
			BLACK, OFFWHITE, BROWN, GRAY, BEIGE
	};

	/*
	 * Vanilla palette
	 */
	public static final SpriteColor VANILLA_WHITE =
			new SpriteColor("Vanilla white", 248, 248, 248);
	public static final SpriteColor VANILLA_YELLOW =
			new SpriteColor("Vanilla yellow", 240, 216, 64);
	public static final SpriteColor VANILLA_SKIN_DK =
			new SpriteColor("Vanilla skin shade", 184, 104, 32);
	public static final SpriteColor VANILLA_SKIN =
			new SpriteColor("Lieutenant face", 240, 160, 104);
	public static final SpriteColor VANILLA_OUTLINE =
			new SpriteColor("Vanilla outline black", 40, 40, 40);
	public static final SpriteColor VANILLA_ORANGE =
			new SpriteColor("Vanilla orange", 248, 120, 0);
	public static final SpriteColor VANILLA_RED =
			new SpriteColor("Vanilla red", 192, 24, 32);
	public static final SpriteColor VANILLA_HAIR =
			new SpriteColor("Vanilla Link's pink hair", 232, 96, 176);
	public static final SpriteColor VANILLA_TUNIC_DK =
			new SpriteColor("Vanilla tunic shade", 56, 144, 104);
	public static final SpriteColor VANILLA_TUNIC =
			new SpriteColor("Vanilla tunic", 64, 216, 112);
	public static final SpriteColor VANILLA_HAT_DK =
			new SpriteColor("Vanilla hat shade", 80, 144, 16);
	public static final SpriteColor VANILLA_HAT =
			new SpriteColor("Vanilla hat", 120, 184, 32);
	public static final SpriteColor VANILLA_HANDS =
			new SpriteColor("Vanilla hands", 224, 144, 80);
	public static final SpriteColor VANILLA_SLEEVES =
			new SpriteColor("Vanilla sleeves", 136, 88, 40);
	public static final SpriteColor VANILLA_WATER =
			new SpriteColor("Vanilla water purple", 192, 128, 240);
	public static final SpriteColor VANILLA_BLUE_MAIL_TUNIC_DK =
			new SpriteColor("Vanilla blue mail tunic shade", 0, 96, 208);
	public static final SpriteColor VANILLA_BLUE_MAIL_TUNIC =
			new SpriteColor("Vanilla blue mail tunic", 136, 160, 232);
	public static final SpriteColor VANILLA_BLUE_MAIL_HAT_DK =
			new SpriteColor("Vanilla blue mail hat shade", 192, 160, 72);
	public static final SpriteColor VANILLA_BLUE_MAIL_HAT =
			new SpriteColor("Vanilla blue mail hat", 248, 216, 128);
	public static final SpriteColor VANILLA_BLUE_MAIL_SLEEVES =
			new SpriteColor("Vanilla blue mail sleeves", 200, 96, 32);
	public static final SpriteColor VANILLA_RED_MAIL_TUNIC_DK =
			new SpriteColor("Vanilla red mail tunic shade", 184, 16, 32);
	public static final SpriteColor VANILLA_RED_MAIL_TUNIC =
			new SpriteColor("Vanilla red mail tunic", 240, 88, 136);
	public static final SpriteColor VANILLA_RED_MAIL_HAT_DK =
			new SpriteColor("Vanilla red mail hat shade", 152, 120, 216);
	public static final SpriteColor VANILLA_RED_MAIL_HAT =
			new SpriteColor("Vanilla red mail hat", 200, 168, 248);
	public static final SpriteColor VANILLA_RED_MAIL_SLEEVES =
			new SpriteColor("Vanilla red mail sleeves", 56, 136, 64);
	public static final SpriteColor VANILLA_BUNNY_SKIN =
			new SpriteColor("Vanilla bunny skin", 144, 24, 48);
	public static final SpriteColor VANILLA_GLOVES =
			new SpriteColor("Vanilla power gloves", 181, 186, 165);
	public static final SpriteColor VANILLA_MITTS =
			new SpriteColor("Vanilla mitts", 181, 219, 0);

	public static final SpriteColor[] VANILLA_FAVORITES = new SpriteColor[] {
			VANILLA_WHITE, VANILLA_YELLOW, VANILLA_SKIN_DK, VANILLA_SKIN, VANILLA_OUTLINE,
			VANILLA_ORANGE, VANILLA_RED, VANILLA_HAIR, VANILLA_TUNIC_DK, VANILLA_TUNIC,
			VANILLA_HAT_DK, VANILLA_HAT, VANILLA_HANDS, VANILLA_SLEEVES, VANILLA_WATER,
			VANILLA_BLUE_MAIL_TUNIC_DK, VANILLA_BLUE_MAIL_TUNIC,
			VANILLA_BLUE_MAIL_HAT_DK, VANILLA_BLUE_MAIL_HAT, VANILLA_BLUE_MAIL_SLEEVES,
			VANILLA_RED_MAIL_TUNIC_DK, VANILLA_RED_MAIL_TUNIC,
			VANILLA_RED_MAIL_HAT_DK, VANILLA_RED_MAIL_HAT, VANILLA_RED_MAIL_SLEEVES,
			VANILLA_BUNNY_SKIN, VANILLA_GLOVES, VANILLA_MITTS
	};

	/*
	 * Boss colors
	*/
	public static final SpriteColor ARMOS_KNIGHT_BLUE =
			new SpriteColor("Armos Knight blue", 80, 112, 200);
	public static final SpriteColor ANGRY_ARMOS_KNIGHT_RED =
			new SpriteColor("Angry Armos Knight red", 184, 64, 72);
	public static final SpriteColor ARMOS_SHIELD_BRONZE =
			new SpriteColor("Armos' shield bronze", 176, 96, 40);
	public static final SpriteColor LANMOLAS_GREEN =
			new SpriteColor("Lanmolas green", 64, 136, 88);
	public static final SpriteColor LANMOLAS_BURROW_BROWN =
			new SpriteColor("Lanmolas' burrow brown", 72, 64, 24);
	public static final SpriteColor MOLDORM_YELLOW =
			new SpriteColor("Moldorm yellow", 168, 152, 24);
	public static final SpriteColor MOLDORMS_EYES_RED =
			new SpriteColor("Moldorm's buggy eyes red", 232, 128, 104);
	public static final SpriteColor AGAHNIM_ROBE_GREEN =
			new SpriteColor("Agahnim's robe green", 144, 208, 56);
	public static final SpriteColor AGAHNIM_ROBE_PURPLE =
			new SpriteColor("Agahnim's robe trim purple", 192, 64, 128);
	public static final SpriteColor BLUE_BALL_BLUE =
			new SpriteColor("Blue ball blue", 120, 144, 248);
	public static final SpriteColor HELMASAUR_ORANGE =
			new SpriteColor("Helmasaur orange", 208, 56, 40);
	public static final SpriteColor HELMASAUR_MASK_BLUE =
			new SpriteColor("Helmasaur's mask blue", 120, 112, 232);
	public static final SpriteColor ARRGHUS_RED =
			new SpriteColor("Arrghus red", 176, 64, 56);
	public static final SpriteColor ARRGHUS_PUFF_ORANGE =
			new SpriteColor("Arrghus puff orange", 216, 88, 0);
	public static final SpriteColor MOTHULA_BLUE =
			new SpriteColor("Mothula blue", 120, 112, 232);
	public static final SpriteColor MOTHULA_ORANGE =
			new SpriteColor("Mothula orange", 216, 88, 32);
	public static final SpriteColor BLIND_RED =
			new SpriteColor("Blind red", 144, 48, 24);
	public static final SpriteColor BLIND_LASER_ORANGE =
			new SpriteColor("Blind laser orange", 248, 112, 48);
	public static final SpriteColor KHOLDSTARE_PINK =
			new SpriteColor("Kholdstare pink", 240, 160, 216);
	public static final SpriteColor KHOLDSTARE_SHELL_BLUE =
			new SpriteColor("Kholdstare's shell blue", 88, 176, 232);
	public static final SpriteColor FALLING_ICE_BLUE =
			new SpriteColor("Falling ice blue", 160, 200, 248);
	public static final SpriteColor VITREOUS_GREEN =
			new SpriteColor("Vitreous green", 80, 192, 144);
	public static final SpriteColor VITREOUS_GOO_GREEN =
			new SpriteColor("Vitreous goo green", 72, 152, 88);
	public static final SpriteColor FIRE_NEXX_RED =
			new SpriteColor("Fire nexx red", 160, 0, 40);
	public static final SpriteColor ICE_NEX_BLUE =
			new SpriteColor("Ice nexx blue", 72, 80, 208);
	public static final SpriteColor STONE_NEX_GRAY =
			new SpriteColor("Stone nexx gray", 120, 136, 144);
	public static final SpriteColor GANON_BLUE =
			new SpriteColor("Ganon blue", 80, 136, 168);
	public static final SpriteColor GANON_CAPE_RED =
			new SpriteColor("Ganon cape red", 184, 48, 16);
	public static final SpriteColor GANON_TUNIC_BROWN =
			new SpriteColor("Ganon tunic brown", 136, 96, 8);
	public static final SpriteColor GANON_STUN_BLUE =
			new SpriteColor("Ganon's stunning blue", 96, 184, 192);

	public static final SpriteColor[] BOSS_COLORS = new SpriteColor[] {
			ARMOS_KNIGHT_BLUE, ANGRY_ARMOS_KNIGHT_RED, ARMOS_SHIELD_BRONZE,
			LANMOLAS_GREEN, LANMOLAS_BURROW_BROWN,
			MOLDORM_YELLOW, MOLDORMS_EYES_RED,
			AGAHNIM_ROBE_GREEN, AGAHNIM_ROBE_PURPLE, BLUE_BALL_BLUE,
			HELMASAUR_ORANGE, HELMASAUR_MASK_BLUE,
			ARRGHUS_RED, ARRGHUS_PUFF_ORANGE,
			MOTHULA_BLUE, MOTHULA_ORANGE,
			BLIND_RED, BLIND_LASER_ORANGE,
			KHOLDSTARE_PINK, KHOLDSTARE_SHELL_BLUE, FALLING_ICE_BLUE,
			VITREOUS_GREEN, VITREOUS_GOO_GREEN,
			FIRE_NEXX_RED, ICE_NEX_BLUE, STONE_NEX_GRAY,
			GANON_BLUE, GANON_CAPE_RED, GANON_TUNIC_BROWN, GANON_STUN_BLUE
	};
}