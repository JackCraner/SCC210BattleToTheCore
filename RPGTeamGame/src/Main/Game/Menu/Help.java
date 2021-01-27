package Main.Game.Menu;

import Main.Game.Menu.Button;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Help {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    Sprite textSprite = new Sprite();
    boolean onHelp;

    public Help(){
        window = new RenderWindow(new VideoMode(1000,1000), "Help");
        //this.window = window;
        generateBackground();
        generateButtons();
        window.draw(backgroundSprite);
        window.draw(textSprite);
        window.display();
        onHelp = true;
        runHelp();
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

        Texture textTexture = new Texture();
        try
        {
            textTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "instructions.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        textSprite.setTexture(textTexture);
        textSprite.setPosition(300,450);
    }
    public void generateButtons() {
        Texture quitTexture = new Texture();
        try {
            quitTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "back.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        buttonArray.add(new Button(1, new Vector2f(400, 800), 200, 100));
        buttonArray.get(0).setTexture(quitTexture);

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
    public void runHelp(){
        while(onHelp)
        {
        for (Event event : window.pollEvents())
        {

        }
        if (Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if (checkButtons() == 1)
            {
                //System.out.println("test");
                onHelp = false;
                window.close();
                RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"CoreControl");
                StartMenu newStartMenu = new StartMenu(window);


            }
        }
        window.clear();
        window.draw(backgroundSprite);
        window.draw(textSprite);
        for (Button b: buttonArray)
        {
            window.draw(b);
        }
        window.display();
        }
    }
}
