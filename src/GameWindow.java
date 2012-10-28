import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;

import javax.swing.*;
import javax.imageio.ImageIO;

public class GameWindow implements ActionListener {

    /** GLOBALS */

    Game game;

    JFrame frame;
    
    Container panel;

    Container gridPanel;

    PayoffPanel payoffPanel;

    SelectionPanel selectionPanel;

    JPanel rightPanel;
    CardLayout rightPanelLayout;

    GameLogPanel gameLogPanel;
    
    PlayerPanel p1Panel;
    PlayerPanel p2Panel;

    JPanel buttonPanel;
    JButton playB;
    
    JButton editP1RulesB;
    JButton editP2RulesB;
    JButton showGameLogB;

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
	frame.setSize(700, 700);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	panel = frame.getContentPane();
	panel.setBackground(Color.YELLOW);

	gridPanel = new JPanel(new GridLayout(3,2));

	JPanel ph1 = new JPanel();
	ph1.setBackground(new Color(92, 86, 73));
	try {
	    BufferedImage bearImage = ImageIO.read(new File("media/barebone.gif"));
	    ph1.add(new JLabel(new ImageIcon(bearImage)));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	gridPanel.add(ph1);
	p2Panel = new PlayerPanel(game.getColumnPlayer(), 2);
	gridPanel.add(p2Panel);
	p1Panel = new PlayerPanel(game.getRowPlayer(), 1);
	gridPanel.add(p1Panel);
	payoffPanel = new PayoffPanel();
	gridPanel.add(payoffPanel);
	JPanel ph2 = new JPanel();
	ph2.setBackground(new Color(180, 194, 181));
	gridPanel.add(ph2);
	selectionPanel = new SelectionPanel();
	gridPanel.add(selectionPanel);
	
	panel.add(gridPanel, BorderLayout.CENTER);

	gameLogPanel = new GameLogPanel();
	gameLogPanel.setPreferredSize(new Dimension(frame.getWidth() / 3, frame.getHeight()));

	panel.add(gameLogPanel, BorderLayout.LINE_END);
			     
	buttonPanel = new JPanel();
	buttonPanel.setBackground(new Color(180, 194, 181));
	buttonPanel.setLayout(new FlowLayout());
	playB = new JButton("Play");
	editP1RulesB = new JButton("Edit P1 Rules");
	editP2RulesB = new JButton("Edit P2 Rules");

	playB.addActionListener(this);
	editP1RulesB.addActionListener(this);
	editP2RulesB.addActionListener(this);

	buttonPanel.add(playB);
	buttonPanel.add(editP1RulesB);
	buttonPanel.add(editP2RulesB);

	panel.add(buttonPanel, BorderLayout.PAGE_END);

	frame.pack();
	frame.setVisible(true);
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
	
	if(e.getSource() == editP1RulesB) {
	    System.out.println("EDIT RULES P1");
	    EditRulesWindow p1Window = new EditRulesWindow(game.getRowPlayer());
	    p1Window.setVisible(true);
	}

	if(e.getSource() == editP2RulesB) {
	    System.out.println("EDIT RULES P2");
	    EditRulesWindow p2Window = new EditRulesWindow(game.getColumnPlayer());
	    p2Window.setVisible(true);
	}
    }

    public static void main(String[] args) {
	new GameWindow();
    }

}