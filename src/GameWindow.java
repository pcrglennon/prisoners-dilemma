import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameWindow implements ActionListener {

    Game game;

    JFrame frame;
    Container panel;

    Container gridPanel;
    PayoffPanel payoffPanel;
    SelectionPanel selectionPanel;
    PlayerPanel p1Panel;
    PlayerPanel p2Panel;

    GameLogPanel gameLogPanel;

    JPanel buttonPanel;
    JButton playB;
    JButton editRowPlayerB;
    JButton editColumnPlayerB;
    JButton stratHelpB;
    JButton pdHelpB;
    JButton quitB;

    /** 
     * Constructor
     */
    public GameWindow() {
	game = new Game();

	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    createAndShowGUI();
		}
	    });
    }

    public void createAndShowGUI() {
	frame = new JFrame("Prisoner's Dilemma Simulation");
	frame.setSize(Config.GAME_WINDOW_SIZE);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	panel = frame.getContentPane();

	setupGridPanel();

	gameLogPanel = new GameLogPanel();
	gameLogPanel.setPreferredSize(Config.GAMELOG_PREF_SIZE);
	gameLogPanel.setBackground(Config.LIGHT_GRAY);

	panel.add(gameLogPanel, BorderLayout.LINE_END);
			     
	setupButtonPanel();

	frame.pack();
	frame.setVisible(true);
    }

    /**
     * Sets up the main grid - the player panels, the payoff matrix panel,
     * the selection panel, and two placeholder panels (one of which holds
     * the "Bearbones Game Theorists" image
     */
    private void setupGridPanel() {
	gridPanel = new JPanel(new GridLayout(3,2));

	JPanel imagePanel = new JPanel();
	imagePanel.setBackground(Config.DARK_GRAY);
	//Load the "Bearbones Game Theorists" Image
	try {
	    BufferedImage bearImage = ImageIO.read(new File("media/barebone.gif"));
	    imagePanel.add(new JLabel(new ImageIcon(bearImage)));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	gridPanel.add(imagePanel);
	p2Panel = new PlayerPanel(game.getColumnPlayer(), 2);
	gridPanel.add(p2Panel);
	p1Panel = new PlayerPanel(game.getRowPlayer(), 1);
	gridPanel.add(p1Panel);
	payoffPanel = new PayoffPanel();
	gridPanel.add(payoffPanel);
	JPanel placeHolderPanel = new JPanel();
	placeHolderPanel.setBackground(Config.LIGHT_GRAY);
	gridPanel.add(placeHolderPanel);
	selectionPanel = new SelectionPanel();
	selectionPanel.setBackground(Config.LIGHT_GRAY);
	gridPanel.add(selectionPanel);
	
	panel.add(gridPanel, BorderLayout.CENTER);
    }
    
    /**
     * Sets up the the button panel - the play button, the strategy-editing
     * buttons, and the help button
     */
    private void setupButtonPanel() {
	buttonPanel = new JPanel();
	buttonPanel.setBackground(Config.LIGHT_GRAY);
	buttonPanel.setLayout(new FlowLayout());
	playB = new JButton("Play");
	editRowPlayerB = new JButton("Edit Row Player");
	editColumnPlayerB = new JButton("Edit Column Player");	
	stratHelpB = new JButton("Strategy Help");
	pdHelpB = new JButton("P.D. Help");
	quitB = new JButton("Quit");
	
	playB.addActionListener(this);
	editRowPlayerB.addActionListener(this);
	editColumnPlayerB.addActionListener(this);
	stratHelpB.addActionListener(this);
	pdHelpB.addActionListener(this);
	quitB.addActionListener(this);
	
	buttonPanel.add(playB);
	buttonPanel.add(editRowPlayerB);
	buttonPanel.add(editColumnPlayerB);
	buttonPanel.add(Box.createHorizontalStrut(60));
	buttonPanel.add(stratHelpB);
	buttonPanel.add(pdHelpB);
	buttonPanel.add(Box.createHorizontalStrut(40));
	buttonPanel.add(quitB);

	panel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == playB) {
	    int[][] payoffs = payoffPanel.getAllPayoffs();
	    if (payoffs == null) {
		JOptionPane.showMessageDialog(frame, "One or more payoff cells are incomplete");
	    } else {
		if (selectionPanel.ensureDilemma()) {
		    if(game.isDilemma(payoffs)) {
			game.wantPlayGame(payoffs, selectionPanel.getNumRounds());
			gameLogPanel.updateGameLog(game.getGameString());
		    } else {
			JOptionPane.showMessageDialog(frame, "Invalid Format for a Prisoner's Dilemma");
		    }
		} else {
		    game.wantPlayGame(payoffs, selectionPanel.getNumRounds());
		    gameLogPanel.updateGameLog(game.getGameString());
		}
	    }
	}
	if(e.getSource() == editRowPlayerB) {
	    System.out.println("EDIT RULES P1");
	    EditRulesWindow p1Window = new EditRulesWindow(game.getRowPlayer());
	    p1Window.setVisible(true);
	}

	if(e.getSource() == editColumnPlayerB) {
	    System.out.println("EDIT RULES P2");
	    EditRulesWindow p2Window = new EditRulesWindow(game.getColumnPlayer());
	    p2Window.setVisible(true);
	}
	if(e.getSource() == stratHelpB) {
	    HelpWindow helpWindow = new HelpWindow(HelpWindow.STRAT_HELP_TYPE);
	    helpWindow.setVisible(true);
	}
	if(e.getSource() == pdHelpB) {
	    HelpWindow helpWindow = new HelpWindow(HelpWindow.PD_HELP_TYPE);
	    helpWindow.setVisible(true);
	}
	if(e.getSource() == quitB) {
	    System.exit(0);
	}
    }

    public static void main(String[] args) {
	new GameWindow();
    }

}