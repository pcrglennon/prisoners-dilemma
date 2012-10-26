import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EditRulesWindow extends JFrame implements ActionListener {

    Player player;

    EditRulesPanel editRulesPanel;

    public EditRulesWindow(Player player) {
	super();
	this.player = player;
	
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    createFrame();
		}
	    });
    }

    private void createFrame() {
	setSize(400,700);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	editRulesPanel = new EditRulesPanel(player);
	setContentPane(editRulesPanel);

	pack();
    }

    public void actionPerformed(ActionEvent e) {

    }

}