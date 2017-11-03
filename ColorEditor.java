package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import SpriteAnimator.GUIHelpers;

public class ColorEditor extends Container {
	private static final long serialVersionUID = -6002186857197093009L;
	private Palette pal;
	private boolean editable;
	private SplotchBlob blob = new SplotchBlob();
	private int curIndex = 0;
	private final JLabel indexLabel = new JLabel("0");
	private static final Dimension prefDim = new Dimension(400,550);
	private static String[] INSTRUCTION_STYLE = {
			"padding: 10px 10px 10px 0px"
	};
	/**
	 * 
	 * @param p
	 */
	public ColorEditor(Palette p) {
		super();
		pal = p;
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
		editNewColor(curIndex);
		initializeDisplay();
	}

	/**
	 * 
	 * @param i
	 */
	public void editNewColor(int i) {
		curIndex = i;
		editable = Palette.editableIndex(i);
		blob.setEditable(editable);
		blob.setColors(pal.splotchesForIndex(i));
		indexLabel.setText(i+"");
		this.revalidate();
		repaint();
	}

	/**
	 * 
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

		final JLabel a = new JLabel();

		a.setText("<html>" +
				"<div style=\"" + GUIHelpers.join(INSTRUCTION_STYLE, ";") + "\">" +
				"Use this area to edit the colors at your selected index. " +
				"Note that SNES colors are 15-bit, so RGB values will be rounded down to the nearest multiple of 8." +
				"<br /><br />" +
				"Checking \"Use color for all mails\" will apply the first color to every mail when selecting \"Apply\"." +
				"</div>" +
				"</html>");
		l.putConstraint(SpringLayout.WEST, a, 0,
				SpringLayout.WEST, curEditing);
		l.putConstraint(SpringLayout.EAST, a, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.NORTH, a, 0,
				SpringLayout.SOUTH, curEditing);
		this.add(a);


		l.putConstraint(SpringLayout.WEST, blob, 0,
				SpringLayout.WEST, this);
		l.putConstraint(SpringLayout.SOUTH, blob, -20,
				SpringLayout.SOUTH, this);
		this.add(blob);
	}
}