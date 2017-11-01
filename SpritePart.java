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
	private static final int RASTERSIZE = SpriteManipulator.INDEXEDRASTERSIZE;
	private SpritePart(String name, String imagePath,
			byte[][] colorIndexMap, byte[] paletteIndexMap, String[] areas,
			int zIndex) {
		n = name;
		path = imagePath;
		colorMap = colorIndexMap;
		indexMap = paletteIndexMap;
		colorAreas = areas;
		// deep copy
		defaultIndexMap = new byte[indexMap.length];
		resetIndexMapping();		
		z = zIndex;
		raster = new byte[RASTERSIZE];
		getResourceAndRaster();
	}

	public void resetIndexMapping() {
		for (int i = 0; i < indexMap.length; i++) {
			indexMap[i] = defaultIndexMap[i];
		}
	}

	/**
	 * 
	 */
	public String getName() {
		return n;
	}

	/**
	 * 
	 */
	public int colorCount() {
		return indexMap.length;
	}

	/**
	 * 
	 */
	public byte[] indexMap() {
		return indexMap;
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public String colorName(int i) {
		return colorAreas[i];
	}

	/**
	 * 
	 */
	public byte colorIndex(int i) {
		return indexMap[i];
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getRaster() {
		return raster;
	}
	/**
	 * 
	 */
	private void getResourceAndRaster() {
		BufferedImage file;
		try {
			file =
				ImageIO.read(SpritePart.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		file = SpriteManipulator.convertToABGR(file);
		byte[] rawRaster = SpriteManipulator.getImageRaster(file);

		// convert pal for easier comparison
		int[] RGB9Pal = new int[colorMap.length];
		for (int i = 0; i < colorMap.length; i++) {
			RGB9Pal[i] = SpriteManipulator.RGB9(
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
			curCol = SpriteManipulator.RGB9(
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
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}
	/**
	 * Display priority
	 */
	public int zIndex() {
		return z;
	}


	private static byte[][] convertArray(int[][] c) {
		byte[][] ret = new byte[c.length][c[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = (byte) c[i][j];
			}
		}
		return ret;
	}

	@Override
	public int compareTo(SpritePart p) {
		return z - p.z;
	}

	/*
	 * Public constants for sprite parts
	 */
	public static final SpritePart BODY = new SpritePart(
			"Body",
			"/SpriteMe/Images/Weird Chocolate Link.png",
			convertArray(new int[][] { 
						{ 40, 40, 40 }, // black outline
						{ 120, 184, 32 }, // tunic
						{ 80, 144, 16 } // tunic shade
					}),
			new byte[] { 5, 10, 9 },
			new String[] { "Outline", "Base color", "Shading" },
			0
			);

	public static final SpritePart TEST = new SpritePart(
			"Body",
			"/SpriteMe/Images/Weird Chocolate Link.png",
			convertArray(new int[][] { 
						{ 40, 40, 40 }, // black outline
						{ 240, 160, 104 }, // skin
						{ 184, 104, 32} // skin shade
					}),
			new byte[] { 5, 4, 3 },
			new String[] { "Outline", "Base color", "Shading" },
			0
			);

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
}