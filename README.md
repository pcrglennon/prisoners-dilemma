Prisoner's Dilemma Simulation
===========


A visual Prisoner's Dilemma simulation, written in Java (using the Swing and AWT libraries).

Users can edit the strategies for both players in the dilemma and configure the payoff structure before starting the game, and then the round-by-round results can be seen in a scrollable text panel.

I wrote this with Clint Mullins (github.com/ClintFMullins) for our Algorithmic Game Theory Midterm project. He wrote the Game.java, Player.java, and RuleFactory.java files, while I wrote the graphical classes and the Config.java file.

Many configuration options (i.e. fonts and window sizes) can be edited via Config.java.

The easiest way to run the program is using Apache ant, and simply executing "ant run" in the directory containing the build.xml file.