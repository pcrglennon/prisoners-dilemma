import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Static class which contains a number of constants (such as window sizes
 * and positions, colors, fonts, etc.) used by our program.
 *
 * They are placed here so they can be edited from this one file.
 */

public class Config {

    public final static Dimension GAME_WINDOW_SIZE = new Dimension(750, 750);
    public final static Dimension EDIT_RULES_WINDOW_SIZE = new Dimension(400, 750);
    public final static Dimension HELP_WINDOW_SIZE = new Dimension(420, 750);

    public final static Dimension GAMELOG_PREF_SIZE = new Dimension(GAME_WINDOW_SIZE.width / 3, GAME_WINDOW_SIZE.height);
    public final static Dimension PAYOFF_CELL_PREF_SIZE = new Dimension(105,40);
    public final static Dimension STRATEGY_PANEL_PREF_SIZE = new Dimension(300, 200);

    public final static Color LIGHT_GRAY = new Color(180, 194, 181);
    public final static Color DARK_GRAY = new Color(92, 86, 73);
    public final static Color LIGHT_ORANGE = new Color(245, 210, 149);
    public final static Color DARK_ORANGE = new Color(242, 176, 61);
    public final static Color LABEL_COLOR = new Color(227, 223, 216);

    public final static Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 17);
    public final static Font PAYOFF_FONT = new Font("SansSerif", Font.BOLD, 20);

    public final static String[] MOVE_LIST = {"Cooperate", "Defect", "Opp. Prev.", "! Opp. Prev.", "Your Prev.", "! Your Prev.", "Anything"};

    public final static String[] STRATEGY_LIST = {"Cooperate", "Defect", "Random", "Tit for Tat", "Tat for Tit", "Forgiving Tit for Tat", "Grim", "Prideful", "Nice Average", "Custom"};

}