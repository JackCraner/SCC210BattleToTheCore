package Main.GUI;

import Main.ForeGround.Entities.Player;
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
        healthBarSize = new Vector2f(p.getpView().getSize().x - 300, 50);
        front = new RectangleShape(healthBarSize);
        back = new RectangleShape(healthBarSize);
        front.setFillColor(Color.RED);
        back.setFillColor(Color.BLACK);
    }
    public void updateHealthBarPosition()
    {
        Vector2f curP = p.getPosition();
        front.setPosition(curP.x - (healthBarSize.x*(currentHealth/100)/2), curP.y + (p.getpView().getSize().y/2) - healthBarSize.y - 30);
        back.setPosition(curP.x - (healthBarSize.x/2), curP.y + (p.getpView().getSize().y/2) - healthBarSize.y - 30);
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
