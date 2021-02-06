package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.GUI.GUIComponent;
import Main.Game.Menu.Settings;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.io.File;
import java.nio.file.Paths;

public class GUIDeadScreen extends GUIComponent<Health>
{
    private Color ScreenBack = new Color(38, 38, 38);           //The colour of the background
    private Color ScreenFront = new Color(254, 22, 22);         //The colour of the buttons
    private RectangleShape front;                                        //The title element of the screen
    private int numButtons = 3;
    private RectangleShape[] buttons = new RectangleShape[numButtons];   //The button element of the screen
    private RectangleShape back;                                         //The back element of the screen
    private RectangleShape load;                                         //The load element of the screen
    private float PlayerHealth;                                          //The value of the current Health
    private Vector2f screenSize;
    private Vector2f buttonSize;
    private Texture t1 = new Texture();
    private Texture t2 = new Texture();
    private Texture t3 = new Texture();
    private Texture t4 = new Texture();

     /**
     * Defines the healthBars params
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIDeadScreen(Health s)
    {
        super(s);
        PlayerHealth = s.getStat();
        screenSize = new Vector2f((float)(Game.WINDOWSIZE), 1000); //length, width of bar
        buttonSize = new Vector2f((float)(Game.WINDOWSIZE - 400), 200);
        front = new RectangleShape(buttonSize);
        back = new RectangleShape(screenSize);
        try
        {
            t1.loadFromFile(Paths.get("Assets" + File.separator + "Screens" + File.separator+ "GameOver.png"));
            //t2.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "load.png"));
            t3.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "settings.png"));
            t4.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "quit.png"));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        front.setFillColor(ScreenFront);
        front.setPosition(220, Game.WINDOWSIZE - buttonSize.y - 700);
        front.setTexture(t1);
        buttons[0] = new RectangleShape(buttonSize);
        buttons[1] = new RectangleShape(buttonSize);
        buttons[2] = new RectangleShape(buttonSize);
        buttons[0].setFillColor(ScreenFront);
        //buttons[0].setTexture(t2);
        buttons[0].setPosition(220, Game.WINDOWSIZE - buttonSize.y - 500);
        buttons[1].setFillColor(ScreenFront);
        buttons[1].setTexture(t3);
        buttons[1].setPosition(200, Game.WINDOWSIZE - buttonSize.y - 300);
        buttons[2].setFillColor(ScreenFront);
        buttons[2].setTexture(t4);
        buttons[2].setPosition(200, Game.WINDOWSIZE - buttonSize.y - 100);

        back.setFillColor(ScreenBack);
        back.setPosition(0, 0);
    }

    /**
     * Updates the End Screen shown on the GUI
     */
    @Override
    public void update()
    {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPosition(200, Game.WINDOWSIZE - buttonSize.y - 100);
        }
        front.setPosition(230, Game.WINDOWSIZE - buttonSize.y - 670);
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

        renderTarget.draw(back,renderStates);
        for (int i = 0; i < buttons.length; i++) {
            renderTarget.draw(buttons[i]);
        }
        renderTarget.draw(front,renderStates);
    }
}

