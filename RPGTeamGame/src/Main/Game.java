package Main;

import Main.Background.Background;
import Main.Background.MapGen.Block;
import Main.DataTypes.PositionVector;
import Main.ForeGround.Entities.Player;
import Main.ForeGround.Foreground;
import Main.GUI.GUIController;
import Main.Physics.CEntity;
import Main.Physics.Cblock;
import Main.Shader.ShaderController;
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
    ShaderController sC;
    RenderStates rS;
    PositionVector currentPos;


    RenderTexture gameRender = new RenderTexture();
    Sprite gameWindow = new Sprite();

    public static int windowSize = 1000;
    public static int viewSize = 1600;
    public static int chunkSizeBlocks = 100;
    public static int chunkSizePixels = 1600;
    public static int blockSize = 16;

    int numberOfChunksX = 3;
    int numberOfChunksY = 3;

    public Game()
    {

        playerObject = new Player(1,new Vector2f(((numberOfChunksX/2) * chunkSizePixels) + (viewSize/2),viewSize/2), viewSize);
        currentPos = new PositionVector(playerObject.getPosition());
        bGround = new Background(chunkSizeBlocks, chunkSizePixels, numberOfChunksX, numberOfChunksY);
        fGround = new Foreground();
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

            Vector2f currentPos = playerObject.getPosition();

            if (checkOnLand())// touching block
            {
                if(Keyboard.isKeyPressed(Keyboard.Key.D))
                {
                    playerObject.setVelocity(new Vector2f(1,0));
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.A))
                {
                    playerObject.setVelocity(new Vector2f(-1,0));
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.SPACE))
                {
                    playerObject.setVelocity(new Vector2f(0,-1));
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.S))
                {
                    playerObject.setVelocity(new Vector2f(0,1));
                }
                playerObject.move();
            }
            else // not touching block
            {
                if(Keyboard.isKeyPressed(Keyboard.Key.D))
                {
                    playerObject.setVelocityWithGravity(new Vector2f(1,0));
                }
                if(Keyboard.isKeyPressed(Keyboard.Key.A))
                {
                    playerObject.setVelocityWithGravity(new Vector2f(-1,0));
                }
                playerObject.moveWithGravity();
            }

            playerObject.moveEntity();
            if (!playerObject.hasMoved(currentPos))
            {

                bGround.updateBackGroundOnMove(playerObject);
            }

            window.clear();
            gameRender.clear(Color.BLACK);
            gameRender.setView(playerObject.getpView());
            gameRender.draw(bGround);
            gameRender.draw(fGround);
            gameRender.draw(playerObject);
            gameRender.display();

            gameWindow.setTexture(gameRender.getTexture());
            rS = new RenderStates(sC.createShader(gameWindow.getTexture()));

            window.clear();
            window.draw(gameWindow,rS);
            window.draw(GUI);

            window.display();
        }
    }

    public boolean checkOnLand()
    {
        Vector2f playerLocation = playerObject.inBlock();
        Vector2f underPlayerBlockLocation = new Vector2f(playerLocation.x, playerLocation.y + 1);

        int underPlayerBlockId = bGround.getMapObject().getChunkAtPosition(playerObject.inChunk()).getBlockAtVector(underPlayerBlockLocation).getID();

        if (underPlayerBlockId == 0 && new CEntity(playerLocation, blockSize,blockSize).getcBox().checkCollistion(new Cblock(underPlayerBlockLocation).getcBox()))
        {
            return true;
        }

        return false;
    }

}
