package spriteme;

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
	public static final ColorPair[] SKIN_COLORS = new ColorPair[] {
			new ColorPair("White skin", SpriteColor.WHITE_SKIN_LT, SpriteColor.WHITE_SKIN_DK),
			SpriteColor.PALE_SKIN.makeShadedPair(),
			SpriteColor.HISPANIC_SKIN.makeShadedPair(),
			SpriteColor.BROWN_SKIN.makeShadedPair(),
			SpriteColor.BLACK_SKIN.makeShadedPair()
	};
}