import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * All tested and functioning
 * 		
 * @author ClintFrank
 *
 */
public class Game {
    private int curRound,p1choice,p2choice;
    private Player p1, p2;
    private boolean p1move, p2move;
    private String printString;
    private int roundTotal;
    public Game(){
	p1 = new Player("Row");
	p2 = new Player("Column");
    }
	
    /**
     * play this GAAAAAAAAAMYEEEEEEAAAAHHHH!
     * 
     * plays the game for the given number of turns and with given payoffs
     * 
     * @param payoffs
     * @param roundTotal
     * @return
     */
    public String wantPlayGame(int[][] payoffs, int roundTotal){
	curRound=0;
	this.roundTotal = roundTotal;
	printString=getWelcomeScreen()+"\n\n";
	p1.setPayoffsAndTurns(payoffs[0][0],payoffs[1][0],payoffs[2][0],payoffs[3][0],roundTotal);
	p2.setPayoffsAndTurns(payoffs[0][1],payoffs[2][1],payoffs[1][1],payoffs[3][1],roundTotal);
	printString+="------Player Rulesets------\n";
	printString+=rulesNiceAndTidy(true);
	printString+=rulesNiceAndTidy(false);
	printString+="---------------------------\n\n";
	while (curRound<roundTotal){
	    p1move = p1.nextMove();  //gets move for both players
	    p2move = p2.nextMove();
	    p1choice = (p1move==true?0:1);  //gets index to assign points
	    p2choice = (p2move==true?0:1);
	    p1.yourReward(p1choice,p2choice);  //gives them their rewaaaarrhhdd (this is a crucial Aladdin reference)
	    p2.yourReward(p2choice,p1choice);
	    curRound++;								//increments the round
	    p1.andSoItGoes(p1move,p2move,curRound);  //updates histories and rounds of players
	    p2.andSoItGoes(p2move,p1move,curRound);
	    printString+=getMoveData(0)+"\n\n";
	}
	printString+="\n\n"+getWinner();
	printDataToFile();
	return getWinner();
    }
	
	
    /**
     * {{8,8},{0,13},
     * {13,0},{3,3}};
     * 
     * Above is prisoner's dilemma
     * 
     * {{A,A},{C,B},
     * {B,C},{D,D}};
     * B>A>D>C
     * this is the format, keep it!
     * 
     * returns true if it is in the above format, false otherwise
     * 
     * @param payoffs
     * @return
     */
    public boolean isDilemma(int[][] payoffs){
	if (Math.min(payoffs[2][0],payoffs[1][1]) <= Math.max(payoffs[0][0],payoffs[0][1])){
	    return false;
	}
	if (Math.min(payoffs[0][0],payoffs[0][1]) <= Math.max(payoffs[3][0],payoffs[3][1])){
	    return false;
	}
	if (Math.min(payoffs[3][0],payoffs[3][1]) <= Math.max(payoffs[2][1],payoffs[1][0])){
	    return false;
	}
	return true;
    }

    public Player getRowPlayer() {
	return p1;
    }

    public Player getColumnPlayer() {
	return p2;
    }
	
    /**
     * Sets the strategy for a given player to a given strategy index
     * @param player (boolean) true: row / false: column
     * @param strat (int) index
     */
    public void setStrat(boolean player, int strat){
	if (player){
	    p1.loadStrat(strat);
	}
	p2.loadStrat(strat);
    }
	
    /**
     * returns the Winner at the end of the game (or any time it is called)
     * 
     * @return
     */
    public String getWinner(){
	if (p1.getPoints()==p2.getPoints()){
	    return "Tie. Good food, bad outcome.";
	}
	return (p1.getPoints()>p2.getPoints()?"Player Row":"Player Column")+" is the winner!";
    }
	
    /**
     * Returns Boot up Screen
     * 
     * @return
     */
    public String getWelcomeScreen(){
	return "Welcome to Prisoners Dilemma\n" +
	    "(1) Choose desired payoffs for each player\n" +
	    "(2) Choose strategies for each player.";
    }
	
    /**
     * Returns Rules all Nice and Tidy
     * 
     * @param player (boolean) true: row / false: column
     * @return
     */
    public String rulesNiceAndTidy(boolean player){
	Player p = (player?p1:p2);
	String rules = (player?"Player Row:":"Player Column:")+"\n";
	for (String rule: p.getRuleString()){
	    if (rule!=null){
		rules+=rule+"\n";
	    }
	}
	return rules+"\n";
    }
	
    /**
     * Get all data in a big beautiful string.
     * 
     * @return
     */
    public String getGameString(){
	return printString;
    }
	
	
    /**
     * For any turn, will return all relevent data
     * 
     * @param desiredChange
     * @return
     */
    public String getMoveData(int desiredChange){
	if (curRound+desiredChange < 0 || curRound+desiredChange > roundTotal){
	    return "Hey, man. That's out of range. You knew that; stop fooling around.";
	}
	curRound+=desiredChange;
	String turnData = "-------Round "+curRound+"-------\n\nPlayer Row\n";
	turnData+="Rule Fired      "+p1.getWhichRuleFiredTurn(curRound);
	turnData+="\nMove             "+(p1.getMyMoveTurn(curRound)?"cooperate":"defect");
	turnData+="\nPoints Won    "+p1.getPointChangeHistoryTurn(curRound);
	turnData+="\nTotal Points   "+p1.getPointCumChangeTurn(curRound);
	turnData+="\n\nPlayer Column\n";
	turnData+="Rule Fired      "+p2.getWhichRuleFiredTurn(curRound);
	turnData+="\nMove             "+(p2.getMyMoveTurn(curRound)?"cooperate":"defect");
	turnData+="\nPoints Won    "+p2.getPointChangeHistoryTurn(curRound);
	turnData+="\nTotal Points   "+p2.getPointCumChangeTurn(curRound);
	return turnData;
    }
	
    /**
     * Prints out all data to a file 
     */
    public void printDataToFile(){
	try {
	    File file = new File("media/gameOutput.txt");
 
	    // if file doesnt exists, then create it
	    if (!file.exists()) {
		file.createNewFile();
	    }
 
	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(printString);
	    bw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
	
    public static void main(String[] args){
	int[][] players = {{8,8},{0,9},
			   {15,0},{1,3}};
	Game test = new Game();
	System.out.println(test.isDilemma(players));
	//test.p1.loadStrat(3);
	//test.p2.loadStrat(2);
	//test.wantPlayGame(players,10);
    }
}
