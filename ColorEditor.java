package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class ColorEditor extends Container {
	private static final long serialVersionUID = -6002186857197093009L;
	private Palette pal;
	private JPanel colorArea;
	private boolean editable;
	private SplotchBlob blob = new SplotchBlob();
	private int curIndex = 0;
	private final JLabel indexLabel = new JLabel("0");
	private static final Dimension prefDim = new Dimension(400,600);

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

		l.putConstraint(SpringLayout.EAST, blob, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.SOUTH, blob, 0,
				SpringLayout.SOUTH, this);
		this.add(blob);
		
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
	}
}