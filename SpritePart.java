package SpriteMe;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import SpriteManipulator.SpriteManipulator;
public final class SpritePart implements Comparable<SpritePart> {

	// Maps colors to an indexing that only serves this sprite part
	// only used for initializing a map of the image
	private final byte[][] colorMap;
	/* Maps indices to an index in the full palette
	 * Such that { a, b } means :
	 * Index 0 turns into index a of the main palette
	 * Index 1 of this part becomes index b of the image
	 */
	private final byte[] indexMap;
	// default map for resetting the indices
	private final byte[] defaultIndexMap;

	private final String n;
	private final String path;
	private final String[] colorAreas;
	protected final int z;
	private final byte[] raster;

	// invariable size
	private static final int RASTERSIZE = SpriteManipulator.INDEXED_RASTER_SIZE;

	/**
	 * Creates a new {@code SpritePart}.
	 * All allowable parts are defined as constants within this file, and,
	 * as such, this constructor is hidden.
	 * @param name - Name of the part. To be used in {@code toString()}
	 * @param imagePath - File path to the {@code PNG} file for this sprite's data.
	 * @param colorIndexMap - Custom indexing of the file based on its default color values.
	 * @param paletteIndexMap - Maps this image's indices to indices on Link's palette.
	 * @param areas - {@code String} names for each color index.
	 * @param zIndex - Display priority of the part, relative to all other parts. Higher values are drawn last.
	 */
	private SpritePart(String name, String imagePath, byte[][] colorIndexMap,
						byte[] paletteIndexMap, String[] areas, int zIndex) {
		n = name;
		path = imagePath;
		colorMap = colorIndexMap;
		defaultIndexMap = paletteIndexMap;
		indexMap = new byte[defaultIndexMap.length];
		colorAreas = areas;
		// deep copy
		resetIndexMapping();		
		z = zIndex;
		raster = new byte[RASTERSIZE];
		getResourceAndRaster();
	}

	/**
	 * Undoes all custom indexing performed by the user.
	 */
	public void resetIndexMapping() {
		for (int i = 0; i < indexMap.length; i++) {
			indexMap[i] = defaultIndexMap[i];
		}
	}

	/**
	 * 
	 */
	public String toString() {
		return n;
	}

	/**
	 * @return The number of unique color indices for this part.
	 */
	public int colorCount() {
		return indexMap.length;
	}

	/**
	 * @return A {@code byte[]} map containing
	 * which color index is mapped to which Link palette index,
	 * such that {@code indexMap[0]} refers to color index 0 of this part.
	 */
	public byte[] indexMap() {
		return indexMap;
	}

	/**
	 * @param i
	 * @return The {@code String} name given to desired index {@code i}.
	 */
	public String colorName(int i) {
		return colorAreas[i];
	}

	/**
	 * @param i
	 * @return The Link palette index mapped to desired index {@code i}.
	 */
	public byte colorIndex(int i) {
		return indexMap[i];
	}

	/**
	 * @return A full raster of indices for the part,
	 * where the indices used are that of Link's palette.
	 */
	public byte[] getRaster() {
		return raster;
	}

	/**
	 * Fetches the file's resource and creates a raster based on the part's indexing.
	 */
	private void getResourceAndRaster() {
		BufferedImage file;
		try {
			file = ImageIO.read(SpritePart.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		file = SpriteManipulator.convertToABGR(file);
		byte[] rawRaster = SpriteManipulator.getImageRaster(file);

		// convert pal for easier comparison
		int[] RGB9Pal = new int[colorMap.length];
		for (int i = 0; i < colorMap.length; i++) {
			RGB9Pal[i] = SpriteManipulator.toRGB9(
					Byte.toUnsignedInt(colorMap[i][0]),
					Byte.toUnsignedInt(colorMap[i][1]),
					Byte.toUnsignedInt(colorMap[i][2])
					);
		}

		// reindex file
		int curCol;
		int pos;
		int curColI;
		boolean matchedColor;

		for (int i = 0; i < RASTERSIZE; i++) {
			pos = 4 * i;
			curCol = SpriteManipulator.toRGB9(
					Byte.toUnsignedInt(rawRaster[pos+3]), // blue comes first in raster
					Byte.toUnsignedInt(rawRaster[pos+2]),
					Byte.toUnsignedInt(rawRaster[pos+1])
					);
			curColI = 0;
			matchedColor = false;
			// test color
			for (int t : RGB9Pal) {
				if (t == curCol) {
					matchedColor = true;
					break;
				} else {
					curColI++;
				}
			} // end color test

			// apply mapped index
			if (matchedColor) {
				raster[i] = indexMap[curColI];
			} else {
				raster[i] = 0;
			}
		} // end raster loop
	} // end raster creation

	/**
	 * Change the assignment of Color X to a new Palette index
	 */
	public void remapColor(int i, byte j) throws IndexOutOfBoundsException {
		try {
			indexMap[i] = j;
			getResourceAndRaster();
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}

	/**
	 * @return This part's display priority.
	 */
	public int zIndex() {
		return z;
	}

	/**
	 * Private function that converts an array of {@code int} values
	 * into an array of {@code byte} values.
	 * 
	 * Only used for cleaner hard coding of inputs.
	 * @param c
	 */
	private static byte[][] convertArray(int[][] c) {
		byte[][] ret = new byte[c.length][c[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = (byte) c[i][j];
			}
		}
		return ret;
	}

	/**
	 * 
	 */
	public int compareTo(SpritePart p) {
		return z - p.z;
	}

	/*
	 * Public constants for sprite parts
	 */
	/**
	 * Default placeholder object. Contains no data.
	 */
	public static final SpritePart NOTHING = new SpritePart(
			"Nothing",
			"/SpriteMe/Images/Nothing.png",
			new byte[][] { {1,1,1} },
			new byte[] { 0 },
			new String[] {},
			0
		);

	/*
	 * Main template
	 */
	public static final SpritePart BODY = new SpritePart(
			"Body",
			"/SpriteMe/Images/Dummy Link.png",
			convertArray(new int[][] { 
						{ 40, 40, 40 }, // black outline
						{ 248, 248, 248 }, // eyes and water
						{ 240, 216, 64 }, // yellow
						{ 240, 160, 104 }, // skin
						{ 184, 104, 32 }, // skin shade
						{ 194, 24, 32 }, // mouth
						{ 192, 128, 240 } // water
					}),
			new byte[] { 5, 1, 2, 4, 3, 7, 15 },
			new String[] { "","","","","","","" },
			0
		);

	/*
	 * Hair
	 */
	public static final SpritePart BALD = new SpritePart(
			"Bald",
			"/SpriteMe/Images/Nothing.png",
			new byte[][] { {1,1,1} },
			new byte[] { 0 },
			new String[] {},
			0
		);

	/*
	 * Accessories
	 */
	public static final SpritePart GLASSES = new SpritePart(
			"Glasses",
			"/SpriteMe/Images/glasses_template.png",
			convertArray(new int[][] { 
						{ 40, 40, 40 }, // black outline
						{ 192, 128, 240 } // lens
					}),
			new byte[] { 5, 15 },
			new String[] { "Frame", "Lens" },
			100
		);

	public static final SpritePart EYEPATCH = new SpritePart(
			"Eyepatch",
			"/SpriteMe/Images/Eyepatch template.png",
			convertArray(new int[][] { 
						{ 40, 40, 40 }, // black outline
					}),
			new byte[] { 5 },
			new String[] { "Eye patch color" },
			100
		);
}