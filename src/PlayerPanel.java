import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

/**
 * Holds the strategy-selection dropdown, and a panel which displays the player's ruleset
 */

public class PlayerPanel extends JPanel implements ItemListener{

    private Player player;

    private JLabel playerLabel;

    private JComboBox<String> strategyBox;
    private JLabel strategyName;
    private StrategyPanel strategyPanel;

    public PlayerPanel(Player player) {
	super();
	this.player = player;

	setBackground(Config.DARK_ORANGE);

	//Default strategy - always cooperate
	player.loadStrat(0);

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	playerLabel = new JLabel();
	playerLabel.setFont(Config.LABEL_FONT);
	playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	playerLabel.setText(player.getID() + " Player:");
	add(playerLabel);

	setupStrategyBox();
	add(strategyBox);

	strategyName = new JLabel((String)strategyBox.getSelectedItem());
	strategyName.setFont(Config.LABEL_FONT);
	strategyName.setAlignmentX(Component.CENTER_ALIGNMENT);
	add(strategyName);

	strategyPanel = new StrategyPanel();
	strategyPanel.setRuleText(player.getRuleString());
	add(strategyPanel);
    }

    public void updateStrategyPanel() {
	strategyPanel.setRuleText(player.getRuleString());
    }

    public void itemStateChanged(ItemEvent e) {
	if (e.getSource() == strategyBox) {
	    if(e.getStateChange() == e.SELECTED) {
		strategyName.setText((String)strategyBox.getSelectedItem());
		player.loadStrat(strategyBox.getSelectedIndex());
		strategyPanel.setRuleText(player.getRuleString());
	    }
	}
    }

    public void setupStrategyBox() {
	strategyBox = new JComboBox<String>(Config.STRATEGY_LIST);
	strategyBox.addItemListener(this);
    }
}