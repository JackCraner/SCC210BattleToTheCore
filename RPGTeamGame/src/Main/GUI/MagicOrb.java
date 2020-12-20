package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class MagicOrb
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color MP = new Color(10, 153, 249);         //The colour of the current Magic points
    Color MPLost = new Color(3, 72, 117);       //The colour of the Magic Lost
    RectangleShape front;                                //The front element of the Magic Orb
    RectangleShape back;                                 //The back element of the Magic Orb
    Vector2f MagicOrbSize;                               //The vector2f of the Magic Orb
    float totalMP = 100;                                 //The value of the total Magic points
    float currentMP = 100;                               //The value of the current Magic points
    Player p;                                            //Object reference of player class

    /**
     * MagicOrb() constructor
     * This constructs the MagicOrb element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * @param p The player
     */
    public MagicOrb(Player p)
    {
        this.p = p;
        MagicOrbSize = new Vector2f((float)(Game.windowSize-870), 130); //length, width of bar
        front = new RectangleShape(MagicOrbSize);
        back = new RectangleShape(MagicOrbSize);
        front.setFillColor(MP);
        back.setFillColor(MPLost);
    }

    /**
     * updateMagicOrbPosition()
     * This method updates the MagicOrb according to the current window size
     */
    public void updateMagicOrbPosition()
    {
        front.setPosition(820, Game.windowSize - MagicOrbSize.y - 30);
        back.setPosition(820, Game.windowSize - MagicOrbSize.y - 30);
    }

    /**
     * getMagicOrbFront()
     * This method returns the front of the MagicOrb element
     * @return The front element
     */
    public RectangleShape getMagicOrbFront()
    {
        return front;
    }

    /**
     * getMagicOrbBack()
     * This method returns the back of the MagicOrb element
     * @return The back element
     */
    public RectangleShape getMagicOrbBack()
    {
        return back;
    }

    /**
     * takeMagic()
     * This method removes magic from the MagicOrb
     * @param x The amount of Magic lost
     */
    public void takeMagic(int x)
    {
        currentMP -=x;
        front.setSize(new Vector2f(MagicOrbSize.x, MagicOrbSize.y * (currentMP/100)));
    }
}