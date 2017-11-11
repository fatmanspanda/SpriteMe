package SpriteMe;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import SpriteManipulator.*;

public class SpriteMe {
	// version numbering
	// Time stamp: 7 Nov 2017
	@SuppressWarnings("unused")
	private static final byte VERSION = 1;
	private static final String VERSION_TAG = "v0.0.0";
	@SuppressWarnings("unused")
	private static final byte SME_VERSION = 1; // file type specification
	@SuppressWarnings("unused")
	private static final byte[] SME_FLAG = { 'S', 'P', 'R', 'I', 'T', 'E', 'M', 'E' };

	// class constants
	public static final int SPLOTCH_SIZE = 16;
	public static final Dimension SPLOTCH_DIMENSION = new Dimension(SPLOTCH_SIZE, SPLOTCH_SIZE);

	private static String[] INSTRUCTION_STYLE = {
			"padding: 10px 10px 10px 0px",
			"width: 230px"
	};

	/*
	 * Combo box constants
	 */
	public static final ColorPair[] SKINCOLORS = new ColorPair[] {
			ColorPair.WHITE_SKIN,
			ColorPair.PALE_SKIN,
			ColorPair.HISPANIC_SKIN,
			ColorPair.BROWN_SKIN,
			ColorPair.BLACK_SKIN
	};

	public static final String[] MAIL_NAMES = {
		"Green mail", "Blue mail", "Red mail", "Bunny"
	};

	public static final SpritePart[] ACCESSORIES = new SpritePart[] {
		SpritePart.NOTHING,
		SpritePart.GLASSES,
		SpritePart.EYEPATCH
	};

	// file type constants
	static final String[] SPRITE_ME_EXTS = { "sme" }; // SpriteMe files
	static final String[] EXPORT_EXTS = { ZSPRFile.EXTENSION }; // sprite files
	static final String[] ROM_EXTS = { "sfc" }; // ROM

