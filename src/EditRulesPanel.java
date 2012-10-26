import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class EditRulesPanel extends JPanel {

    private Player player;

    private JLabel editRulesLabel;

    private JPanel ruleListPanel;
    private Border ruleListPanelBorder;

    private StrategyPanel strategyPanel;

    private NewRulePanel newRulePanel;
    private Border newRulePanelBorder;
    
    public EditRulesPanel(Player player) {
	super();
	this.player = player;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	Font labelFont = new Font("SansSerif", Font.BOLD, 18);
	editRulesLabel = new JLabel();
	editRulesLabel.setFont(labelFont);
	editRulesLabel.setText("Edit " + player.getID() + " Player's Rules");
	editRulesLabel.setAlignmentX(Component.TOP_ALIGNMENT);
	editRulesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	add(editRulesLabel);

	ruleListPanelBorder = BorderFactory.createLineBorder(Color.BLACK);
	ruleListPanel = new JPanel();
	ruleListPanel.setBorder(ruleListPanelBorder);

	strategyPanel = new StrategyPanel();
	strategyPanel.setRuleText(player.getRuleString());
	add(strategyPanel);

	add(ruleListPanel);
	
	newRulePanelBorder = BorderFactory.createLineBorder(Color.BLACK);
	newRulePanel = new NewRulePanel();
	newRulePanel.setBorder(newRulePanelBorder);
	add(newRulePanel, BorderLayout.PAGE_END);
    }

    public void updateStrategyPanel() {
	strategyPanel.setRuleText(player.getRuleString());
    }
}

class NewRulePanel extends JPanel implements ActionListener {

    private JPanel addRuleButtonsPanel;
    private JButton addRuleOneB;
    private JButton addRuleTwoB;

    private JPanel ruleFormatsPanel;

    private AddRuleFormatOnePanel rfOnePanel;

    private JPanel addRuleFormatTwoPanel;
    private JLabel alwaysLabel;
    private JComboBox<String> defectCooperateBox;

    public NewRulePanel() {
	super();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	rfOnePanel = new AddRuleFormatOnePanel();
	add(rfOnePanel);

	addRuleOneB = new JButton("Add");
	addRuleOneB.addActionListener(this);
	add(addRuleOneB);

	JPanel rfTwoPanel = new JPanel();
	rfTwoPanel.setBackground(Color.ORANGE);
	alwaysLabel = new JLabel("Always");
	rfTwoPanel.add(alwaysLabel, BorderLayout.PAGE_START);
	
	addRuleTwoB = new JButton("Add");
	addRuleTwoB.addActionListener(this);
	add(addRuleTwoB);
	
	String[] moveList = {"Cooperate", "Defect", "Opp. Prev.", "! Opp. Prev.", "Your Prev.", "! Your Prev", "Anything"};
	defectCooperateBox = new JComboBox<String>(moveList);
	rfTwoPanel.add(defectCooperateBox, BorderLayout.CENTER);
	add(rfTwoPanel);
    }

    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Container Class for the long-format rule our agents use
     */

    class AddRuleFormatOnePanel extends JPanel {

	private final static int CONFIG_MAX_MOVES = 10;
	private final static int CONFIG_MOVES_INCREMENT = 1;
	
	private JComboBox<String> ifUntilBox;
	private JComboBox<String> playerBox;
	private JComboBox<String> previousMoveTypeBox;
	private JComboBox<String> overUnderBox;
	private JComboBox<String> previousMovePercentBox;
	private JComboBox<String> previousNumMovesBox;
	private JComboBox<String> futureNumMovesBox;
	private JComboBox<String> futureMoveTypeBox;

	private JLabel moveIsLabel = new JLabel("move is");
	private JLabel forLabel = new JLabel("for");
	private JLabel percentOfLabel = new JLabel("% of the previous");
	private JLabel movesThenLabel = new JLabel("moves then queue");
	private JLabel turnOfLabel = new JLabel("turn of");

	public AddRuleFormatOnePanel() {
	    super();
	    
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    String[] ifUntilList = {"If", "Until"};
	    String[] playerList = {"Your", "Opp."};
	    String[] moveList = {"Cooperate", "Defect", "Opp. Prev.", "! Opp. Prev.", "Your Prev.", "! Your Prev.", "Anything"};
	    String[] overUnderList = {"At least", "Under"};
	    String[] percentageList = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
	    
	    ifUntilBox = new JComboBox<String>(ifUntilList);
	    playerBox = new JComboBox<String>(playerList);
	    previousMoveTypeBox = new JComboBox<String> (moveList);
	    previousMovePercentBox = new JComboBox<String> (percentageList);

	    previousNumMovesBox = new JComboBox<String>();
	    futureNumMovesBox = new JComboBox<String>();
	    setupNumMovesBox(previousNumMovesBox);
	    setupNumMovesBox(futureNumMovesBox);

	    futureMoveTypeBox = new JComboBox<String>(moveList);

	    add(ifUntilBox);
	    add(Box.createVerticalGlue());
	    add(playerBox);
	    add(Box.createVerticalGlue());
	    add(moveIsLabel);
	    add(Box.createVerticalGlue());
	    add(previousMoveTypeBox);
	    add(Box.createVerticalGlue());
	    add(forLabel);
	    add(Box.createVerticalGlue());
	    add(previousMovePercentBox);
	    add(Box.createVerticalGlue());
	    add(percentOfLabel);
	    add(Box.createVerticalGlue());
	    add(previousNumMovesBox);
	    add(Box.createVerticalGlue());
	    add(movesThenLabel);
	    add(Box.createVerticalGlue());
	    add(futureNumMovesBox);
	    add(Box.createVerticalGlue());
	    add(turnOfLabel);
	    add(Box.createVerticalGlue());
	    add(futureMoveTypeBox);
	}

	/**
	 * Returns the variables with define a rule, with each variable delimited
	 * by the '|' character
	 */

	public String getRuleString() {
	    return((String)ifUntilBox.getSelectedItem() + "|" +
		   (String)playerBox.getSelectedItem() + "|" + 
		   (String)previousMoveTypeBox.getSelectedItem() + "|" +
		   (String)previousMovePercentBox.getSelectedItem() + "|" +
		   (String)previousNumMovesBox.getSelectedItem() + "|" +
		   (String)futureNumMovesBox.getSelectedItem() + "|" +
		   (String)futureMoveTypeBox.getSelectedItem());
	}

	private void setupNumMovesBox(JComboBox<String> numMovesBox) {
	    for(int i = 1; i <= CONFIG_MAX_MOVES; i += CONFIG_MOVES_INCREMENT) {
		numMovesBox.addItem("" + i);
	    }
	}
    }
}