package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class XPBar
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color XP = new Color(82, 220, 47);         //The colour of the XP gained
    Color XPEmpty = new Color(74, 185, 46);    //The colour of the empty XP bar
    RectangleShape front;                               //The front element of the XP bar
    RectangleShape back;                                //The back element of the Xp bar
    Vector2f XPBarFrontSize;                            //The vector2f of the XP gained
    Vector2f XPBarBackSize;                             //The vector2f of the total XP
    float totalXP = 100;                                //The value of the total XP the player needs to level up
    float currentXP = 10;                               //The current XP value of the player
    Player p;                                           //Object reference of player class

    /**
     * XPBar() constructor
     * This constructs the XPBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * @param p The player
     */
    public XPBar(Player p)
    {
        this.p = p;
        XPBarFrontSize = new Vector2f((float)(Game.windowSize-980), 50);    //length, width of bar
        XPBarBackSize = new Vector2f((float)(Game.windowSize-200), 50);     //length, width of bar
        front = new RectangleShape(XPBarFrontSize);
        back = new RectangleShape(XPBarBackSize);
        front.setFillColor(XP);
        back.setFillColor(XPEmpty);
    }

    /**
     * updateXPBarPosition()
     * This method updates the XPBar according to the current window size
     */
    public void updateXPBarPosition()
    {
        front.setPosition(100, Game.windowSize - XPBarFrontSize.y - 135);
        back.setPosition(100, Game.windowSize - XPBarBackSize.y - 135);
    }

    /**
     * getXPBarFront()
     * This method returns the front of the XPBar element
     * @return The front element
     */
    public RectangleShape getXPBarFront()
    {
        return front;
    }

    /**
     * getXPBarBack()
     * This method returns the back of the XPBar element
     * @return The back element
     */
    public RectangleShape getXPBarBack()
    {
        return back;
    }

    /**
     * AddXP()
     * This method adds XP to the XPBar
     * @param x The amount of XP gained
     */
    public void AddXP(int x)
    {
        /*
        if (front.getSize() >= back.getSize()){
        } */
        currentXP +=x;
        front.setSize(new Vector2f(XPBarFrontSize.x * (currentXP/100), XPBarFrontSize.y));
    }
}