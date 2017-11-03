package SpriteMe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import SpriteManipulator.SpriteManipulator;

/*
 * TODO : Lighten / darken buttons
 * TODO : Padding and borders
 * TODO : Fix "custom color"
 * TODO : Labelling
 * TODO : You cannot edit this color
*/
public class SplotchEditor extends Container {
	private static final long serialVersionUID = -5665184823715239064L;

	private final GridBagLayout w = new GridBagLayout();
	private final Splotch victim;
	private Splotch[] allFourVictims;
	private boolean isAllFour = false;
	private boolean enabled;
	private final int[] RGB;
	private final SpriteColor originalColor;
	private final JSlider[] sliders;
	private final JFormattedTextField[] vals;
	private final JSlider red;
	private final JSlider green;
	private final JSlider blue;
	private final JFormattedTextField redT;
	private final JFormattedTextField greenT;
	private final JFormattedTextField blueT;
	private final JButton confirm = new JButton("Set color");
	private final JButton reset = new JButton("Reset");
	private String colorName = "Custom color";
	private final ColorPreview p = new ColorPreview();
	private final JComboBox<SpriteColor> presets = new JComboBox<SpriteColor>(SpriteColor.CONSTANTS);
	private static final Dimension sliderD = new Dimension(120, 10);
	private static final Dimension textD = new Dimension(40, 20);
	private static final Dimension labelD = new Dimension(20, 20);
	public SplotchEditor(Splotch c, boolean e) {
		presets.setSelectedItem(null);
		victim = c;
		enabled = e;
		RGB = c.getColorVals();
		originalColor = new SpriteColor(c.getName(), RGB[0], RGB[1], RGB[2]); // new object so it never changes
		sliders = new JSlider[] {
				new JSlider(JSlider.HORIZONTAL, 0, 31, RGB[0]/8),
				new JSlider(JSlider.HORIZONTAL, 0, 31, RGB[1]/8),
				new JSlider(JSlider.HORIZONTAL, 0, 31, RGB[2]/8)
			};
		vals = new JFormattedTextField[3];
		try {
			vals[0] = new JFormattedTextField(new MaskFormatter("###"));
			vals[1] = new JFormattedTextField(new MaskFormatter("###"));
			vals[2] = new JFormattedTextField(new MaskFormatter("###"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		red = sliders[0];
		green = sliders[1];
		blue = sliders[2];
		redT = vals[0];
		greenT = vals[1];
		blueT = vals[2];
		red.setMaximumSize(sliderD);
		red.setPreferredSize(sliderD);
		green.setMaximumSize(sliderD);
		green.setPreferredSize(sliderD);
		blue.setMaximumSize(sliderD);
		blue.setPreferredSize(sliderD);
		redT.setValue(RGB[0]);
		greenT.setValue(RGB[1]);
		blueT.setValue(RGB[2]);
		p.setColor(RGB);
		initializeDisplay();
		addListeners();
	}

	private void initializeDisplay() {
		this.setLayout(w);
		setEnabling();
		// red
		GridBagConstraints l = new GridBagConstraints();
		l.fill = GridBagConstraints.HORIZONTAL;

		// sizes
		red.setMaximumSize(sliderD);
		red.setPreferredSize(sliderD);
		green.setMaximumSize(sliderD);
		green.setPreferredSize(sliderD);
		blue.setMaximumSize(sliderD);
		blue.setPreferredSize(sliderD);

		redT.setMaximumSize(textD);
		redT.setPreferredSize(textD);
		greenT.setMaximumSize(textD);
		greenT.setPreferredSize(textD);
		blueT.setMaximumSize(textD);
		blueT.setPreferredSize(textD);

		// preview
		l.gridy = 0;
		l.gridx = 0;
		l.gridheight = 3;
		this.add(p, l);

		l.gridheight = 1;
		// red
		l.gridy = 0;
		final JLabel lr = new JLabel("R", SwingConstants.CENTER);
		lr.setPreferredSize(labelD);
		lr.setMinimumSize(labelD);
		l.gridx = 1;
		this.add(lr,l);
		l.gridx = 2;
		this.add(red, l);
		l.gridx = 3;
		this.add(redT, l);

		l.gridx = 4;
		this.add(confirm, l);

		// green
		l.gridy = 1;
		final JLabel lg = new JLabel("G", SwingConstants.CENTER);
		lg.setPreferredSize(labelD);
		lg.setMinimumSize(labelD);
		l.gridx = 1;
		this.add(lg,l);
		l.gridx = 2;
		this.add(green, l);
		l.gridx = 3;
		this.add(greenT, l);

		l.gridx = 4;
		this.add(reset, l);

		// blue
		l.gridy = 2;
		final JLabel lb = new JLabel("B", SwingConstants.CENTER);
		lb.setPreferredSize(labelD);
		lb.setMinimumSize(labelD);
		l.gridx = 1;
		this.add(lb,l);
		l.gridx = 2;
		this.add(blue, l);
		l.gridx = 3;
		this.add(blueT, l);

		// preset colors
		final JLabel wordPreview = new JLabel("Preset colors:");
		l.gridy = 3;
		l.gridx = 0;
		l.gridwidth = 2;
		this.add(wordPreview, l);
		l.gridx = 2;
		this.add(presets,l);
	}

	private void addListeners() {
		ChangeListener slideListener = slideListen();
		ChangeListener repaintListener = repaintListen();
		for (JSlider s : sliders) {
			s.addChangeListener(slideListener);
			s.addChangeListener(repaintListener);
		}
		PropertyChangeListener textListener = textListen();
		for (JFormattedTextField v : vals) {
			v.addPropertyChangeListener(textListener);
		}

		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isAllFour && allFourVictims != null) {
					for (Splotch s : allFourVictims) {
						s.setColor(new SpriteColor(colorName,
								RGB[0],
								RGB[1],
								RGB[2])
								);
					}
				} else {
					victim.setColor(new SpriteColor(colorName,
							RGB[0],
							RGB[1],
							RGB[2])
							);
				}
			}});

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SplotchEditor.this.resetColor();
			}});

		presets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SpriteColor sel = (SpriteColor) presets.getSelectedItem();
				if (sel != null) {
					SplotchEditor.this.setColor(sel);
				}
				
			}});
	}

	private void resetColor() {
		setColor(originalColor);
		presets.setSelectedItem(null);
	}

	private  void setColor(SpriteColor c) {
		byte[] t = c.getRGB();
		RGB[0] = Byte.toUnsignedInt(t[0]);
		RGB[1] = Byte.toUnsignedInt(t[1]);
		RGB[2] = Byte.toUnsignedInt(t[2]);
		sliders[0].setValue(RGB[0]/8);
		sliders[1].setValue(RGB[1]/8);
		sliders[2].setValue(RGB[2]/8);
	}

	public void setEnabled(boolean e) {
		enabled = e;
		setEnabling();
	}

	private void setEnabling() {
		for (JSlider s : sliders) {
			s.setEnabled(enabled);
		}
		for (JFormattedTextField v : vals) {
			v.setEnabled(enabled);
		}
		presets.setEnabled(enabled);
		confirm.setEnabled(enabled);
		reset.setEnabled(enabled);
	}

	public void editAllMails(boolean b) {
		isAllFour = b;
	}
	
	public void setFourVictims(Splotch[] allFourVictims) {
		this.allFourVictims = allFourVictims;
	}

	private ChangeListener slideListen() {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int val = source.getValue();
				val *= 8;
				JFormattedTextField t = null;
				if (source == red) {
					RGB[0] = val;
					t = redT;
				} else if (source == green) {
					RGB[1] = val;
					t = greenT;
				} else if (source == blue) {
					RGB[2] = val;
					t = blueT;
				}
				t.setValue(val);
				t.setText(t+"");
			}
		};
	}

	private ChangeListener repaintListen() {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				p.setColor(RGB);
				p.repaint();
				colorName = "Custom color";
			}
		};
	}

	private PropertyChangeListener textListen() {
		return new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				JFormattedTextField source = (JFormattedTextField) e.getSource();
				int val = ((Number) source.getValue()).intValue();
				if (val < 0) {
					val = 0;
				} else if (val > 255) {
					val = 255;
				}
				val = SpriteManipulator.roundVal(val);
				JSlider s = null;
				if (source == redT) {
					RGB[0] = val;
					s = red;
				} else if (source == greenT) {
					RGB[1] = val;
					s = green;
				} else if (source == blueT) {
					RGB[2] = val;
					s = blue;
				}
				s.setValue(val/8);
			}
		};
	}
}