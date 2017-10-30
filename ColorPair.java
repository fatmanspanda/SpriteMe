package SpriteMe;
/**
 * Pairs colors together.
 */
public class ColorPair {
	private String n;
	private SpriteColor color1;
	private SpriteColor color2;

	public ColorPair(String name, SpriteColor a, SpriteColor b) {
		n = name;
		color1 = a;
		color2 = b;
	}
	
	public SpriteColor color1() {
		return color1;
	}
	
	public SpriteColor color2() {
		return color2;
	}
	
	public String toString() {
		return n;
	}
	
	/*
	 * Some constants
	 */
	public static final ColorPair WHITE_SKIN =
			new ColorPair("White skin", SpriteColor.WHITE_SKIN_LT, SpriteColor.WHITE_SKIN_DK);
	public static final ColorPair PALE_SKIN =
			new ColorPair("Pale skin", SpriteColor.PALE_SKIN_LT, SpriteColor.PALE_SKIN_DK);
	public static final ColorPair HISPANIC_SKIN =
			new ColorPair("Hispanic skin", SpriteColor.HISPANIC_SKIN_LT, SpriteColor.HISPANIC_SKIN_DK);
	public static final ColorPair BROWN_SKIN =
			new ColorPair("Brown skin", SpriteColor.BROWN_SKIN_LT, SpriteColor.BROWN_SKIN_DK);
	public static final ColorPair BLACK_SKIN =
			new ColorPair("Black skin", SpriteColor.BLACK_SKIN_LT, SpriteColor.BLACK_SKIN_DK);
}
