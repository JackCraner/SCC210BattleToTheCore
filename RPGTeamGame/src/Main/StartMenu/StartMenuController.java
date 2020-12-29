package Main.StartMenu;

import Main.System.Button;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StartMenuController
{
    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    boolean onMenu;
    public StartMenuController(RenderWindow window)
    {
        this.window = window;
        generateButtons();
        generateBackground();
        onMenu = true;
        runMenu();
    }
    public void generateBackground()
    {
        Texture backgroundTexture = new Texture();
        try
        {
           backgroundTexture.loadFromFile(Paths.get("Assets"+ File.separator +"Background.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setScale(1,1.5f);
        //backgroundSprite.setPosition(0,0);
    }
    public void generateButtons()
    {
        float width1 = (window.getSize().x / 2);
        float height1 = (window.getSize().y/10);
        buttonArray.add(new Button(1,new Vector2f(window.getSize().x/2 - (width1/2),window.getSize().y/2 - (height1/2)),width1,height1));
        buttonArray.get(0).setFillColor(Color.RED);
    }
    public int checkButtons()
    {
        for (Button b: buttonArray)
        {
            if (b.isHovered(Mouse.getPosition(window)))
            {
                return b.getId();
            }
        }
        return 0;
    }
    public void runMenu()
    {
        while(onMenu)
        {

            for (Event event : window.pollEvents())
            {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                onMenu = false;
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {
                    onMenu = false;
                }
            }
            window.clear();
            window.draw(backgroundSprite);
            for (Button b: buttonArray)
            {
                window.draw(b);
            }
            window.display();
        }


    }
}
