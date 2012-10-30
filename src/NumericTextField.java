import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.text.*;

/**
* Textfield which only accepts numeric input.
* 
* From www.coderanch.com/how-to/java/NumericTextField
*/
class NumericTextField extends JTextField {

    //Maximum number of digits allowed as input
    //Also used as the number of columns in the text field
    private int maxDigits;

    public NumericTextField(int maxDigits) {
	super();
	this.maxDigits = maxDigits;
	setDocument(createDefaultModel());
	setColumns(maxDigits);
    }

    @Override
    protected Document createDefaultModel() {
	return new NumericDocument();
    }

    /**
     * Will only accept numbers as input
     */
    
    private class NumericDocument extends PlainDocument {
	
	private final Pattern DIGITS = Pattern.compile("\\d*");
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	    if(str != null && DIGITS.matcher(str).matches() && getLength() + str.length() <= maxDigits) {
		super.insertString(offs, str, a);
	    }
	}
    }
}