import java.awt.Component;
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

	setBackground(Config.DARK_ORANGE);

	//Default strategy - always cooperate
	player.loadStrat(0);

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	playerLabel = new JLabel();
	playerLabel.setFont(Config.LABEL_FONT);
	playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	playerLabel.setText((playerNum == 1 ? "Row" : "Column") + " Player:");
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
	strategyBox = new JComboBox<String>(Config.MOVE_LIST);
	strategyBox.addItem("Custom");
	strategyBox.addItemListener(this);
    }
}