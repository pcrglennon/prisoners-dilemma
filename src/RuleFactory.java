
/**
 * TESTED:
 * 		getRule0 -works
 * 		getRule1 -works
 * 		getRuleSting -works
 * 
 * NOT TESTED:
 * 		getValues (not finished because Needs GUI incorporation)
 * 
 * 
 * 		
 * @author ClintFrank
 *
 */
public class RuleFactory {
    public static int priority;
    //Rule 1:
    //[if/until](1) the [opponent/myself](2) does [move](3) [over||=/under](4) [number](5)% of times over the previous 
    //[number](6) of moves, do [move](7) for the next [number/all](8) moves.
    private static int ifUntil1, //0 - if, 1 - until
	oppMyself2, //0 - opponent, 1 - myself
	move3, //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous, 4 - any/random
	overUnder4, //0 - over or equal to, under (not equal to)
	numPerc5, //0-100 - percentage amount
	numOrAll6, //number of turns, -1 means the rest of the game 
	move7, //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous, 4 - any/random
	numOrAll8; //number of turns, -1 means the rest of the game
    //Rule 2:
    //Otherwise [move](1)
    private static int move1;  //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous, 4 - opposite of opp previous move, 5 - opposite of my previous move, 6 - any/random
	
    //Below are the strings to be used to fill in the rule statment displays on the GUI.
    private static String[] sIU = {"If","Until"};
    private static String[] sOM = {"Opp.", "You"};
    private static String[] sM1 = Config.MOVE_LIST;
    //private static String[] sM2 = {"[cooperate]","[defect]","[do opponent's previous move]","[do opposite of opponents previous move]","[do my previous move]","[do opposite of my previous move]", "[random move]"};
    private static String[] sOU = {"At least", "Under"};
	
    /**
     * This method pulls values from the GUI into this class for use by the Rule Factory's Rule Creation
     */
    public static void getRule0Vals(String rule){
	String[] ruleString = rule.split("\\|");
	//here is where we update all values with the boxes from the GUI
	//BELOW IS ALL TEST/TEMPORARY CODE. THIS MUST BE REPLACED WITH GUI VARIABLE EXTRACTION
	priority = Integer.parseInt(ruleString[0]) - 1;
	ifUntil1 = (ruleString[1].equals("If")?0:1); //0 - if, 1 - until
	oppMyself2 = (ruleString[2].equals("Opp.")?0:1); //0 - opponent, 1 - myself
	move3 = decideMove(ruleString[3]);
	overUnder4 = (ruleString[4].equals("At least")?0:1); //0 - over or equal to, under (not equal to)
	numPerc5 = Integer.parseInt(ruleString[5]); //0-100 - percentage amount
	numOrAll6 = Integer.parseInt(ruleString[6]); //number of turns, -1 means the rest of the game 
	move7 = decideMove(ruleString[7]);
	numOrAll8 = Integer.parseInt(ruleString[8]);  //number of turns, -1 means the rest of the game
    } 
	
    public static void getRule1Vals(String rule){
	String[] ruleString = rule.split("\\|");
	priority = Integer.parseInt(ruleString[0]) - 1;
	move1 = decideMove(ruleString[1]);
    }

	public static int decideMove(String move){
		if (move.equals("Cooperate")) return 0;  //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous,  4 - my prev, 5 - opp of my prev, 6 - any
		else if (move.equals("Defect")) return 1;
		else if (move.equals("Opp. Prev.")) return 2;
		else if (move.equals("! Opp. Prev.")) return 3;
		else if (move.equals("Your Prev.")) return 4;
		else if (move.equals("! Your Prev")) return 5;
		else return 6; 
	}
	
    /**
     * Retrieves rule specifications in form of int[]
     * 
     * @return rule0 (int[]) list of specifications
     */
    public static int[] getRule0(String rule){
	getRule0Vals(rule);
	int[] rule0 = {priority, ifUntil1, oppMyself2, move3, overUnder4, numPerc5, numOrAll6, move7, numOrAll8};
	return rule0;
    }
	
