import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.*;

/**
 * Holds the two configuration items:
 *    1) Checkbox to ensure a prisoner's dilemma payoff structure
 *    2) NumericTextField for number of rounds to be played
 */

public class SelectionPanel extends JPanel {

    private JLabel ensurePDLabel;
    private JCheckBox ensurePD;
    
    private JLabel roundsLabel;
    private NumericTextField numRounds;

    public SelectionPanel() {
	super();
	
	ensurePDLabel = new JLabel("Ensure Prisoner's Dilemma:");
	ensurePD = new JCheckBox();
	ensurePD.setSelected(true);

	numRounds = new NumericTextField(3);
	numRounds.setText("10");

	roundsLabel = new JLabel("Number of Rounds:");

	addComponentsToPanel();
    }
    
    private void addComponentsToPanel() {
	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	Insets padding = new Insets(20, 20, 20, 20);
	Insets noPadding = new Insets(0,0,0,0);

	c.insets = noPadding;
	c.gridx = 0;
	c.gridy = 0;
	add(ensurePDLabel);

	c.gridx = 1;
	c.gridy = 0;
	add(ensurePD, c);
	
	c.gridx = 0;
	c.gridy = 1;
	add(roundsLabel, c);

	c.gridx = 1;
	c.gridy = 1;
	add(numRounds, c);
    }

    public boolean ensureDilemma() {
	return (ensurePD.isSelected());
    }

    public int getNumRounds() {
	int toReturn;
	try {
	    toReturn = Integer.parseInt(numRounds.getText());
	} catch (Exception e) {
	    toReturn = 0;
	}
	return toReturn;
    }
    
}