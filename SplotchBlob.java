package SpriteMe;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class SplotchBlob extends Container {
	// class constants
	private static final long serialVersionUID = 5958661421574061169L;
	private static final Border padding = BorderFactory.createEmptyBorder(1,2,1,4);
	private final JLabel[] labels = new JLabel[] {
			new JLabel("Green mail", SwingConstants.RIGHT),
			new JLabel("Blue mail", SwingConstants.RIGHT),
			new JLabel("Red mail", SwingConstants.RIGHT),
			new JLabel("Bunny", SwingConstants.RIGHT)
	};

	// local vars
	private Splotch[] workingSet;
	private boolean editable = true;
	private final JCheckBox allMails = new JCheckBox("Use color or all mails");
	private SplotchEditor[] editors = new SplotchEditor[4];

	/**
	 * Creates a new {@code SplotchBlob}
	 */
	public SplotchBlob() {
		this.setLayout(new GridBagLayout());
		for (JLabel l : labels) {
			l.setBorder(padding);
		}
		allMails.addPropertyChangeListener(checkListen());
	}

	/**
	 * Sets the blob to edit a new set of {@code Splotch} objects.
	 * @param set
	 */
	public void setColors(Splotch[] set) {
		workingSet = set;
		resetEditArea();
		repaint();
	}

	/**
	 * Disable the all toggle for gloves
	 */
	public void setGloveMode() {
		allMails.setSelected(true);
		allMails.setEnabled(false);
	}

	/**
	 * 
	 */
	public void setEnabled(boolean b) {
		editable = b;
		allMails.setEnabled(b);
		allMails.setSelected(!b);
		for (Component c : this.getComponents()) {
			c.setEnabled(b);
		}
	}

	/**
	 * Resets all {@code SplotchEditor} objects and revalidates the GUI.
	 */
	private void resetEditArea() {
		this.removeAll();
		GridBagConstraints l = new GridBagConstraints();
		this.revalidate();
		l.gridy = 0;
		l.gridwidth = 2;
		this.add(allMails,l);
		l.gridy = 1;
		l.ipady = 10;
		l.gridwidth = 1;
		int i = 0;
		for (Splotch s : workingSet) {
			l.gridx = 0;
			this.add(labels[i],l);
			l.gridx = 1;
			editors[i] = new SplotchEditor(s,editable);
			this.add(editors[i],l);
			l.gridy++;
			i++;
		}
		editors[0].setFourVictims(workingSet);
	}

	/**
	 * @return A {@link PropertyChangeListener} for the "all mails" button
	 * that enables/disables the latter 3 editors appropriately.
	 */
	private PropertyChangeListener checkListen() {
		return new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				JCheckBox source = (JCheckBox) e.getSource();
				boolean all = source.isSelected();
				try {
					editors[0].editAllMails(all);
				} catch (Exception e1) { return; }
				for (int i = 1; i < 4; i++) {
					editors[i].setEnabled(!all);
					labels[i].setEnabled(!all);
				}
			}
		};
	}
}