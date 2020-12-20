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
    Sprite miniMap = new Sprite();
    public static int windowSize = 1000;
    public static int viewSize = 1000;
    public static int chunkSizeBlocks = 100;
    public static int chunkSizePixels = 3200;
    public static int blockSize = 32;

    public static int numberOfChunksX = 3;
    public static int numberOfChunksY = 3;

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
        //window.setFramerateLimit(100);
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
            playerObject.move();


            playerObject.moveEntity();
            if (!playerObject.hasMoved(currentPos))
            {
                System.out.println(playerObject.inChunk() + " " +  playerObject.inBlock());
                bGround.updateBackGroundOnMove(playerObject);
            }

            window.clear();
            gameRender.clear(Color.BLACK);
            gameRender.draw(bGround);
            gameRender.draw(fGround);
            gameRender.draw(playerObject);                          //adds all the game textures into one texture
            gameRender.setView(playerObject.getpView());
            gameRender.display();

            gameWindow.setTexture(gameRender.getTexture());
            rS = new RenderStates(sC.createShader(gameWindow.getTexture()));    // Gives this big texture to the shaderController (sC) to create the shader

            window.clear();
            window.draw(gameWindow,rS);         //The shader acts as a post processing effect, sort of layering ontop of the main game
            window.draw(GUI);                   //draws the GUI as the next layer ontop (uneffected by the shader)

            window.display();
        }
    }

    public boolean checkOnLand()
    {
        Vector2f playerLocation = playerObject.inBlock();
        Vector2f underPlayerBlockLocation = new Vector2f(playerLocation.x, playerLocation.y + 1);       //bug with +1

        int underPlayerBlockId = bGround.getMapObject().getChunkAtPosition(playerObject.inChunk()).getBlockAtVector(underPlayerBlockLocation).getID();

        if (underPlayerBlockId == 0 && new CEntity(playerLocation, blockSize,blockSize).getcBox().checkCollistion(new Cblock(underPlayerBlockLocation).getcBox()))
        {
            return true;
        }

        return false;
    }
}
