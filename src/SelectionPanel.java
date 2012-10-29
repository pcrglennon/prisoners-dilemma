import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.*;


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

	numRounds = new NumericTextField();
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

/**
* Textfield which only accepts numeric input.
*
* Also limits the input to no more than 3 digits.
* 
* From www.coderanch.com/how-to/java/NumericTextField
*/

class NumericTextField extends JTextField {

    public NumericTextField() {
	super();
	setColumns(3);
    }

    @Override
    protected Document createDefaultModel() {
	return new NumericDocument();
    }
    
    private static class NumericDocument extends PlainDocument {

	private final static int MAX_DIGITS = 3;
	
	private final static Pattern DIGITS = Pattern.compile("\\d*");
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	    if(str != null && DIGITS.matcher(str).matches() && getLength() + str.length() <= MAX_DIGITS) {
		super.insertString(offs, str, a);
	    }
	}
    }
}

