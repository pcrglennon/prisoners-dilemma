import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class PlayerPanel extends JPanel implements ItemListener{

    private Player player;
    private int playerNum;

    private JLabel playerLabel;

    private JComboBox<String> strategyBox;
    private JLabel strategyName;
    private StrategyPanel strategyPanel;

    public PlayerPanel(Player player, int playerNum) {
	super();
	this.player = player;
	this.playerNum = playerNum;

	setBackground(new Color(242, 176, 61));

	//Default strategy - always cooperate
	player.loadStrat(0);

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	playerLabel = new JLabel();
	Font labelFont = new Font("SansSerif", Font.BOLD, 17);
	playerLabel.setFont(labelFont);
	playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	playerLabel.setText((playerNum == 1 ? "Row" : "Column") + " Player:");
	add(playerLabel);

	setupStrategyBox();
	add(strategyBox);

	strategyName = new JLabel((String)strategyBox.getSelectedItem());
	strategyName.setFont(labelFont);
	strategyName.setAlignmentX(Component.CENTER_ALIGNMENT);
	add(strategyName);

	strategyPanel = new StrategyPanel();
	strategyPanel.setRuleText(player.getRuleString());
	add(strategyPanel);
    }

    public void itemStateChanged(ItemEvent e) {
	if (e.getSource() == strategyBox) {
	    if(e.getStateChange() == e.SELECTED) {
		strategyName.setText((String)strategyBox.getSelectedItem());
		player.loadStrat(strategyBox.getSelectedIndex());
		strategyPanel.setRuleText(player.getRuleString());
		//TODO - update the strategy panel in the editrules panel
	    }
	}
    }

    public void setupStrategyBox() {
	String[] choices = {"Cooperate", "Defect", "Random", "Tit for Tat", "Tat for Tit", "Forgiving TfT"};
	//String[] choices = {"Cooperate", "Defect", "Random", "Tit for Tat", "Tat for Tit", "Forgiving TfT", "Grim", "Prideful", "Average"};
	strategyBox = new JComboBox<String>(choices);
	strategyBox.addItemListener(this);
    }
}