    /**
     * Retrieves rule specifications in form of int[]
     * 
     * @return rule1 (int[]) list of specifications
     */
    public static int[] getRule1(String rule){
	getRule1Vals(rule);
	int[] rule1 = {priority, move1};
	return rule1;
    }
	
	/**
	 * returns a sentence representing a rule to be put in a display list
	 * 
	 * 
	 * @param rule
	 * @return
	 */
	public static String getRuleString(int[] specs){
		priority = specs[0];
		if (specs.length==9){
			ifUntil1 = specs[1]; //0 - if, 1 - until
			oppMyself2 = specs[2]; //0 - opponent, 1 - myself
			move3 = specs[3]; //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous, 4 - my prev, 5 - opp of my prev, 6 - any/random
			overUnder4 = specs[4]; //0 - over or equal to, under (not equal to)
			numPerc5 = specs[5]; //0-100 - percentage amount
			numOrAll6 = specs[6]; //number of turns, -1 means the previous entire game
			move7 = specs[7]; //0 - coop, 1 - defect, 2 - same as opp previous, 3 - opposite of opp previous, 4 - my prev, 5 - opp of my prev, 6 - any
			numOrAll8 = specs[8]; //number of turns, -1 means the rest of the game
			return "" + (priority + 1) + ") [" + sIU[ifUntil1] + "] " + " [" +
					sOM[oppMyself2] + "] [" +
					sM1[move3] +"] [" +
					sOU[overUnder4] +"] ["+
					numPerc5+"]% of the time over the previous ["+
					(numOrAll6==-1 ? "all": numOrAll6)+"] move(s), do ["+
					sM1[move7]+"] for the next ["+
					(numOrAll8==-1 ? "all": numOrAll8) +"] move(s).";
		}
		else{
			move7 = specs[1];
			return "" + (priority + 1) + ") Always ["+sM1[move7] + "].";
		} //{0,1,0,6,0,100,1,0,1}
	}     

	
    /**
     * This method holds premade strategies that can be retrieved with an integer input. 
     * Guide:
     * 0 - always cooperate
     * 1 - always defect
     * 2 - always Random
     * 3 - tit for tat
     * 4 - tat for tit
     * 5 - forgiving tit for tat
     * 6 - grim
     * 7 - prideful
     * 8 (else) - average
     *
     * @param strat (int) input that allows the caller to choose from the above
     * @return
     */
    public static int[][] getStrat(int strat){
	if (strat==0){ //always cooperate
	    int[][] alwaysCooperate = {{0,0}};
	    return alwaysCooperate;
	}
	else if (strat==1){ //always defect
	    int[][] alwaysDefect = {{0,1}};
	    return alwaysDefect;
	}
	else if (strat==2){ //always random
	    int[][] alwaysRandom = {{0,6}};
	    return alwaysRandom;
	}
	else if (strat==3){ //TitforTat
	    int[][] titForTat = {{0,2}, //same as opp previous
				 {1,0}}; //otherwise, we cooperate
	    return titForTat;
	}
	else if (strat==4){ //tatForTit
	    int[][] tatForTit = {{0,3},
				 {1,0}};
	    return tatForTit;
	}
	else if (strat==5){ //forgiving Tit for Tat
	    int[][] forgivingTitForTat = {{0,0,0,1,0,100,2,1,1}, //if the opponent defects twice in a row, we defect
					  {1,0}}; //otherwise, we cooperate
	    return forgivingTitForTat;
	}
	else if (strat==6){ //grim
	    int[][] grim = {{0,1,0,1,0,100,1,0,1}, //until opponent defects once, cooperate
			    {1,1}}; //defect
	    return grim;
	}
	else if (strat==7){ //prideful (reverse grim)
	    int[][] prideful = {{0,1,0,0,0,100,1,1,1}, //until opponent cooperates once, defect
				{1,0}}; //cooperate
	    return prideful;
	}
	else if(strat==8){ //nice average
	    int[][] average = {{0,1,0,6,0,100,1,0,1},
			       {1,0,0,0,0,50,-1,0,1}, //if opponent plays cooperate at least 50% of the time, cooperate
			       {2,1}};//cooperate
	    return average;
	}
	else{
		int[][] custom = {};//cooperate
	    return custom;
    }
    }
}
