package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class GUIFinScreen extends GUIComponent<Level>
{
    private Color ScreenBack = new Color(51, 51, 51);      //The colour of the current Health
    private Color ScreenFront = new Color(254, 22, 22);    //The colour of the Health Lost
    private RectangleShape[] buttons = new RectangleShape[3];       //The front element of the screen
    private RectangleShape back;                                    //The back element of the screen
    private RectangleShape load;                                    //The load element of the screen
    private float PlayerLevel;                                      //The value of the current Health
    private float buttonLength;
    private Vector2f buttonSize;
    private Vector2f screenSize;
    private Texture t = new Texture();
     /**
     * Defines the healthBars params
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIFinScreen(Level s)
    {
        super(s);
        PlayerLevel = s.getCurrentLevel();
        screenSize = new Vector2f((float)(Game.WINDOWSIZE), 0); //length, width of bar
        buttonLength = (float)(Game.WINDOWSIZE - 900);
        buttonSize = new Vector2f(buttonLength, 90);
        back = new RectangleShape(screenSize);
        back.setFillColor(ScreenBack);
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new RectangleShape(screenSize);
            if (i % 2 == 0){
                buttons[i].setFillColor(ScreenFront);
            }
            else {
                buttons[i].setFillColor(ScreenBack);
            }
            buttons[i].setPosition(200 + (buttonLength * i), Game.WINDOWSIZE - buttonSize.y - 30);
        }
        back.setPosition(0, Game.WINDOWSIZE- screenSize.y);

        try
        {
            t.loadFromFile(Paths.get("Assets" + File.separator + "Screens" + File.separator+ "GameOver.png"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        back.setTexture(t);
    }

    /**
     * Updates the End Screen shown on the GUI
     */
    @Override
    public void update()
    {
        PlayerLevel = getT().getCurrentLevel();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPosition(100, Game.WINDOWSIZE - buttonSize.y - 30);
        }
        back.setPosition(200, Game.WINDOWSIZE - buttonSize.y - 30);
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
        for (int i = 0; i < buttons.length; i++) {
            renderTarget.draw(buttons[i]);
        }
    }
}