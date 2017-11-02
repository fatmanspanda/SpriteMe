package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class ColorEditor extends Container {
	private static final long serialVersionUID = -6002186857197093009L;

	private byte[] originalColorMap = {};
	private byte[] colorMap = {};
	private Palette pal;
	private JPanel colorArea;
	private boolean editable;
	private SplotchBlob blob = new SplotchBlob();
	private static final Dimension prefDim = new Dimension(350,200);

	/**
	 * 
	 * @param p
	 */
	public ColorEditor(Palette p) {
		super();
		pal = p;
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
		editNewColor(0);
		initializeDisplay();
	}
	
	/**
	 * 
	 * @param i
	 */
	public void editNewColor(int i) {
		editable = Palette.editableIndex(i);
		blob.setColors(pal.splotchesForIndex(i));
	}
	
	/**
	 * 
	 */
	private void initializeDisplay() {
		// add palette area
		SpringLayout l = new SpringLayout();
		this.setLayout(l);

		colorArea = new JPanel(new GridBagLayout());
		l.putConstraint(SpringLayout.EAST, colorArea, 0,
				SpringLayout.EAST, this);
		l.putConstraint(SpringLayout.SOUTH, colorArea, 0,
				SpringLayout.SOUTH, this);
		this.add(colorArea);
	}
}
