import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.*;

/**
 * The payoff matrix, made up of 4 cells containing the payoffs for both row and column players
 */

public class PayoffPanel extends JPanel implements FocusListener {
    
    private PayoffCell topLeft;
    private PayoffCell topRight;
    private PayoffCell bottomLeft;
    private PayoffCell bottomRight;

    private boolean isValid = false;

    public PayoffPanel() {
	super();

	setBackground(Config.DARK_GRAY);

	setLayout(new GridLayout(3,3));

	topLeft = new PayoffCell("8,8");
	topRight = new PayoffCell("0,13");
	bottomLeft = new PayoffCell("13,0");
	bottomRight = new PayoffCell("3,3");

	topLeft.setFont(Config.PAYOFF_FONT);
	topRight.setFont(Config.PAYOFF_FONT);
	bottomLeft.setFont(Config.PAYOFF_FONT);
	bottomRight.setFont(Config.PAYOFF_FONT);

	topLeft.addFocusListener(this);
	topRight.addFocusListener(this);
	bottomLeft.addFocusListener(this);
	bottomRight.addFocusListener(this);

	add(new JLabel(""));
	JLabel cooperateLabelOne = new JLabel("Cooperate");
	cooperateLabelOne.setAlignmentX(Component.CENTER_ALIGNMENT);
	cooperateLabelOne.setForeground(Config.LABEL_COLOR);
	add(cooperateLabelOne);
	JLabel defectLabelOne = new JLabel("Defect");
	defectLabelOne.setAlignmentX(Component.CENTER_ALIGNMENT);
	defectLabelOne.setForeground(Config.LABEL_COLOR);
	add(defectLabelOne);
	JLabel cooperateLabelTwo = new JLabel("Cooperate");
	cooperateLabelTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
	cooperateLabelTwo.setForeground(Config.LABEL_COLOR);
	add(cooperateLabelTwo);
	add(topLeft);
	add(topRight);
	JLabel defectLabelTwo = new JLabel("Defect");
	defectLabelTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
	defectLabelTwo.setForeground(Config.LABEL_COLOR);
	add(defectLabelTwo);
	add(bottomLeft);
	add(bottomRight);
    }
    
    /**
     * Gets all payoffs from the PayoffCells, and returns them as a 2D array
     * Will return null if any PayoffCell is empty
     */
    public int[][] getAllPayoffs() {
	int[][] allPayoffs = new int[4][2];
	allPayoffs[0] = topLeft.getPayoffs();
	allPayoffs[1] = topRight.getPayoffs();
	allPayoffs[2] = bottomLeft.getPayoffs();
	allPayoffs[3] = bottomRight.getPayoffs();
	if(allPayoffs[0] == null || allPayoffs[1] == null || allPayoffs[2] == null || allPayoffs[3] == null) {
	    return null;
	}
	return allPayoffs;
    }

    public void focusGained(FocusEvent e) {}

    /**
     * After a cell is filled, validates the contents of the cell
     */
    public void focusLost(FocusEvent e) {
	if(e.getSource() instanceof PayoffCell) {
	    PayoffCell pc = (PayoffCell)e.getSource();
	    if(pc.validateCell()) {
		isValid = true;
	    } else {
		isValid = false;
		pc.setText("");
	    }
	}
    }
}

/**
 * Similar to the NumericTextField class, which was taken from:
 * www.coderanch.com/how-to/java/NumericTextField
 */

class PayoffCell extends JTextField {

    //Text inside a cell must match this format:
    //1-3 digits (row payoff) followed by "," followed by 1-3 digits (column payoff)
    private final static Pattern FORMAT = Pattern.compile("^\\d{1,3},\\d{1,3}$");
    
    private boolean autofill = true;

    public PayoffCell(String payoffString) {
	super();
	setDocument(createDefaultModel());
	setHorizontalAlignment(JTextField.CENTER);
	setPreferredSize(Config.PAYOFF_CELL_PREF_SIZE);
	setText(payoffString);
	//After autofilling with payoffString:
	//require cell input to be validated (see PayoffCell.insertString)
	autofill = false;
    }

    public boolean validateCell() {
	if(FORMAT.matcher(getText()).matches()) {
	    return true;
	}
	return false;
    }

    public int[] getPayoffs() {
	//Validate the cell format first
	if(!validateCell()) {
	    return null;
	}
	String[] payoffStrings = getText().split(",");
	int[] payoffs = new int[2];
	try {
	    payoffs[0] = Integer.parseInt(payoffStrings[0]);
	    payoffs[1] = Integer.parseInt(payoffStrings[1]);
	} catch (Exception e) {
	    //Cell is empty
	    payoffs = null;
	}
	return payoffs;
    }

    @Override
    protected Document createDefaultModel() {
	return new PayoffDocument();
    }

    private class PayoffDocument extends PlainDocument {
	
	private final Pattern DIGITS = Pattern.compile("[\\d*,]");
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	    //Allow cell to be autofilled at initialization
	    if(autofill) {
		super.insertString(offs, str, a);
	    }
	    else if(str != null && DIGITS.matcher(str).matches()) {
		super.insertString(offs, str, a);
	    }
	}

    }
}
