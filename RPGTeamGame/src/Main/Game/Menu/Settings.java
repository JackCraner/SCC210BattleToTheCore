package Main.Game.Menu;


import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Settings {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    ArrayList<Slider> sliderArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    Sprite textSprite = new Sprite();
    boolean onSett;
    Music m;


    public Settings(){
        window = new RenderWindow(new VideoMode(1000,1000), "Settings");
        //this.window = window;
        generateBackground();
        generateButtons();
        generateSlider();
        window.draw(backgroundSprite);
        window.draw(textSprite);
        window.display();
        onSett = true;
        runSett();
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
            textTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Sound label.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        textSprite.setTexture(textTexture);
        textSprite.setPosition(350,325);

    }
    public void generateButtons() {
        Texture backTexture = new Texture();
        try {
            backTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "back.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        buttonArray.add(new Button(1, new Vector2f(400, 800), 200, 100));
        buttonArray.get(0).setTexture(backTexture);


    }
    public void generateSlider(){
        Texture fullTexture = new Texture();
        try {
            fullTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "10.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        sliderArray.add(new Slider(1, new Vector2f(300, 400), 400, 100));
        sliderArray.get(0).setTexture(fullTexture);
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
    public int checkSlider()
    {

        for (Slider s: sliderArray)
        {

            if (s.isHovered(Mouse.getPosition(window)))
            {
                return s.getId();
            }
        }
        return 0;
    }
    public void runSett(){
        Texture fiveTexture = new Texture();
        try {
            fiveTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "5.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        Texture zeroTexture = new Texture();
        try {
            zeroTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "0.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        Texture fullTexture = new Texture();
        try {
            fullTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "10.png"));
        } catch (Exception e) {
            System.out.println(e);
        }



        while(onSett)
        {
            for (Event event : window.pollEvents())
            {

            }

            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {

                    //System.out.println("test");
                    onSett = false;
                    window.close();
                    RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"CoreControl");
                    StartMenu newStartMenu = new StartMenu(window);


                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM5)){
                if (checkSlider() == 1) {

                    sliderArray.get(0).setTexture(fiveTexture);
                    m = new Music();
                    try {
                        m.openFromFile(Paths.get("Assets" + File.separator + "Sound" + File.separator + "cave_Background.ogg"));
                    }catch (Exception e) {
                        System.out.println("no" + e);
                    }
                    m.setVolume(30);
                    m.play();

                }

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM0)){
                if (checkSlider() == 1) {

                    sliderArray.get(0).setTexture(zeroTexture);
                    m = new Music();
                    try {
                        m.openFromFile(Paths.get("Assets" + File.separator + "Sound" + File.separator + "cave_Background.ogg"));
                    }catch (Exception e) {
                        System.out.println("no" + e);
                    }
                    m.setVolume(0);
                    m.play();
                }

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM9)){
                if (checkSlider() == 1) {

                    sliderArray.get(0).setTexture(fullTexture);
                    m = new Music();
                    try {
                        m.openFromFile(Paths.get("Assets" + File.separator + "Sound" + File.separator + "cave_Background.ogg"));
                    }catch (Exception e) {
                        System.out.println("no" + e);
                    }
                    m.setVolume(100);
                    m.play();
                }

            }
            window.clear();
            window.draw(backgroundSprite);
            window.draw(textSprite);
            for (Button b: buttonArray)
            {
                window.draw(b);
            }
            for (Slider s: sliderArray)
            {
                window.draw(s);
            }
            window.display();
        }
    }
}

