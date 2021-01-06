package Main.Game.GUI;

import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class HealthOrb
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color HP = new Color(254, 22, 22);         //The colour of the current Health
    Color HPLost = new Color(209, 17, 17);     //The colour of the Health Lost
    RectangleShape front;                               //The front element of the Health Orb
    RectangleShape back;                                //The back element of the Health Orb
    Vector2f HealthOrbSize;                             //The vector2f of the Health Orb
    float totalHealth = 100;                            //The value of the total Health
    float currentHealth = 100;                          //The value of the current Health

    /**
     * HealthOrb() constructor
     * This constructs the HealthOrb element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     */
    public HealthOrb()
    {
        HealthOrbSize = new Vector2f((float)(Game.getGame().getWindow().getSize().x-870), 130); //length, width of bar
        front = new RectangleShape(HealthOrbSize);
        back = new RectangleShape(HealthOrbSize);
        front.setFillColor(HPLost);
        back.setFillColor(HP);
    }

    /**
     * updateHealthOrbPosition()
     * This method updates the HealthOrb according to the current window size
     */
    public void updateHealthOrbPosition()
    {
        front.setPosition(50, Game.getGame().getWindow().getSize().y - HealthOrbSize.y - 30);
        back.setPosition(50, Game.getGame().getWindow().getSize().y- HealthOrbSize.y - 30);
    }

    /**
     * getHealthOrbFront()
     * This method returns the front of the HealthOrb element
     * @return The front element
     */
    public RectangleShape getHealthOrbFront()
    {
        return front;
    }

    /**
     * getHealthBarBack()
     * This method returns the back of the HealthOrb element
     * @return The back element
     */
    public RectangleShape getHealthOrbBack()
    {
        return back;
    }

    /**
     * takeDamage()
     * This method removes health from the HealthOrb
     * @param x The amount of Health lost
     */
    public void takeDamage(int x)
    {
        currentHealth -=x;
        front.setSize(new Vector2f(HealthOrbSize.x,HealthOrbSize.y* (currentHealth/100)));
    }
}
