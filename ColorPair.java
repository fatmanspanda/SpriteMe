package SpriteMe;

public class ColorPair {
	private String n;
	private SpriteColor color1;
	private SpriteColor color2;

	/**
	 * Pairs 2 colors to operate together.
	 * @param name
	 * @param a
	 * @param b
	 */
	public ColorPair(String name, SpriteColor a, SpriteColor b) {
		n = name;
		color1 = a;
		color2 = b;
	}

	/**
	 * @return Object reference for the first {@code SpriteColor}.
	 */
	public SpriteColor color1() {
		return color1;
	}

	/**
	 * @return Object reference for the second {@code SpriteColor}.
	 */
	public SpriteColor color2() {
		return color2;
	}

	public String toString() {
		return n;
	}
	
	/*
	 * Some constants
	 */
	// Skin
	public static final ColorPair WHITE_SKIN =
			new ColorPair("White skin", SpriteColor.WHITE_SKIN_LT, SpriteColor.WHITE_SKIN_DK);
	public static final ColorPair PALE_SKIN =
			SpriteColor.PALE_SKIN.makeShadedPair();
	public static final ColorPair HISPANIC_SKIN =
			SpriteColor.HISPANIC_SKIN.makeShadedPair();;
	public static final ColorPair BROWN_SKIN =
			SpriteColor.BROWN_SKIN.makeShadedPair();
	public static final ColorPair BLACK_SKIN =
			SpriteColor.BLACK_SKIN.makeShadedPair();
}