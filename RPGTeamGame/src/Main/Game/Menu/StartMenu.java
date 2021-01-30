package Main.Game.Menu;


import Main.Game.Menu.Button;
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

public class StartMenu {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    boolean onMenu;

    public StartMenu(RenderWindow window)
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
            backgroundTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "MenuBackground.png"));
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
        Texture playTexture = new Texture();
        try
        {
            playTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "start.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(1,new Vector2f(150, 400), 300, 100));
        buttonArray.get(0).setTexture(playTexture);

        Texture quitTexture = new Texture();
        try
        {
            quitTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "quit.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(2,new Vector2f(150,550),300,100));
        buttonArray.get(1).setTexture(quitTexture);

        Texture helpTexture = new Texture();
            try
        {
            helpTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Help.png"));
        }
            catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(3,new Vector2f(550,550),300,100));
        buttonArray.get(2).setTexture(helpTexture);

        Texture credTexture = new Texture();
        try
        {
            credTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Credits.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(4,new Vector2f(550,700),300,100));
        buttonArray.get(3).setTexture(credTexture);

        Texture conTexture = new Texture();
        try
        {
            conTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Continue.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(5,new Vector2f(550, 400), 300, 100));
        buttonArray.get(4).setTexture(conTexture);

        Texture settTexture = new Texture();
        try
        {
            settTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Settings.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(6,new Vector2f(150, 700), 300, 100));
        buttonArray.get(5).setTexture(settTexture);
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
                    window.close();
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 2)
                {
                    window.close();
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 3)
                {
                    window.close();
                    Help help = new Help();
                    onMenu = false;
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 4)
                {
                    window.close();
                    Credits creds = new Credits();
                    onMenu = false;
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 6)
                {
                    window.close();
                    Settings sett = new Settings();
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
