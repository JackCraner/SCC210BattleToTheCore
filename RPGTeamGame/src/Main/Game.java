package Main;

import Main.Background.Background;
import Main.DataTypes.PositionVector;
import Main.ForeGround.Entities.Player;
import Main.ForeGround.Foreground;
import Main.GUI.GUIController;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class Game
{
    Player playerObject;

    Clock systemClock = new Clock();
    Background bGround;
    Foreground fGround;
    GUIController GUI;
    PositionVector currentPos;


    RenderTexture gameRender = new RenderTexture();

    int windowSize = 1000;
    int viewSize = 1600;
    public static int chunkSizeBlocks = 100;
    public static int chunkSizePixels = 1600;

    int numberOfChunksX = 3;
    int numberOfChunksY = 3;

    public Game()
    {

        playerObject = new Player(1,new Vector2f(((numberOfChunksX/2) * chunkSizePixels) + (viewSize/2),viewSize/2), viewSize);
        currentPos = new PositionVector(playerObject.getPosition());
        bGround = new Background(chunkSizeBlocks, chunkSizePixels, numberOfChunksX, numberOfChunksY);
        fGround = new Foreground();
        GUI = new GUIController();
        runGame();
    }

    public void runGame()
    {




        RenderWindow window = new RenderWindow(new VideoMode(windowSize,windowSize),"Mine");
        window.setFramerateLimit(100);
        window.setVerticalSyncEnabled(true);    //??
        int counter = 0;
        bGround.initialiseBackGround(playerObject);
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
            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                playerObject.addVelocity(new Vector2f(1,0));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                playerObject.addVelocity(new Vector2f(-1,0));

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                playerObject.addVelocity(new Vector2f(0,-1));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                playerObject.addVelocity(new Vector2f(0,1));



            }

            window.clear();

            if (playerObject.checkVelocityGreater())
            {
                playerObject.moveEntity();
                bGround.updateBackGroundOnMove(playerObject);
                GUI.updateGUI(playerObject);
                currentPos = new PositionVector(playerObject.getPosition());
            }

            window.setView(playerObject.getpView());
            window.draw(bGround);
            window.draw(fGround);
            window.draw(playerObject);
            window.draw(GUI);
            window.display();
        }
    }




}
