import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class EditRulesPanel extends JPanel {

    private Player player;

    private JLabel editRulesLabel;

    private JPanel ruleListPanel;

    private StrategyPanel strategyPanel;

    private NewRulePanel newRulePanel;
    
    public EditRulesPanel(Player player) {
	super();
	this.player = player;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	editRulesLabel = new JLabel();
	editRulesLabel.setFont(Config.LABEL_FONT);
	editRulesLabel.setText(player.getID() + " Player's Rules");
	editRulesLabel.setAlignmentX(Component.TOP_ALIGNMENT);
	editRulesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	add(editRulesLabel);

	strategyPanel = new StrategyPanel();
	strategyPanel.setRuleText(player.getRuleString());
	add(strategyPanel);
	
	newRulePanel = new NewRulePanel();
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
    
    private RuleFormatOnePanel rfOnePanel;

    private RuleFormatTwoPanel rfTwoPanel;

    public NewRulePanel() {
	super();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	rfOnePanel = new RuleFormatOnePanel();
	rfOnePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	add(rfOnePanel);

	add(Box.createVerticalStrut(5));
	addRuleOneB = new JButton("Add");
	addRuleOneB.addActionListener(this);
	add(addRuleOneB);

	add(Box.createVerticalStrut(15));
	rfTwoPanel = new RuleFormatTwoPanel();
	rfTwoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	add(rfTwoPanel);

	add(Box.createVerticalStrut(5));
	addRuleTwoB = new JButton("Add");
	addRuleTwoB.addActionListener(this);
	add(addRuleTwoB);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == addRuleOneB) {
	    System.out.println(rfOnePanel.getRuleString());
	}
	if(e.getSource() == addRuleTwoB) {
	    System.out.println(rfTwoPanel.getRuleString());
	}
    }

    /**
     * Container Class for the long-format rule our agents use
     */

    class RuleFormatOnePanel extends JPanel {

	private final static int CONFIG_MAX_MOVES = 10;
	private final static int CONFIG_MOVES_INCREMENT = 1;
	
	private JComboBox<Integer> priorityNumBox;
	private JComboBox<String> ifUntilBox;
	private JComboBox<String> playerBox;
	private JComboBox<String> previousMoveTypeBox;
	private JComboBox<String> overUnderBox;
	private JComboBox<String> previousMovePercentBox;
	private JComboBox<String> previousNumMovesBox;
	private JComboBox<String> futureNumMovesBox;
	private JComboBox<String> futureMoveTypeBox;

	private JLabel moveIsLabel = new JLabel("move is");
	private JLabel percentOfLabel = new JLabel("% of the previous");
	private JLabel movesThenLabel = new JLabel("moves then queue");
	private JLabel turnOfLabel = new JLabel("turn of");

	public RuleFormatOnePanel() {
	    super();
	    
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    Integer[] priorityNumList = {1,2,3,4,5,6,7,8,9,10};
	    String[] ifUntilList = {"If", "Until"};
	    String[] playerList = {"Your", "Opp."};
	    String[] overUnderList = {"At least", "Under"};
	    String[] percentageList = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
	    JPanel priorityPanel = new JPanel();
	    priorityPanel.setLayout(new FlowLayout());
	    JLabel priorityLabel = new JLabel("Priority");
	    priorityPanel.add(priorityLabel);
	    priorityNumBox = new JComboBox<Integer>(priorityNumList);
	    priorityPanel.add(priorityNumBox);

	    ifUntilBox = new JComboBox<String>(ifUntilList);
	    playerBox = new JComboBox<String>(playerList);
	    previousMoveTypeBox = new JComboBox<String>(Config.MOVE_LIST);
	    previousMovePercentBox = new JComboBox<String>(percentageList);
	    overUnderBox = new JComboBox<String>(overUnderList);
	    previousNumMovesBox = new JComboBox<String>();
	    futureNumMovesBox = new JComboBox<String>();
	    setupNumMovesBox(previousNumMovesBox);
	    setupNumMovesBox(futureNumMovesBox);
	    futureMoveTypeBox = new JComboBox<String>(Config.MOVE_LIST);

	    add(priorityPanel);
	    add(Box.createVerticalGlue());
	    add(ifUntilBox);
	    add(Box.createVerticalGlue());
	    add(playerBox);
	    add(Box.createVerticalGlue());
	    add(moveIsLabel);
	    add(Box.createVerticalGlue());
	    add(previousMoveTypeBox);
	    add(Box.createVerticalGlue());
	    add(overUnderBox);
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
	    return((int)priorityNumBox.getSelectedItem() + "|" +
		   (String)ifUntilBox.getSelectedItem() + "|" +
		   (String)playerBox.getSelectedItem() + "|" + 
		   (String)previousMoveTypeBox.getSelectedItem() + "|" +
		   (String)overUnderBox.getSelectedItem() + "|" +
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

    class RuleFormatTwoPanel extends JPanel{
	
	private JComboBox<Integer> priorityNumBox;

	private JComboBox<String> moveTypeBox;

	public RuleFormatTwoPanel() {
	    super();

	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    Integer[] priorityNumList = {1,2,3,4,5,6,7,8,9,10};
	    JPanel priorityPanel = new JPanel();
	    priorityPanel.setLayout(new FlowLayout());
	    JLabel priorityLabel = new JLabel("Priority");
	    priorityPanel.add(priorityLabel);
	    priorityNumBox = new JComboBox<Integer>(priorityNumList);
	    priorityPanel.add(priorityNumBox);
	    
	    add(priorityPanel);
	    
	    JPanel alwaysMovePanel = new JPanel();
	    alwaysMovePanel.setLayout(new FlowLayout());
	    JLabel alwaysLabel = new JLabel("Always");
	    alwaysMovePanel.add(alwaysLabel);
	    moveTypeBox = new JComboBox<String>(Config.MOVE_LIST);
	    alwaysMovePanel.add(moveTypeBox);
	    
	    add(alwaysMovePanel);
	}
	
	public String getRuleString() {
	    return((int)priorityNumBox.getSelectedItem() + "|" +
		   (String)moveTypeBox.getSelectedItem());
	}
    }
}