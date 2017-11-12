package SpriteMe;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import SpriteManipulator.SpriteManipulator;
public final class SpritePart implements Comparable<SpritePart> {
	// class constants
	private static final int RASTERSIZE = SpriteManipulator.INDEXED_RASTER_SIZE;
	private static final String TPATH = "/SpriteMe/Images/";

	private final String n;
	private final String path;
	public final int z;
	private final byte[] raster;
	public final boolean isBlankSheet;
	private final Index[] indices;
	private final Index[] remappableIndices;

	/**
	 * Creates a new {@code SpritePart}.
	 * All allowable parts are defined as constants within this file, and,
	 * as such, this constructor is hidden.
	 * @param name - Name of the part. To be used in {@code toString()}
	 * @param imagePath - File path to the {@code PNG} file for this sprite's data.
	 * @param indices - An array of {@code Index} objects containing info about how to draw and read the image
	 * @param zIndex - Display priority of the part, relative to all other parts. Higher values are drawn last.
	 */
	private SpritePart(String name, String imagePath,
			Index[] indices, int zIndex, boolean isBlank) {
		n = name;
		path = imagePath;
		isBlankSheet = isBlank;
		z = zIndex;
		this.indices = indices;
		// count remappable indices
		int c = 0;
		for (Index i : indices) {
			if (i.isRemappable) {
				c++;
			}
		}
		// make remappable indices array
		this.remappableIndices = new Index[c];
		c = 0;
		for (Index i : indices) {
			if (i.isRemappable) {
				remappableIndices[c++] = i;
			}
		}

		raster = new byte[RASTERSIZE];
		getResourceAndRaster();
	}

	/**
	 * Creates a new {@code SpritePart}, defaulting blankness to {@code false}.
	 * @param name
	 * @param imagePath
	 * @param colorIndexMap
	 * @param paletteIndexMap
	 * @param areas
	 * @param zIndex
	 * @param isBlank
	 */
	private SpritePart(String name, String imagePath, Index[] indices, int zIndex) {
		this(name, imagePath, indices, zIndex, false);
	}

	/**
	 * Undoes all custom indexing performed by the user.
	 */
	public void resetIndexMapping() {
		for (Index i : indices) {
			i.resetMap();
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
		return indices.length;
	}

	/**
	 * @return The number of unique color indices for this part.
	 */
	public int remappableColorCount() {
		return remappableIndices.length;
	}

	/**
	 * @param i
	 * @return The {@code String} name given to desired index {@code i}.
	 */
	public String getColorName(int i) {
		return indices[i].name;
	}

	/**
	 * 
	 */
	public String getNthRemappableColorName(int i) {
		return remappableIndices[i].name;
	}

	/**
	 * @param i
	 * @return The Link palette index mapped to desired index {@code i}.
	 */
	public byte getColorAtIndex(int i) {
		return indices[i].mappedTo;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public byte getNthRemappableIndex(int i) {
		return remappableIndices[i].mappedTo;
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
	private final void getResourceAndRaster() {
		BufferedImage file;
		try {
			file = ImageIO.read(SpritePart.class.getResourceAsStream(TPATH+path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		file = SpriteManipulator.convertToABGR(file);
		byte[] rawRaster = SpriteManipulator.getImageRaster(file);

		// convert pal for easier comparison
		int[] RGB9Pal = new int[indices.length];
		for (int i = 0; i < indices.length; i++) {
			byte[] colorMap = indices[i].defaultColor;
			RGB9Pal[i] = SpriteManipulator.toRGB9(
					Byte.toUnsignedInt(colorMap[0]),
					Byte.toUnsignedInt(colorMap[1]),
					Byte.toUnsignedInt(colorMap[2])
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
				raster[i] = indices[curColI].getIndexMappedTo();
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
			indices[i].remapTo(j);
			getResourceAndRaster();
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}

	public void remapNTHRemappableColor(int i, byte j) throws IndexOutOfBoundsException {
		try {
			remappableIndices[i].remapTo(j);
			getResourceAndRaster();
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
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
			"Nothing.png",
			new Index[] {},
			0,
			true // blank sheet
		);

	/*
	 * Main template
	 */
	public static final SpritePart BODY = new SpritePart(
			"Body",
			"Dummy Link.png",
			new Index[] {
					new Index("", 5, false, 40, 40, 40),
					new Index("", 1, false, 248, 248, 248),
					new Index("", 2, false, 240, 216, 64),
					new Index("", 4, false, 240, 160, 104),
					new Index("", 3, false, 184, 104, 32),
					new Index("", 7, false,  194, 24, 32),
					new Index("", 15, false, 192, 128, 240)
				},
			0
		);

	/*
	 * Hair
	 */
	public static final SpritePart BALD = new SpritePart(
			"Bald",
			"Nothing.png",
			new Index[] {},
			1,
			true // blank sheet
		);

	public static final SpritePart[] HAIR_CHOICES = new SpritePart[] {
		BALD
	};

	/*
	 * Accessories
	 */
	public static final SpritePart[] ACCESSORIES = new SpritePart[] {
		NOTHING,
		// Glasses
		new SpritePart(
			"Glasses",
			"glasses_template.png",
			new Index[] {
					new Index("Frame", 5, false, 40, 40, 40),
					new Index("Lens", 15, true, 192, 128, 240)
				},
			100),
		// Eye patch
		new SpritePart(
			"Eyepatch",
			"Eyepatch template.png",
			new Index[] {
					new Index("Color", 5, true, 40, 40, 40)
				},
			100),
		// pendant
		new SpritePart(
			"Pendant",
			"Pendant template.png",
			new Index[] {
					new Index("Outline", 5, false, 40, 40, 40),
					new Index("String", 1, true, 192, 128, 240), // default white, different here for indexing
					new Index("Gem", 10, true, 64, 216, 112),
					new Index("Gem shade", 9, true, 56, 144, 104),
					new Index("Gem luster", 1, false, 248, 248, 248)
			},
			100)
	};

	/**
	 * Index class for bunching together remap control properties
	 */
	static class Index {
		public final String name;
		/*
		 * Maps indices to an index in the full palette
		 * Such that { a, b } means :
		 * Index 0 turns into index a of the main palette
		 * Index 1 of this part becomes index b of the image
		 */
		private byte mappedTo;
		private final byte defaultMappedTo;
		public final boolean isRemappable;
		// Maps colors to an indexing that only serves this sprite part
		// only used for initializing a map of the image
		public final byte[] defaultColor;

		public Index(String name, int mappedTo, boolean isRemappable,
				int r, int g, int b) {
			this.name = name;
			this.mappedTo = (byte) mappedTo;
			this.defaultMappedTo = (byte) mappedTo;
			this.isRemappable = isRemappable;
			defaultColor = new byte[] {
					(byte) r,
					(byte) g,
					(byte) b
			};
		}

		public void remapTo(byte i) {
			if (isRemappable) {
				mappedTo = i;
			}
		}

		public byte getIndexMappedTo() {
			return mappedTo;
		}

		public void resetMap() {
			mappedTo = defaultMappedTo;
		}
	} // end Index class
}