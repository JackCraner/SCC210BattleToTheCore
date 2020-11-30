package Main;

import Main.Background.Background;
import Main.ForeGround.Entities.MovingEntity;
import Main.ForeGround.Entities.Player;
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

    int windowSize = 1000;
    int viewSize = 1600;
    int chunkSizeBlocks = 100;
    int chunkSizePixels = 1600;

    int numberOfChunksX = 3;
    int numberOfChunksY = 3;
    public Game()
    {

        playerObject = new Player(1,new Vector2f(((numberOfChunksX/2) * chunkSizePixels) + (viewSize/2),viewSize/2), viewSize);
        bGround = new Background(chunkSizeBlocks, chunkSizePixels, numberOfChunksX, numberOfChunksY);

        runGame();
    }

    public void runGame()
    {




        RenderWindow window = new RenderWindow(new VideoMode(windowSize,windowSize),"Mine");
        window.setFramerateLimit(200);
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
            playerObject.moveEntity();
            if (playerObject.checkVelocityGreater())
            {
                bGround.updateBackGroundOnMove(playerObject);
            }

            window.setView(playerObject.getpView());
            window.draw(bGround);
            window.draw(playerObject);
            window.display();
        }
    }




}