	// main
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				doTheGUI();
			}
		});
	}

	// GUI
	public static void doTheGUI() {
		//try to set LaF
		try {
			UIManager.setLookAndFeel("metal");
		} catch (UnsupportedLookAndFeelException
				| ClassNotFoundException
				| InstantiationException
				| IllegalAccessException e) {
			// try to set System default
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException
					| ClassNotFoundException
					| InstantiationException
					| IllegalAccessException e2) {
					// do nothing
			} //end System
		} // end Metal

		// fast and long-lasting tooltips
		ToolTipManager.sharedInstance().setInitialDelay(100);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE); // 596:31:23.647

		// main window
		final Dimension d = new Dimension(1000,750);
		final JFrame frame = new JFrame("Sprite Me " + VERSION_TAG);

		// format main wrapper
		final Container fullWrap = new Container();
		final Dimension cd = new Dimension(600,500);
		final Dimension cbd = new Dimension(120, 20);
		SpringLayout l = new SpringLayout();
		fullWrap.setLayout(l);
		fullWrap.setPreferredSize(cd);
		fullWrap.setMinimumSize(cd);

		final PresetSplotchChooser presets = new PresetSplotchChooser(frame);
		Palette pal = new Palette();
		final IndexedSprite mySprite = new IndexedSprite(pal);

		final JLabel helpText = new JLabel();
		helpText.setText("<html>" +
				"<div style=\"" + String.join(";", INSTRUCTION_STYLE) + "\">" +
				"Use this area to pick and customize your sprite's individual parts. " +
				"When applicable, you may use additional controls to reindex the " +
				"colors of individual parts." +
				"<br /><br />" +
				"Note that anything mapped to index 13 may change when gloves or mitts are obtained." +
				"<br />" +
				"Anything mapped to index 0 will be fully transparent." +
				"</div>" +
				"</html>");
		l.putConstraint(SpringLayout.EAST, helpText, 0,
				SpringLayout.EAST, fullWrap);
		l.putConstraint(SpringLayout.NORTH, helpText, 0,
				SpringLayout.NORTH, fullWrap);
		fullWrap.add(helpText);

		/*
		 * Customization controls
		 */
		// mail preview
		final JLabel mailLbl = new JLabel("Mail preview:", SwingConstants.RIGHT);
		final JComboBox<String> mailPick = new JComboBox<String>(MAIL_NAMES);
		setAllSizes(mailPick, cbd);

		l.putConstraint(SpringLayout.EAST, mailLbl, -6,
				SpringLayout.WEST, mailPick);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, mailLbl, 0,
				SpringLayout.VERTICAL_CENTER, mailPick);
		fullWrap.add(mailLbl);

		l.putConstraint(SpringLayout.EAST, mailPick, -5,
				SpringLayout.EAST, fullWrap);
		l.putConstraint(SpringLayout.NORTH, mailPick, 5,
				SpringLayout.SOUTH, helpText);
		fullWrap.add(mailPick);

		// skin color
		final JLabel skinLbl = new JLabel("Skin color:", SwingConstants.RIGHT);
		final JComboBox<ColorPair> skinPick = new JComboBox<ColorPair>(SKINCOLORS);
		setAllSizes(skinPick, cbd);

		l.putConstraint(SpringLayout.EAST, skinLbl, 0,
				SpringLayout.EAST, mailLbl);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, skinLbl, 0,
				SpringLayout.VERTICAL_CENTER, skinPick);
		fullWrap.add(skinLbl);

		l.putConstraint(SpringLayout.EAST, skinPick, 0,
				SpringLayout.EAST, mailPick);
		l.putConstraint(SpringLayout.WEST, skinPick, 0,
				SpringLayout.WEST, mailPick);
		l.putConstraint(SpringLayout.NORTH, skinPick, 2,
				SpringLayout.SOUTH, mailPick);
		fullWrap.add(skinPick);

		// accessory 1
		final JComboBox<SpritePart> acc1Pick = new JComboBox<SpritePart>(ACCESSORIES);
		Picker acc1PickThis = part -> mySprite.setAccessory(part, 1);
		SpritePartEditor acc1Editor =
				new SpritePartEditor("Accessory 1", pal, acc1Pick, acc1PickThis);
		setAllSizes(acc1Pick, cbd);

		l.putConstraint(SpringLayout.EAST, acc1Editor, 0,
				SpringLayout.EAST, mailPick);
		l.putConstraint(SpringLayout.NORTH, acc1Editor, 2,
				SpringLayout.SOUTH, skinPick);
		fullWrap.add(acc1Editor);

		// accessory 2
		final JComboBox<SpritePart> acc2Pick = new JComboBox<SpritePart>(ACCESSORIES);
		Picker acc2PickThis = part -> mySprite.setAccessory(part, 2);
		SpritePartEditor acc2Editor =
				new SpritePartEditor("Accessory 2", pal, acc2Pick, acc2PickThis);
		setAllSizes(acc2Pick, cbd);

		l.putConstraint(SpringLayout.EAST, acc2Editor, 0,
				SpringLayout.EAST, mailPick);
		l.putConstraint(SpringLayout.NORTH, acc2Editor, 2,
				SpringLayout.SOUTH, acc1Editor);
		fullWrap.add(acc2Editor);

		// accessory 3
		final JComboBox<SpritePart> acc3Pick = new JComboBox<SpritePart>(ACCESSORIES);
		Picker acc3PickThis = part -> mySprite.setAccessory(part, 3);
		SpritePartEditor acc3Editor =
				new SpritePartEditor("Accessory 3", pal, acc3Pick, acc3PickThis);
		setAllSizes(acc3Pick, cbd);

		l.putConstraint(SpringLayout.EAST, acc3Editor, 0,
				SpringLayout.EAST, mailPick);
		l.putConstraint(SpringLayout.NORTH, acc3Editor, 2,
				SpringLayout.SOUTH, acc2Editor);
		fullWrap.add(acc3Editor);

		// format frame
		final Container framesWrap = frame.getContentPane();
		SpringLayout f = new SpringLayout();
		framesWrap.setLayout(f);

		// palette
		f.putConstraint(SpringLayout.NORTH, pal, 0,
				SpringLayout.NORTH, framesWrap);
		f.putConstraint(SpringLayout.EAST, pal, 0,
				SpringLayout.EAST, framesWrap);
		framesWrap.add(pal);

		// sprite appearance
		l.putConstraint(SpringLayout.NORTH, mySprite, 5,
				SpringLayout.NORTH, fullWrap);
		l.putConstraint(SpringLayout.WEST, mySprite, 5,
				SpringLayout.WEST, fullWrap);
		fullWrap.add(mySprite);

		// color changer
		ColorEditor colorEditor = new ColorEditor(presets, pal);
		pal.attachEditor(colorEditor);

		// wrapper frame
		f.putConstraint(SpringLayout.NORTH, fullWrap, 2,
				SpringLayout.NORTH, framesWrap);
		f.putConstraint(SpringLayout.SOUTH, fullWrap, -2,
				SpringLayout.SOUTH, framesWrap);
		f.putConstraint(SpringLayout.WEST, fullWrap, 2,
				SpringLayout.WEST, framesWrap);
		framesWrap.add(fullWrap);

		// color editor
		f.putConstraint(SpringLayout.NORTH, colorEditor, 2,
				SpringLayout.SOUTH, pal);
		f.putConstraint(SpringLayout.SOUTH, colorEditor, 2,
				SpringLayout.SOUTH, framesWrap);
		f.putConstraint(SpringLayout.EAST, colorEditor, -2,
				SpringLayout.EAST, framesWrap);
		framesWrap.add(colorEditor);

		// menu
		final JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);

		// file menu
		final JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);

		// File load
		final JMenuItem loadSpr = new JMenuItem("Open");
		ImageIcon compass = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Compass.png")
			);
		loadSpr.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		loadSpr.setIcon(compass);
		fileMenu.add(loadSpr);

		// separator
		fileMenu.addSeparator();

		// File quicksave
		final JMenuItem saveSpr = new JMenuItem("Save");
		ImageIcon book = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Book.png")
			);
		saveSpr.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveSpr.setIcon(book);
		fileMenu.add(saveSpr);

		// File save
		final JMenuItem saveSprTo = new JMenuItem("Save as...");
		ImageIcon bookAs = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Book as.png")
			);
		saveSprTo.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		saveSprTo.setIcon(bookAs);
		fileMenu.add(saveSprTo);

		// separator
		fileMenu.addSeparator();

		// ZSPR quicksave
		final JMenuItem expSpr = new JMenuItem("Export to " + ZSPRFile.EXTENSION);
		ImageIcon smallKey = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Small key.png")
			);
		expSpr.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		expSpr.setIcon(smallKey);
		fileMenu.add(expSpr);

		// ZSPR save
		final JMenuItem expSprTo = new JMenuItem("Export to " + ZSPRFile.EXTENSION + " + as...");
		ImageIcon smallKeyAs = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Small key as.png")
			);
		expSprTo.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		expSprTo.setIcon(smallKeyAs);
		fileMenu.add(expSprTo);

		// separator
		fileMenu.addSeparator();

		// ROM patch
		final JMenuItem patchRom = new JMenuItem("Patch to ROM");
		ImageIcon bigKey = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Big key.png")
			);
		patchRom.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		patchRom.setIcon(bigKey);

		fileMenu.add(patchRom);

		// separator
		fileMenu.addSeparator();

		// exit
		final JMenuItem exit = new JMenuItem("Exit");
		ImageIcon mirror = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Mirror.png")
			);
		exit.setIcon(mirror);
		fileMenu.add(exit);
		exit.addActionListener(arg0 -> System.exit(0));
		// end file menu

		// help menu
		final JMenu helpMenu = new JMenu("Help");

		// Acknowledgements
		final JMenuItem peeps = new JMenuItem("About");
		ImageIcon mapIcon = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Map.png")
			);
		peeps.setIcon(mapIcon);

		final JDialog aboutFrame = new JDialog(frame, "Acknowledgements");
		buildAbout(aboutFrame);

		peeps.addActionListener(arg0 -> aboutFrame.setVisible(true)); // do it here because short
		helpMenu.add(peeps);

		menu.add(helpMenu);
		// end help menu

		// saved names used for quick saving/exporting
		FakeString lastSavePath = new FakeString(null);
		FakeString lastSpritePath = new FakeString(null);

		// file explorer
		final BetterJFileChooser explorer = new BetterJFileChooser();

		// TODO Uncomment this for exports
		// explorer.setCurrentDirectory(new File(".")); // quick way to set to current .jar loc

		// set filters
		FileNameExtensionFilter smeFilter =
				new FileNameExtensionFilter("SpriteMe files (.sme)", SPRITE_ME_EXTS);
		FileNameExtensionFilter sprFilter =
				new FileNameExtensionFilter("ALttP Sprite files (" + ZSPRFile.EXTENSION + ")", EXPORT_EXTS);
		FileNameExtensionFilter romFilter =
				new FileNameExtensionFilter("ROM files (.sfc)", ROM_EXTS);

		explorer.setAcceptAllFileFilterUsed(false);

		// icon
		ImageIcon ico = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Link thinking small.png")
			);
		ImageIcon icoTask = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Link thinking.png")
			);
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(ico.getImage());
		icons.add(icoTask.getImage());
		frame.setIconImages(icons);
		aboutFrame.setIconImages(icons);

		// display frame
		frame.setPreferredSize(d);
		frame.setMinimumSize(d);
		setAllSizes(frame, d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(200, 70);

		// repainting on all sprite changes
		SpriteChangeListener repainter =
				arg0 -> {
					acc1Editor.refreshPalette();
					acc2Editor.refreshPalette();
					acc3Editor.refreshPalette();
					frame.repaint();
				};

		acc1Editor.addSpriteChangeListener(repainter);
		acc2Editor.addSpriteChangeListener(repainter);
		acc3Editor.addSpriteChangeListener(repainter);
		pal.addSpriteChangeListener(repainter);
		mySprite.addSpriteChangeListener(repainter);

		// Action listeners for controls
		skinPick.addItemListener(
				arg0 -> pal.setSkinColor((ColorPair) skinPick.getSelectedItem())
			);

		mailPick.addItemListener(
				arg0 -> mySprite.setMail(mailPick.getSelectedIndex())
			);

		// Action listeners for menu
		// save spr as sme file
		saveSpr.addActionListener(
				arg0 -> {
					if (lastSavePath.isSet()) {
/*						try {

						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame,
									"Error saving custom selections",
									"WOW",
									JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						}*/
					} else { // if nowhere to export, click "export to" to prompt a file selection
						saveSprTo.doClick();
					}
				});

		saveSprTo.addActionListener(
				arg0 -> {
					explorer.removeAllFilters();
					explorer.setFileFilter(smeFilter);
					int option = explorer.showSaveDialog(frame);

					if (option == JFileChooser.CANCEL_OPTION) {
						return;
					}

					String n = "";
					try {
						n = explorer.getSelectedFile().getPath();
					} catch (NullPointerException e) {
						// do nothing
					} finally {
						if (!SpriteManipulator.testFileType(n,SPRITE_ME_EXTS)) {
							if(!n.contains(".") && n.length() > 0) { // no filetype, append .sme
								n = n + ".sme";
							} else { // otherwise break out
								return;
							}
						}
						// if we have a file name, set it then click export
						lastSavePath.changeString(n);
						saveSpr.doClick();
					}
				});

		// sprite exporting
		expSpr.addActionListener(
				arg0 -> {
					if (lastSpritePath.isSet()) {
						ZSPRFile newSPR = mySprite.makeSprite();
						try {
							SpriteManipulator.writeSPRFile(lastSpritePath.toString(), newSPR);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame,
									"Error exporting to a " + ZSPRFile.EXTENSION + " file.",
									"WOW",
									JOptionPane.WARNING_MESSAGE);
						} catch (NotZSPRException e) {
							JOptionPane.showMessageDialog(frame,
									"File is not a " + ZSPRFile.EXTENSION + " file",
									"Not my job",
									JOptionPane.WARNING_MESSAGE);
							return;
						} catch (BadChecksumException e) {
							JOptionPane.showMessageDialog(frame,
									"Bad checksum; file may be corrupted",
									"Invalid",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					} else { // if nowhere to export, click "export to" to prompt a file selection
						expSprTo.doClick();
					}
				});

		expSprTo.addActionListener(
				arg0 -> {
					explorer.removeAllFilters();
					explorer.setFileFilter(sprFilter);
					int option = explorer.showSaveDialog(frame);

					if (option == JFileChooser.CANCEL_OPTION) {
						return;
					}

					String n = "";
					try {
						n = explorer.getSelectedFile().getPath();
					} catch (NullPointerException e) {
						// do nothing
					} finally {
						if (!SpriteManipulator.testFileType(n,EXPORT_EXTS)) {
							if(!n.contains(".") && n.length() > 0) { // no filetype, append zspr
								n = n + "." + ZSPRFile.EXTENSION;
							} else { // otherwise break out
								return;
							}
						}
						// if we have a file name, set it then click export
						lastSpritePath.changeString(n);
						expSpr.doClick();
					}
				});

		// ROM patching
		patchRom.addActionListener(
				arg0 -> {
					explorer.removeAllFilters();
					explorer.setFileFilter(romFilter);
					int option = explorer.showSaveDialog(frame);

					if (option == JFileChooser.CANCEL_OPTION) {
						return;
					}

					String n = "";
					try {
						n = explorer.getSelectedFile().getPath();
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(frame,
								"No ROM file found",
								"HOLY COW",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					ZSPRFile newSPR = mySprite.makeSprite();
					try {
						SpriteManipulator.patchRom(n, newSPR);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
								"No ROM found",
								"UH-OH SPAGHETTI-Os",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				});

		// Display frame
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Set min max and preferred size for a component
	 * @param c
	 * @param d
	 */
	private static void setAllSizes(Component c, Dimension d) {
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
	}

	// about frame built here
	private static void buildAbout(JDialog aboutFrame) {
		final TextArea peepsList = new TextArea("", 0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		peepsList.setEditable(false);
		peepsList.append("Written by fatmanspanda"); // hey, that's me

		peepsList.append("\n\nArt:\n");
		peepsList.append(String.join(", ",
				new String[]{
						"iBazly",
						"Fish"
					}));

		aboutFrame.add(peepsList);

		aboutFrame.setSize(300,300);
		aboutFrame.setLocation(150,150);
		aboutFrame.setResizable(false);
	}

	/**
	 * Objects must be final in actionlisteners
	 * Use a wrapper that's final for a string we're allowed to change
	 */
	private static class FakeString {
		private String s;

		public FakeString(String s) {
			this.s = s;
		}

		public void changeString(String s) {
			this.s = s;
		}

		public String toString() {
			return this.s;
		}

		public boolean isSet() {
			return this.s != null;
		}
	}
}