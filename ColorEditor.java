package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import SpriteAnimator.GUIHelpers;

public class ColorEditor extends Container {
	private static final long serialVersionUID = -6002186857197093009L;

	private static final Dimension prefDim = new Dimension(400,550);
	private static final String[] INSTRUCTION_STYLE = {
			"padding: 10px 10px 10px 0px"
	};

	private static final String CANNOT = "This color index cannot be edited.";
	private static final String CAN = "";
	private static final String GLOVES_TEMPLATE =
			"This color overrides index 13 when Link has obtained the %s.";
	private static final String GLOVES_EDIT_TEXT = String.format(GLOVES_TEMPLATE, "power gloves");
	private static final String MITTS_EDIT_TEXT = String.format(GLOVES_TEMPLATE, "mitts");

	// locals
	private Palette pal;
	private boolean editable;
	private int curIndex = 0;
	private final JLabel indexLabel = new JLabel("0");
	private final JLabel editableText = new JLabel("");
	private SplotchBlob blob;
	/**
	 * Creates a new editor attached to a palette.
	 * @param p
	 */
	public ColorEditor(PresetSplotchChooser chooser, Palette p) {
		super();
		blob = new SplotchBlob(chooser);
		pal = p;
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
		editNewColor(curIndex);
		initializeDisplay();
	}

	/**
	 * Sets a new index to edit.
	 * @param i
	 */
	public void editNewColor(int i) {
		curIndex = i;
		editable = Palette.editableIndex(i);
		blob.setColors(pal.splotchesForIndex(i));
		blob.setEnabled(editable);
		switch (i) {
			case 16 :
				editableText.setText(GLOVES_EDIT_TEXT);
				blob.setGloveMode();
				indexLabel.setText("G");
				break;
			case 17 :
				editableText.setText(MITTS_EDIT_TEXT);
				blob.setGloveMode();
				indexLabel.setText("M");
				break;
			default :
				editableText.setText(editable ? CAN : CANNOT);
				indexLabel.setText(i+"");
				break;
		}

		this.revalidate();
		repaint();
	}
	/**
	 * Sets up child components.
	 */
	private void initializeDisplay() {
		// add palette area
		SpringLayout l = new SpringLayout();
		this.setLayout(l);

		final JLabel curEditing = new JLabel("Currently editing colors at index : ");
		l.putConstraint(SpringLayout.EAST, curEditing, 0,
				SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.NORTH, curEditing, 0,
				SpringLayout.NORTH, this);
		this.add(curEditing);

		l.putConstraint(SpringLayout.WEST, indexLabel, 0,
				SpringLayout.EAST, curEditing);
		l.putConstraint(SpringLayout.NORTH, indexLabel, 0,
				SpringLayout.NORTH, this);
		this.add(indexLabel);

		final JLabel helpText = new JLabel();

		helpText.setText("<html>" +
				"<div style=\"" + GUIHelpers.join(INSTRUCTION_STYLE, ";") + "\">" +
				"Use this area to edit the colors at your selected index. " +
				"Note that SNES colors are 15-bit, so RGB values will be rounded down to the nearest multiple of 8." +
				"<br /><br />" +
				"Checking \"Use color for all mails\" will apply the first color to every mail when selecting \"Apply\"." +
				"</div>" +
				"</html>");
		l.putConstraint(SpringLayout.WEST, helpText, 0,
				SpringLayout.WEST, curEditing);
		l.putConstraint(SpringLayout.EAST, helpText, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.NORTH, helpText, 0,
				SpringLayout.SOUTH, curEditing);
		this.add(helpText);

		l.putConstraint(SpringLayout.WEST, editableText, 0,
				SpringLayout.WEST, curEditing);
		l.putConstraint(SpringLayout.EAST, editableText, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.NORTH, editableText, 10,
				SpringLayout.SOUTH, helpText);
		this.add(editableText);

		l.putConstraint(SpringLayout.EAST, blob, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.SOUTH, blob, 0,
				SpringLayout.SOUTH, this);
		this.add(blob);
	} // end display initialization
}