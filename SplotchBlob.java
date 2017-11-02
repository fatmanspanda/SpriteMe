package SpriteMe;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class SplotchBlob extends Container {
	private static final long serialVersionUID = 5958661421574061169L;

	private Splotch[] workingSet;
	private boolean editable = true;
	public SplotchBlob() {}

	public void setColors(Splotch[] set) {
		workingSet = set;
		resetEditArea();
	}

	public void setEditable(boolean e) {
		editable = e;
	}

	private void resetEditArea() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints l = new GridBagConstraints();
		l.gridx = 0;
		l.gridy = 0;
		for (Splotch s : workingSet) {
			this.add(new SplotchEditor(s,editable),l);
			l.gridy++;
		}
	}
}