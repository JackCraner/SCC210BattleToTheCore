package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class GUIEndScreen extends GUIComponent<Health>
{
    private Color ScreenBack = new Color(51, 51, 51);                  //The colour of the current Health
    private Color ScreenFront = new Color(254, 22, 22);             //The colour of the Health Lost
    private RectangleShape front;                                   //The title element of the screen
    private RectangleShape back;                                    //The back element of the screen
    private RectangleShape load;                                   //The load element of the screen
    private float PlayerHealth;                          //The value of the current Health
    
    private Vector2f ScreenSize;
     /**
     * Defines the healthBars params
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIEndScreen(Health s)
    {
        super(s);
        PlayerHealth = s.getMaxHealth();
        ScreenSize = new Vector2f((float)(Game.WINDOWSIZE), 0); //length, width of bar
        front = new RectangleShape(ScreenSize);
        back = new RectangleShape(ScreenSize);
        front.setPosition(100, Game.WINDOWSIZE - ScreenSize.y - 30);
        back.setPosition(0, Game.WINDOWSIZE- ScreenSize.y);
        front.setFillColor(ScreenFront);
        back.setFillColor(ScreenBack);
    }

    /**
     * Updates the End Screen shown on the GUI
     */
    @Override
    public void update()
    {
        PlayerHealth = getT().getStat();
        front.setSize(new Vector2f(ScreenSize.x,ScreenSize.y));
        front.setPosition(new Vector2f(front.getPosition().x, ScreenSize.y));
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

