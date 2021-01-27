package Main.Game.Menu;

import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Systems.*;
import Main.Game.GUI.GUIComponents.GUIModeEnum;
import Main.Game.GUI.GUIManager;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import Main.Game.MapGeneration.Map;
import Main.Game.MapGeneration.MapBlueprint;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.File;
import java.nio.file.Paths;


/*
Main Game/Engine Class

Handles
- Central Game Initalization
- Central Game Loop
- EntityManager
- SystemManager
 */
public class Game
{

    public static int WINDOWSIZE = 1000;


    public static GameObject PLAYER = Blueprint.player(new Vector2f(CellularAutomata.CHUNKSIZEPIXELSX/2,CellularAutomata.CHUNKSIZEPIXELSY/2));

    private static Game levelInstance = new Game();
    public boolean isRunning = false;


    private RenderWindow window;


    public static EntityManager ENTITYMANAGER = EntityManager.getEntityManagerInstance();
    private static SystemManager SYSTEMMANAGER =SystemManager.getSystemManagerInstance();

    private static GUIManager GUIMANAGER = GUIManager.getGUIinstance();

    public static Game getGame()
    {
        return levelInstance;
    }

    //CHEATING
    Text fpsCounter;                //Displays the number of frames per second
    Font textFont = new Font();     //The font for Displaying text
    public RenderTexture hitboxs = new RenderTexture();

    public void generateLevel()
    {

    }

    /**
     * Starts the Game
     * - Generates Background
     * - Adds Player
     *
     *  TO Do
     *  -Add GUI
     */
    public void startGame()
    {
        window = new RenderWindow(new VideoMode(WINDOWSIZE,WINDOWSIZE), "Battle_To_The_Core");



        MapBlueprint mb = new MapBlueprint(4,Map.MAP2);
        ENTITYMANAGER.addGameObject(PLAYER);
        ENTITYMANAGER.addGameObject(Blueprint.sword(new Vector2f(PLAYER.getComponent(Position.class).getPosition().x + 50,PLAYER.getComponent(Position.class).getPosition().y + 50)));
        ENTITYMANAGER.addGameObject(Blueprint.wand(new Vector2f(PLAYER.getComponent(Position.class).getPosition().x + 70,PLAYER.getComponent(Position.class).getPosition().y + 70)));
        ENTITYMANAGER.addGameObject(Blueprint.enemy(new Vector2f(PLAYER.getComponent(Position.class).getPosition().x + 100,PLAYER.getComponent(Position.class).getPosition().y + 100)));
        ENTITYMANAGER.addGameObject(Blueprint.enemy(new Vector2f(PLAYER.getComponent(Position.class).getPosition().x + 150,PLAYER.getComponent(Position.class).getPosition().y + 100)));
        ENTITYMANAGER.addGameObject(Blueprint.enemy(new Vector2f(PLAYER.getComponent(Position.class).getPosition().x + 200,PLAYER.getComponent(Position.class).getPosition().y + 100)));

        //TESTING

        try
        {
            textFont.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
            //hitboxs.create(1000,1000);
        }
        catch (Exception e)
        {
            System.out.println("Font not found");
        }
        fpsCounter = new Text("HI", textFont, 100);     //Fps font and size
        fpsCounter.setPosition(new Vector2f(Game.getGame().getWindow().getSize().x-970,30));
        Camera.cameraInstance().camerView.setCenter(PLAYER.getComponent(Position.class).getPosition());
        isRunning = true;
        runGame();
    }

    /**
     * Main Game loop section
     *
     * Gets all nearby objects from the QuadTree and gives them to the System manager
     * SystemManager then updates Systems
     */
    public void runGame()
    {

        //window.setFramerateLimit(60);
       // window.setVerticalSyncEnabled(true);

        Clock frameTimer = new Clock();
        Clock frameRateTimer = new Clock();
        int frameCounter =0;
        float frameTime =0;
        while(window.isOpen())
        {

            for (Event event : window.pollEvents())
            {
                if (event.type == Event.Type.KEY_PRESSED)
                {
                    if(((KeyEvent)event).key == Keyboard.Key.T)
                    {
                        isRunning = !isRunning;
                        if (isRunning)
                        {
                            GUIMANAGER.swapModes(GUIModeEnum.GAME);
                        }
                        else
                        {
                            GUIMANAGER.swapModes(GUIModeEnum.MENU);
                        }


                    }
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                window.close();
            }

            frameTime = frameTimer.restart().asSeconds();
            if (frameRateTimer.getElapsedTime().asSeconds() >= 1)
            {
                fpsCounter.setString(String.valueOf(frameCounter));
                frameRateTimer.restart();
                frameCounter =0;
            }
            frameCounter ++;
            window.clear();
            if(isRunning)
            {

                for (GameSystem system: SYSTEMMANAGER.getSystemList())
                {
                    for (GameObject g : ENTITYMANAGER.getGameObjectInVicinity(PLAYER.getComponent(Position.class).getPosition(), 530))
                    {
                        SYSTEMMANAGER.addGOtoSYSTEM(g,system);
                    }
                    SYSTEMMANAGER.updateSystem(system,frameTime);

                }
                SYSTEMMANAGER.flushSystems();




/*
            hitboxs.display();
            hitboxs.setView(Camera.cameraInstance().camerView);
            window.draw(new Sprite(hitboxs.getTexture()));


 */

            }
            window.draw(RendererGameSystem.getSystemInstance().screenSprite,new RenderStates(LightingGameSystem.getLightingGameSystem().mapShader));

            window.draw(GUIMANAGER);
            window.draw(fpsCounter);
            window.display();

        }





    }




    public RenderWindow getWindow() {
        return window;
    }

}
