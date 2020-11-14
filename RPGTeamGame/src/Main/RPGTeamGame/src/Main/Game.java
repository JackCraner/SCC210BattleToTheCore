package Main;

import Main.MapGen.Chunk;
import Main.MapGen.ChunkHandler;
import Main.Sprites.Player;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.nio.file.Paths;

public class Game
{
    //GameObjects
    private RenderWindow window;
    private Player playerObject;
    private Texture playerTexture;
    private ChunkHandler cH;
    private View playerView;
    //MapGenerationParameters
    private int chunkSizeBlocks = 100;
    private int chunkSizePixels = 1600;

    private int renderArea = 3;                //3x3 chunks
    private int initalGeneration = 3;      //3x3 chunks
    private int zoomOutRate = 3;
    private int playerSpeed = 15;
    public Game()
    {
        this.window = new RenderWindow(new VideoMode(1000,1000), "Minecraft");
        playerTexture = new Texture();
        try
        {
            playerTexture.loadFromFile(Paths.get("Assets\\Submarine.png"));
        }
        catch(Exception E){}

        Vector2f playerPosition = new Vector2f(chunkSizePixels/2,chunkSizePixels/2);
        playerView = new View(playerPosition, new Vector2f(chunkSizePixels*zoomOutRate,chunkSizePixels*zoomOutRate));
        playerObject = new Player(playerTexture,playerView, playerSpeed,playerPosition);


        cH = new ChunkHandler(chunkSizeBlocks,chunkSizePixels,renderArea,initalGeneration);

        runGame();


    }

    public void runGame()
    {
        while(window.isOpen())
        {
            for (Event event: window.pollEvents())
            {

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
            {
                window.close();
            }

            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
               updatePlayer(1,0);

            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                updatePlayer(-1,0);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                updatePlayer(0,-1);
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                updatePlayer(0,1);
            }


            window.clear();
            window.setView(playerView);

            for (Chunk c: cH.getVisibleChunks())
            {
                c.drawScreen(window);
            }
            window.draw(playerObject);

            window.display();
        }
    }

    public void updatePlayer(int x, int y)
    {
        Vector2f currentPositionPlayer = playerObject.inChunk(chunkSizePixels);
        playerObject.movePlayer(x,y);
        if (currentPositionPlayer.x != playerObject.inChunk(chunkSizePixels).x || currentPositionPlayer.y != playerObject.inChunk(chunkSizePixels).y)
        {
            System.out.println("Player: " + playerObject.inChunk(chunkSizePixels));
            cH.updateVisibleChunks(playerObject.inChunk(chunkSizePixels));
        }

    }






}
