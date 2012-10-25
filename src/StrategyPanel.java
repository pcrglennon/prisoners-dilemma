import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StrategyPanel extends JPanel {

    JTextArea strategyText;
    
    JScrollPane strategyTextScrollPane;

    public StrategyPanel() {
	super();
	


	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	strategyText = new JTextArea();
	strategyText.setEditable(false);
	strategyText.setLineWrap(true);
	strategyText.setWrapStyleWord(true);
	strategyText.setBackground(new Color(245, 210, 149));

	strategyTextScrollPane = new JScrollPane(strategyText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	add(strategyTextScrollPane);


	setPreferredSize(new Dimension(300,200));
    }

    public void setRuleText(String[] ruleString) {
	strategyText.setText(null);
	String rules = "";
	for(String rule: ruleString) {
	    if(rule == null) {
		break;
	    }
	    rules += rule + "\n";
	}
	strategyText.setText(rules);
	strategyText.setCaretPosition(0);
    }

}