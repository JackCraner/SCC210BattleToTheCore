package Main.Game;

import Main.Game.Background.Background;
import Main.Game.DataTypes.PositionVector;
import Main.Game.Player.Player;
import Main.Game.ForeGround.Foreground;
import Main.Game.GUI.GUIController;
import Main.Game.Shader.ShaderController;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;


public class Game
{
    Player playerObject;

    Clock systemClock = new Clock();
    Background bGround;
    Foreground fGround;
    GUIController GUI;
    ShaderController sC;
    RenderStates rS;
    PositionVector currentPos;


    RenderTexture gameRender = new RenderTexture();
    Sprite gameWindow = new Sprite();
    Sprite miniMap = new Sprite();

    RenderWindow window;

    public static int WINDOWSIZE = 1000;
    public static int VIEWSIZE = 1000;          //change to zoom in and out (100x100 blocks)
    public static int metaSize = 100;           //max zoom out blocks
    public static int CHUNKSIZEBLOCKS = 100;
    public static int CHUNKSIZEPIXELS = 3200;
    public static int blockSize = 32;

    public static int numberOfChunksX = 3;      //change to make map bigger or smaller
    public static int numberOfChunksY = 3;

    public Game(RenderWindow window)
    {
        this.window = window;

        playerObject = new Player(1,new Vector2f(500,500), VIEWSIZE);
        currentPos = new PositionVector(playerObject.getPosition());
        bGround = new Background();
        fGround = new Foreground(playerObject, bGround.getMapObject());
        GUI = new GUIController();
        sC = new ShaderController();

        runGame();
    }

    public void runGame()
    {



        try
        {
            gameRender.create(1000,1000);
        }
        catch(Exception e)
        {

        }

        window.setFramerateLimit(100);

        int counter = 0;
        playerObject.setPosition(bGround.getMapObject().getPlayerStart());
        bGround.initialiseBackGround(playerObject);
        fGround.initaliseForeground();
        GUI.initializeGUI(playerObject);

        System.out.println(playerObject.getPosition());
        while(window.isOpen())
        {

            if(systemClock.getElapsedTime().asSeconds() >= 1.f)
            {
                GUI.setFPS(counter);
                counter = 0;
                systemClock.restart();
            }
            counter++;
            for (Event event : window.pollEvents()) {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                window.close();
            }

            Vector2f currentPos = playerObject.getPosition();


            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                playerObject.setVelocity(new Vector2f(1,0));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                playerObject.setVelocity(new Vector2f(-1,0));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                playerObject.setVelocity(new Vector2f(0,-1));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                playerObject.setVelocity(new Vector2f(0,1));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.R))
            {
                playerObject.zoom(-30);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.F))
            {
                playerObject.zoom(30);
            }
            playerObject.move();


            playerObject.moveEntity();
            if (!playerObject.hasMoved(currentPos))
            {
                bGround.updateBackGroundOnMove(playerObject);
                fGround.updateForeground(playerObject);
                //System.out.println(bGround.getMapObject().getBlockAt(playerObject.inBlockTest().x,playerObject.inBlockTest().y).getID());
            }
            window.clear();
            gameRender.clear(Color.BLACK);
            gameRender.draw(bGround);
            gameRender.draw(fGround);
            gameRender.draw(playerObject);                          //adds all the game textures into one texture
            gameRender.setView(playerObject.getpView());
            gameRender.display();

            gameWindow.setTexture(gameRender.getTexture());
            rS = new RenderStates(sC.createShader(gameWindow.getTexture(), fGround.getLightList(), playerObject));    // Gives this big texture to the shaderController (sC) to create the shader

            window.clear();
            window.draw(gameWindow,rS);         //The shader acts as a post processing effect, sort of layering ontop of the main game
            window.draw(GUI);                   //draws the GUI as the next layer ontop (uneffected by the shader)
            window.display();
        }
    }

}
