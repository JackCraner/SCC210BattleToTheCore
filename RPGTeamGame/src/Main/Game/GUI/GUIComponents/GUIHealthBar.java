package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.HealthBar;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

/**
 * The HealthBar GUI Component
 */
public class GUIHealthBar extends GUIComponent<HealthBar>
{
    private Color HPBack = new Color(1, 1, 1);         //The colour of the current Health
    private Color HPFront = new Color(254, 22, 22);     //The colour of the Health Lost
    private RectangleShape front;                               //The front element of the Health Orb
    private RectangleShape back;                                //The back element of the Health Orb
    private Vector2f healthOrbSize;                             //The vector2f of the Health Orb
    private float totalHealth = 100;                            //The value of the total Health
    private float currentHealth = 100;                          //The value of the current Health
    private float topLeftY;

    /**
     * Defines the healthBars params
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIHealthBar(HealthBar s)
    {
        super(s);
        totalHealth = s.getMaxHealth();
        currentHealth = s.getMaxHealth();
        healthOrbSize = new Vector2f((float)(Game.WINDOWSIZE-870), 130); //length, width of bar
        front = new RectangleShape(healthOrbSize);
        back = new RectangleShape(healthOrbSize);
        front.setPosition(50, Game.WINDOWSIZE - healthOrbSize.y - 30);
        back.setPosition(50, Game.WINDOWSIZE- healthOrbSize.y - 30);
        topLeftY = front.getPosition().y;
        front.setFillColor(HPFront);
        back.setFillColor(HPBack);
    }

    /**
     * Updates the health value shown on the GUI
     */
    @Override
    public void update()
    {
        currentHealth = getT().getCurrentHealth();
        front.setSize(new Vector2f(healthOrbSize.x,healthOrbSize.y* (currentHealth/totalHealth)));
        front.setPosition(new Vector2f(front.getPosition().x,topLeftY + (healthOrbSize.y *(1-(currentHealth/totalHealth)))));
    }

    /**
     * draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(back,renderStates);
        renderTarget.draw(front,renderStates);
    }
}
