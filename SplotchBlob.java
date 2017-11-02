package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

public class SplotchBlob extends Container {
	private static final long serialVersionUID = 5958661421574061169L;

	private Splotch[] workingSet;
	private boolean editable = true;
	private static final Dimension prefDim = new Dimension(350,400);
	public SplotchBlob() {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(prefDim);
		this.setMinimumSize(prefDim);
	}

	public void setColors(Splotch[] set) {
		workingSet = set;
		resetEditArea();
		repaint();
	}

	public void setEditable(boolean e) {
		editable = e;
	}

	private void resetEditArea() {
		for (int i = 2; i < 6; i++) {
			try{
				this.remove(i);
			} catch (Exception e) {
				
			}
		}
		GridBagConstraints l = new GridBagConstraints();
		this.revalidate();
		l.gridx = 0;
		l.gridy = 0;
		int i = 0;
		for (Splotch s : workingSet) {
			this.add(new SplotchEditor(s,editable),l,i);
			i++;
			l.gridy++;
		}
	}
}