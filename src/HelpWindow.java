import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HelpWindow extends JFrame {

    public static final int PD_HELP_TYPE = 1;
    public static final int STRAT_HELP_TYPE = 2;

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
	setSize(576,400);
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
		helpImage = ImageIO.read(new File("media/test.gif"));
	    } else { //Load the Rule help image
		helpImage = ImageIO.read(new File("media/test2.gif"));
	    }
	    panel.add(new JScrollPane(new JLabel(new ImageIcon(helpImage)), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }

}