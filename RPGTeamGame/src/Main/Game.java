package Main;

import Main.Background.Background;
import Main.Background.Sprites.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.nio.file.Paths;

public class Game
{
    Player playerObject;
    Texture playerTexture = new Texture();
    View playerView;
    Clock systemClock = new Clock();
    Background bGround;

    int windowSize = 1000;
    int viewSize = 1600 * 3;
    int chunkSizeBlocks = 100;
    int chunkSizePixels = 1600;

    int numberOfChunksX = 50;
    int numberOfChunksY = 4;
    public Game()
    {



        try
        {
            playerTexture.loadFromFile(Paths.get("Assets\\Submarine.png"));
        }
        catch(Exception E){}


        playerView = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerObject = new Player(playerTexture, playerView,3,new Vector2f(((numberOfChunksX/2) * chunkSizePixels) + (viewSize/2),viewSize/2));
        bGround = new Background(chunkSizeBlocks, chunkSizePixels, numberOfChunksX, numberOfChunksY);
        runGame();
    }

    public void runGame()
    {




        RenderWindow window = new RenderWindow(new VideoMode(windowSize,windowSize),"Mine");
        //window.setFramerateLimit(60);
        int counter = 0;
        bGround.initialiseBackGround(playerObject);
        while(window.isOpen())
        {

            if(systemClock.getElapsedTime().asSeconds() >= 1.f)
            {
                bGround.setFPS(counter);
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
                bGround.updateBackGroundOnMove(playerObject,1,0);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                bGround.updateBackGroundOnMove(playerObject,-1,0);

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                bGround.updateBackGroundOnMove(playerObject,0,-1);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                bGround.updateBackGroundOnMove(playerObject,0,1);

            }

            window.clear();
            window.setView(playerView);
            window.draw(bGround);

            window.draw(playerObject);
            window.display();
        }
    }




}
