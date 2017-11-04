package SpriteMe;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import SpriteAnimator.GUIHelpers;
import SpriteManipulator.SpriteManipulator;

public class SpriteMe {
	private static final String VERSION = "v0.0.0";
	public static final int SPLOTCH_SIZE = 16;
	public static final Dimension SPLOTCH_DIMENSION = new Dimension(SPLOTCH_SIZE, SPLOTCH_SIZE);
	private static final Border rightPad = BorderFactory.createEmptyBorder(0,0,0,5);

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
		SpritePart.NOTHING, SpritePart.GLASSES,	
	};

	// file type constants
	static final String[] SPRITE_ME_EXTS = { "sme" }; // SpriteMe files
	static final String[] EXPORT_EXTS = { "spr" }; // sprite files
	static final String[] ROM_EXTS = { "sfc" }; // ROM

	// main
	public static void main(String[] args) {
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

		// main window
		final Dimension d = new Dimension(1000,720);
		final JFrame frame = new JFrame("Sprite Me " + VERSION);
		final JPanel controls = new JPanel(new GridBagLayout());
		GridBagConstraints w = new GridBagConstraints();

		w.gridy = -1;
		w.fill = GridBagConstraints.HORIZONTAL;

		// mail preview
		final JLabel mailLbl = new JLabel("Mail preview", SwingConstants.RIGHT);
		final JComboBox<String> mailPick = new JComboBox<String>(MAIL_NAMES);
		mailLbl.setBorder(rightPad);
		w.gridy++;
		w.gridx = 0;
		controls.add(mailLbl,w);
		w.gridx = 1;
		controls.add(mailPick, w);

		// skin color
		final JLabel skinLbl = new JLabel("Skin color", SwingConstants.RIGHT);
		final JComboBox<ColorPair> skinPick = new JComboBox<ColorPair>(SKINCOLORS);
		skinLbl.setBorder(rightPad);
		w.gridy++;
		w.gridx = 0;
		controls.add(skinLbl,w);
		w.gridx = 1;
		controls.add(skinPick, w);

		// accessories
		final JLabel acc1Lbl = new JLabel("Accessory 1", SwingConstants.RIGHT);
		final JComboBox<SpritePart> acc1Pick = new JComboBox<SpritePart>(ACCESSORIES);
		final JButton acc1Edit = new JButton("Edit");
		acc1Lbl.setBorder(rightPad);
		w.gridy++;
		w.gridx = 0;
		controls.add(acc1Lbl,w);
		w.gridx = 1;
		controls.add(acc1Pick, w);
		w.gridx = 2;
		controls.add(acc1Edit, w);

		// format frame
		final Container framesWrap = frame.getContentPane();
		SpringLayout f = new SpringLayout();
		framesWrap.setLayout(f);

		// format main wrapper
		final Container fullWrap = new Container();
		final Dimension cd = new Dimension(600,500);
		SpringLayout l = new SpringLayout();
		fullWrap.setLayout(l);
		fullWrap.setPreferredSize(cd);
		fullWrap.setMinimumSize(cd);

		// add controls
		l.putConstraint(SpringLayout.EAST, controls, -5,
				SpringLayout.EAST, fullWrap);
		l.putConstraint(SpringLayout.NORTH, controls, 5,
				SpringLayout.NORTH, fullWrap);
		fullWrap.add(controls);

		// palette
		Palette pal = new Palette();
		f.putConstraint(SpringLayout.NORTH, pal, 0,
				SpringLayout.NORTH, framesWrap);
		f.putConstraint(SpringLayout.EAST, pal, 0,
				SpringLayout.EAST, framesWrap);
		framesWrap.add(pal);

		// sprite appearance
		IndexedSprite mySprite = new IndexedSprite(pal);
		l.putConstraint(SpringLayout.NORTH, mySprite, 5,
				SpringLayout.NORTH, fullWrap);
		l.putConstraint(SpringLayout.WEST, mySprite, 5,
				SpringLayout.WEST, fullWrap);
		fullWrap.add(mySprite);

		// color changer
		ColorEditor colorEditor = new ColorEditor(pal);
		SpritePartEditor indexMapEditor = new SpritePartEditor(pal);
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

		// index map
		l.putConstraint(SpringLayout.SOUTH, indexMapEditor, -2,
				SpringLayout.SOUTH, fullWrap);
		l.putConstraint(SpringLayout.EAST, indexMapEditor, -2,
				SpringLayout.EAST, fullWrap);
		fullWrap.add(indexMapEditor);

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

		// SPR quicksave
		final JMenuItem expSpr = new JMenuItem("Export to SPR");
		ImageIcon smallKey = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Small key.png")
			);
		expSpr.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		expSpr.setIcon(smallKey);
		fileMenu.add(expSpr);

		// SPR save
		final JMenuItem expSprTo = new JMenuItem("Export to SPR as...");
		ImageIcon smallKeyAs = new ImageIcon(
				SpriteMe.class.getResource("/SpriteMe/Images/Meta/Small key as.png")
			);
		expSprTo.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		expSprTo.setIcon(smallKeyAs);
		fileMenu.add(expSprTo);

		// separator
		fileMenu.addSeparator();

		// SPR save
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
		buildAbout();
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
				new FileNameExtensionFilter("ALttP Sprite files (.spr)", EXPORT_EXTS);
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

		// display frame
		frame.setPreferredSize(d);
		frame.setMinimumSize(d);
		setAllSizes(frame, d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(200, 100);
		frame.setVisible(true);

		// repainting on all sprite changes
		SpriteChangeListener repainter =
				arg0 -> {
					indexMapEditor.refreshPalette();
					frame.repaint();
				};

		indexMapEditor.addSpriteChangeListener(repainter);
		pal.addSpriteChangeListener(repainter);
		mySprite.addSpriteChangeListener(repainter);

		// Action listeners for controls
		skinPick.addActionListener(
				arg0 -> pal.setSkinColor((ColorPair) skinPick.getSelectedItem())
			);

		mailPick.addActionListener(
				arg0 -> mySprite.setMail(mailPick.getSelectedIndex())
			);

		acc1Pick.addActionListener(
				arg0 -> mySprite.setAccessory((SpritePart) acc1Pick.getSelectedItem(), 1)
			);

		acc1Edit.addActionListener(
			arg0 ->	{
				SpritePart picked = (SpritePart) acc1Pick.getSelectedItem();
				if (picked == SpritePart.NOTHING) {
					indexMapEditor.editNewPart(null);
				} else {
					indexMapEditor.editNewPart(picked);
				}
			});

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
					explorer.setROMStatus(false);
					removeFilters(explorer);
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
							if(!n.contains(".") && n.length() > 0) { // no filetype, append spr
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
						byte[] data = mySprite.makeSprite();
						try {
							SpriteManipulator.writeSPR(data, lastSpritePath.toString());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame,
									"Error exporting to a SPR file.",
									"WOW",
									JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						}
					} else { // if nowhere to export, click "export to" to prompt a file selection
						expSprTo.doClick();
					}
				});

		expSprTo.addActionListener(
				arg0 -> {
					explorer.setROMStatus(false);
					removeFilters(explorer);
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
							if(!n.contains(".") && n.length() > 0) { // no filetype, append spr
								n = n + ".spr";
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
					explorer.setROMStatus(true);
					removeFilters(explorer);
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
						e.printStackTrace();
						return;
					}
					
					byte[] data = mySprite.makeSprite();
					try {
						SpriteManipulator.patchRom(data, n);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
								"Error patching rom",
								"UH-OH SPAGHETTI-Os",
								JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
						return;
					}
				});
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

	/**
	 * Removes all filters from an explorer.
	 * @param ex
	 */
	private static void removeFilters(JFileChooser ex) {
		FileFilter[] exlist = ex.getChoosableFileFilters();
		for (FileFilter r : exlist) {
			ex.removeChoosableFileFilter(r);
		}
	}

	// about frame built here
	static final JFrame aboutFrame = new JFrame("Acknowledgements");
	private static void buildAbout() {
		final TextArea peepsList = new TextArea("", 0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		peepsList.setEditable(false);
		peepsList.append("Written by fatmanspanda"); // hey, that's me

		peepsList.append("\n\nArt:\n");
		peepsList.append(GUIHelpers.join(new String[]{
				"iBazly",
				"Fish"
				}, ", "));

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
	/**
	 * Need alternating Confirm messages
	 */
	private static class BetterJFileChooser extends JFileChooser {
		private static final long serialVersionUID = -7581065406880416887L;
		private boolean patchingROM;
		public void approveSelection() {
			File f = getSelectedFile();
			if(f.exists() && getDialogType() == SAVE_DIALOG){
				String warning;
				String warningHeader;
				if (patchingROM) {
					warning =
							"Are you sure you want to patch \"" + f.getName() + "\"?\n"+
							"This cannot be undone.";
					warningHeader = "This is a ROM";
				} else {
					warning = "The file \"" + f.getName() + "\" already exists. Save anyway?";
					warningHeader = "Existing file";
				}
				int result = JOptionPane.showConfirmDialog(
						this,
						warning,
						warningHeader,
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch(result){
					case JOptionPane.YES_OPTION :
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION :
					case JOptionPane.CLOSED_OPTION :
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
				}
			}
			super.approveSelection();
		}
		
		public void setROMStatus(boolean b) {
			patchingROM = b;
		}
	};
}