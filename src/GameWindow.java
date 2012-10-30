import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameWindow implements ActionListener, WindowListener {

    Game game;

    JFrame frame;
    Container panel;

    Container gridPanel;
    PayoffPanel payoffPanel;
    SelectionPanel selectionPanel;
    PlayerPanel rowPlayerPanel;
    PlayerPanel columnPlayerPanel;

    GameLogPanel gameLogPanel;

    JPanel buttonPanel;
    JButton playB;
    JButton editRowPlayerB;
    JButton editColumnPlayerB;
    JButton stratHelpB;
    JButton pdHelpB;
    JButton quitB;

    EditRulesWindow editRowPlayerWindow;
    EditRulesWindow editColumnPlayerWindow;

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

	editRowPlayerWindow = new EditRulesWindow(game.getRowPlayer());
	editRowPlayerWindow.addWindowListener(this);
	editColumnPlayerWindow = new EditRulesWindow(game.getColumnPlayer());
	editColumnPlayerWindow.addWindowListener(this);

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
	columnPlayerPanel = new PlayerPanel(game.getColumnPlayer(), 2);
	gridPanel.add(columnPlayerPanel);
	rowPlayerPanel = new PlayerPanel(game.getRowPlayer(), 1);
	gridPanel.add(rowPlayerPanel);
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

    /**
     * Handle Button actions
     */
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
	    editRowPlayerWindow.setVisible(true);
	}
	if(e.getSource() == editColumnPlayerB) {
	    editColumnPlayerWindow.setVisible(true);
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

    public void windowClosing(WindowEvent e) {
	if(e.getSource() == editRowPlayerWindow) {
	    rowPlayerPanel.updateStrategyPanel();
	}
	if(e.getSource() == editColumnPlayerWindow) {
	    columnPlayerPanel.updateStrategyPanel();
	}
    }

    /**
     * Methods that must be overriden by the class, as it implements
     * WindowListener, although they are not used
     */
    
    public void windowActivated(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}
    
    public void windowIconified(WindowEvent e) {}

    public void windowOpened(WindowEvent e) {}

    public static void main(String[] args) {
	new GameWindow();
    }

}