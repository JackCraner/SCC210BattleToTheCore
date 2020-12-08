package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class HealthBar
{
    //spacing and items should scale with viewSize
    RectangleShape front;
    RectangleShape back;
    Vector2f healthBarSize;
    float totalHealth = 100;
    float currentHealth = 100;
    Player p;
    public HealthBar(Player p)
    {
        this.p = p;
        healthBarSize = new Vector2f((float)(Game.windowSize-200), 50);
        front = new RectangleShape(healthBarSize);
        back = new RectangleShape(healthBarSize);
        front.setFillColor(Color.RED);
        back.setFillColor(Color.WHITE);
    }
    public void updateHealthBarPosition()
    {

        front.setPosition(100, Game.windowSize - healthBarSize.y - 30);
        back.setPosition(100, Game.windowSize - healthBarSize.y - 30);
    }
    public RectangleShape getHealthBarFront()
    {
        return front;
    }
    public RectangleShape getHealthBarBack()
    {
        return back;
    }
    public void takeDamage(int x)
    {
        currentHealth -=x;
        front.setSize(new Vector2f(healthBarSize.x * (currentHealth/100), healthBarSize.y));

    }
}
