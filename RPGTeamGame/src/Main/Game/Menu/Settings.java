package Main.Game.Menu;


import Main.Game.ECS.Systems.SoundGameSystem;
import org.jsfml.audio.Listener;
import org.jsfml.graphics.*;
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
    ArrayList<Textimage> textimagesArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    boolean onSett;
    int volcount = 10;

    public Settings(){
        window = new RenderWindow(new VideoMode(1000,1000), "Settings");
        //this.window = window;
        generateBackground();
        generateButtons();
        generateSlider();
        generateText();
        window.draw(backgroundSprite);
        window.display();
        onSett = true;
        volcount = 10;
        runSett();
    }

    public void generateBackground()
    {

        Texture backgroundTexture = new Texture();
        try
        {
            backgroundTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "MenuBackground.jpg"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setScale(1,1f);
        //backgroundSprite.setPosition(0,0);


    }
    public void generateText(){
        Texture labelTexture = new Texture();
        Texture numdetsTexture = new Texture();
        try
        {
            labelTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "sound label.png"));
            numdetsTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "numdets.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        textimagesArray.add(new Textimage(1,new Vector2f(300, 275), 400, 75));
        textimagesArray.get(0).setTexture(labelTexture);
        textimagesArray.add(new Textimage(2,new Vector2f(300, 455), 400, 100));
        textimagesArray.get(1).setTexture(numdetsTexture);

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

        sliderArray.add(new Slider(1, new Vector2f(300, 350), 400, 100));
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
    public int checkText()
    {

        for (Textimage t: textimagesArray)
        {
            return t.getId();
        }
        return 0;
    }
    public void runSett(){

        Texture zeroTexture = new Texture();
        Texture oneTexture = new Texture();
        Texture twoTexture = new Texture();
        Texture threeTexture = new Texture();
        Texture fourTexture = new Texture();
        Texture fiveTexture = new Texture();
        Texture sixTexture = new Texture();
        Texture sevenTexture = new Texture();
        Texture eightTexture = new Texture();
        Texture fullTexture = new Texture();
        try {
            zeroTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "0.png"));
            oneTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "1.png"));
            twoTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "2.png"));
            threeTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "3.png"));
            fourTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "4.png"));
            fiveTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "5.png"));
            sixTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "6.png"));
            sevenTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "7.png"));
            eightTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "8.png"));
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

            if (Keyboard.isKeyPressed(Keyboard.Key.NUM9)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fullTexture);
                    Listener.setGlobalVolume(29);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM8)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(eightTexture);
                    Listener.setGlobalVolume(25);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM7)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(sevenTexture);
                    Listener.setGlobalVolume(22);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM6)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(sixTexture);
                    Listener.setGlobalVolume(18);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM5)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fiveTexture);
                    Listener.setGlobalVolume(15);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM4)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fourTexture);
                    Listener.setGlobalVolume(12);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM3)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(threeTexture);
                    Listener.setGlobalVolume(10);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM2)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(twoTexture);
                    Listener.setGlobalVolume(6);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM1)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(oneTexture);
                    Listener.setGlobalVolume(2);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM0)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(zeroTexture);
                    Listener.setGlobalVolume(0);
                }
            }

            window.clear();
            window.draw(backgroundSprite);
            for (Button b: buttonArray)
            {
                window.draw(b);
            }
            for (Slider s: sliderArray)
            {
                window.draw(s);
            }
            for (Textimage t: textimagesArray)
            {
                window.draw(t);
            }
            window.display();
        }
    }
}

