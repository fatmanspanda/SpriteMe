package SpriteMe;

import java.awt.Container;

import javax.swing.JTextArea;

public class SplotchBlob extends Container {
	private static final long serialVersionUID = 5958661421574061169L;

	private Splotch[] workingSet;
	private int[][] colorVals;
	private JTextArea[][] colorValBoxes;
	public SplotchBlob() {
		
	}

	public void setColors(Splotch[] set) {
		workingSet = set;
	}
}
