package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class HealthBar
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color HP = new Color(254, 22, 22);         //The colour of the current Health
    Color HPLost = new Color(209, 17, 17);     //The colour of the Health Lost
    RectangleShape front;                               //The front element of the Health bar
    RectangleShape back;                                //The back element of the Health bar
    Vector2f HealthBarSize;                             //The vector2f of the Health bar
    float totalHealth = 100;                            //The value of the total Health
    float currentHealth = 100;                          //The value of the current Health
    Player p;                                           //Object reference of player class

    /**
     * HealthBar() constructor
     * This constructs the HealthBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * @param p The player
     */
    public HealthBar(Player p)
    {
        this.p = p;
        HealthBarSize = new Vector2f((float)(Game.windowSize-700), 50); //length, width of bar
        front = new RectangleShape(HealthBarSize);
        back = new RectangleShape(HealthBarSize);
        front.setFillColor(HP);
        back.setFillColor(HPLost);
    }

    /**
     * updateHealthBarPosition()
     * This method updates the HealthBar according to the current window size
     */
    public void updateHealthBarPosition()
    {
        front.setPosition(100, Game.windowSize - HealthBarSize.y - 200);
        back.setPosition(100, Game.windowSize - HealthBarSize.y - 200);
    }

    /**
     * getHealthBarFront()
     * This method returns the front of the HealthBar element
     * @return The front element
     */
    public RectangleShape getHealthBarFront()
    {
        return front;
    }

    /**
     * getHealthBarBack()
     * This method returns the back of the HealthBar element
     * @return The back element
     */
    public RectangleShape getHealthBarBack()
    {
        return back;
    }

    /**
     * takeDamage()
     * This method removes health from the HealthBar
     * @param x The amount of Health lost
     */
    public void takeDamage(int x)
    {
        currentHealth -=x;
        front.setSize(new Vector2f(HealthBarSize.x * (currentHealth/100), HealthBarSize.y));
    }
}
