import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.io.*;

import javax.swing.*;

public class GameLogPanel extends JPanel {

    private JLabel gameLogLabel;

    private JTextArea gameLog;
    private JScrollPane gameLogScrollPane;

    public GameLogPanel() {
	super();
	
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	gameLogLabel = new JLabel();
	gameLogLabel.setFont(Config.LABEL_FONT);
	gameLogLabel.setText("Game Log:");
	gameLogLabel.setAlignmentY(Component.TOP_ALIGNMENT);
	gameLogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	add(gameLogLabel);

	gameLog = new JTextArea();
	gameLog.setEditable(false);
	gameLog.setLineWrap(true);
	gameLog.setWrapStyleWord(true);
	makeGameLogPlaceholder();
	
	gameLogScrollPane = new JScrollPane(gameLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//gameLogScrollPane.setPreferredSize();
	
	add(gameLogScrollPane);
	gameLog.setCaretPosition(0);
    }

    public void updateGameLog(String newGameLogText) {
	gameLog.setText(newGameLogText);
    }

    private void makeGameLogPlaceholder() {
	StringBuilder glp = new StringBuilder();
	BufferedReader br = null;
	try {
	    br = new BufferedReader(new FileReader("media/GameLogPlaceholder.txt"));
	    String line = null;
	    while((line = br.readLine()) != null) {
		glp.append(line + "\n");
	    }
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    try {
		if(br != null) {
		    br.close();
		}
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }
	}
	gameLog.setText(glp.toString());
    }
}