import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * New Window which contain one of two guides on how to use the simulation:
 *    1) Understanding a Prisoner's Dilemma
 *    2) How the player's ruleset is set up
 */

public class HelpWindow extends JFrame {

    public static final int PD_HELP_TYPE = 1;
    public static final int STRAT_HELP_TYPE = 2;

    //The type of this instance of a HelpWindow
    private int helpType;

    Container panel;
    
    public HelpWindow(int helpType) {
	super();
	this.helpType = helpType;

	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    createFrame();
		}
	    });
    }

    private void createFrame() {
	setSize(Config.HELP_WINDOW_SIZE);
	if(helpType == PD_HELP_TYPE) {
	    setTitle("Prisoner's Dilemma Help");
	} else {
	    setTitle("Player Strategy Help");
	}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	panel = getContentPane();

	try {
	    BufferedImage helpImage;
	    //Load Prisoner's Dilemma help image
	    if(helpType == PD_HELP_TYPE) {
		helpImage = ImageIO.read(new File("media/pd.gif"));
	    } else { //Load the Rule help image
		helpImage = ImageIO.read(new File("media/rules.gif"));
	    }
	    panel.add(new JScrollPane(new JLabel(new ImageIcon(helpImage)), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }

}