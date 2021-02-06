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
import Main.Game.Menu.Credits;

import java.io.File;
import java.nio.file.Paths;

public class GUIFinScreen extends GUIComponent<Level>
{
    private Color ScreenBack = new Color(51, 51, 51);      //The colour of the current Health
    private Color ScreenFront = new Color(10, 153, 249);    //The colour of the Health Lost
    private RectangleShape[] buttons = new RectangleShape[2];       //The button element of the screen
    private RectangleShape title;                                   //The title element of the screen
    private RectangleShape back;                                    //The back element of the screen
    private RectangleShape load;                                    //The load element of the screen
    private float PlayerLevel;                                      //The value of the current Health
    private Vector2f buttonSize;
    private Vector2f screenSize;
    private Texture t1 = new Texture();                             //Element textures
    private Texture t2 = new Texture();
    private Texture t3 = new Texture();
     /**
     * Defines the healthBars params
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIFinScreen(Level s)
    {
        super(s);
        //PlayerLevel = s.getCurrentLevel();
        screenSize = new Vector2f((float)(Game.WINDOWSIZE), 1000); //length, width of bar
        buttonSize = new Vector2f((float)(Game.WINDOWSIZE - 400), 200);
        title = new RectangleShape(buttonSize);
        back = new RectangleShape(screenSize);
        try
        {
            //t1.loadFromFile(Paths.get("Assets" + File.separator + "Screens" + File.separator+ "TheEnd.png"));
            t2.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "Credits.png"));
            t3.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "quit.png"));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        title.setFillColor(ScreenFront);
        title.setPosition(220, Game.WINDOWSIZE - buttonSize.y - 500);
        //title.setTexture(t1);
        buttons[0] = new RectangleShape(buttonSize);
        buttons[1] = new RectangleShape(buttonSize);
        buttons[0].setFillColor(ScreenFront);
        buttons[0].setTexture(t2);
        buttons[0].setPosition(220, Game.WINDOWSIZE - buttonSize.y - 300);
        buttons[1].setFillColor(ScreenFront);
        buttons[1].setTexture(t3);
        buttons[1].setPosition(200, Game.WINDOWSIZE - buttonSize.y - 100);

        back.setFillColor(ScreenBack);
        back.setPosition(0, 0);
    }

    /**
     * Updates the End Screen shown on the GUI
     */
    @Override
    public void update()
    {
        //PlayerLevel = getT().getCurrentLevel();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPosition(200, Game.WINDOWSIZE - buttonSize.y - 100);
        }
        title.setPosition(220, Game.WINDOWSIZE - buttonSize.y - 500);
        back.setPosition(0, 0);
    }

     /**
     * draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(title,renderStates);
        renderTarget.draw(back,renderStates);
        for (int i = 0; i < buttons.length; i++) {
            renderTarget.draw(buttons[i]);
        }
    }
}