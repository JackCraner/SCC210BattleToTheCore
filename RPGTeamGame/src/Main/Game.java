package Main;

import Main.MapGen.ChunkLoader;
import Main.MapGen.Map;
import Main.Sprites.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.nio.file.Paths;

public class Game
{
    ChunkLoader cH;
    Player playerObject;
    Texture playerTexture = new Texture();
    View playerView;
    View miniMapView;
    Text fpsCounter;
    Font textFont = new Font();
    Clock systemClock = new Clock();
    Map mapObject;

    RenderWindow window;

    int windowSize = 1000;
    int viewSize = 1600;
    int minimapViewSize = 4800;
    int chunkSizeBlocks = 100;
    int chunkSizePixels = 1600;

    int numberOfChunksX = 50;
    int numberOfChunksY = 4;
    public Game()
    {



        try
        {
            playerTexture.loadFromFile(Paths.get("Assets\\Submarine.png"));
            textFont.loadFromFile(Paths.get("Assets\\LEMONMILK-Regular.otf"));
        }
        catch(Exception E){}

        fpsCounter = new Text("HI", textFont, 100);
        playerView = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        miniMapView = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(minimapViewSize,minimapViewSize));
        playerObject = new Player(playerTexture, playerView,miniMapView,fpsCounter,3,new Vector2f(((numberOfChunksX/2) * chunkSizePixels) + (viewSize/2),viewSize/2));
        mapObject = new Map(chunkSizeBlocks,chunkSizePixels,numberOfChunksX,numberOfChunksY);
        cH = new ChunkLoader(chunkSizeBlocks,chunkSizePixels,mapObject,playerObject);

        runGame();
    }

    public void runGame()
    {




        RenderWindow window = new RenderWindow(new VideoMode(windowSize,windowSize),"Mine");
        //window.setFramerateLimit(60);
        int counter = 0;
        while(window.isOpen())
        {

            if(systemClock.getElapsedTime().asSeconds() >= 1.f)
            {
                fpsCounter.setString(String.valueOf(counter));
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
                updateScene(1,0);

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                updateScene(-1,0);

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                updateScene(0,-1);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                updateScene(0,1);

            }

            window.clear();
            window.setView(playerView);
            window.draw(cH);
            window.draw(fpsCounter);
            window.draw(playerObject);
            window.display();
        }
    }

    public void updateScene(int x, int y)
    {

        Vector2f currentPositionPlayer = playerObject.inChunk(chunkSizePixels);
        playerObject.movePlayer(x,y);
        if (currentPositionPlayer.x != playerObject.inChunk(chunkSizePixels).x || currentPositionPlayer.y != playerObject.inChunk(chunkSizePixels).y)
        {
            System.out.println(playerObject.inChunk(chunkSizePixels));
            cH.generateMetaData();

        }
        cH.generateBlockArray();


    }


}
