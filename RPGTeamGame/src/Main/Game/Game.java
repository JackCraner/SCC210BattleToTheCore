package Main.Game;

import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Systems.*;
import Main.Game.GUI.GUIManager;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import Main.Game.MapGeneration.MapManager;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


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
    private boolean isRunning = false;


    private RenderWindow window;
    private FPSCounter testPerformance;
    private Clock systemClock = new Clock();

    public static EntityManager ENTITYMANAGER = EntityManager.getEntityManagerInstance();
    private static SystemManager SYSTEMMANAGER =SystemManager.getSystemManagerInstance();

    public static Game getGame()
    {
        return levelInstance;
    }

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
        ENTITYMANAGER.addGameObject(MapManager.getInstance().generateMap());


        ENTITYMANAGER.addGameObject(PLAYER);


        ENTITYMANAGER.addGameObject(Blueprint.chest(new Vector2f(CellularAutomata.CHUNKSIZEPIXELSX/2+50,CellularAutomata.CHUNKSIZEPIXELSY/2+50)));




        //TESTING
        testPerformance = new FPSCounter();

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

        int counter =0;
        window.setFramerateLimit(200);
        while(window.isOpen())
        {
            if(systemClock.getElapsedTime().asSeconds() >= 1.f)
            {
                testPerformance.setFPS(counter);
                counter = 0;
                systemClock.restart();
            }
            counter++;

            for (Event event : window.pollEvents())
            {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                window.close();
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.D)) {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.A)) {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.W)) {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.S)) {

            }


            window.clear();

            for (GameObject g : ENTITYMANAGER.getGameObjectInVicinity(PLAYER.getPosition(), Camera.cameraInstance().camerView.getSize().x/2)) {
               SYSTEMMANAGER.addGameObjectTOSYSTEMS(g);
            }
            SYSTEMMANAGER.updateSystems();

            window.draw(testPerformance.getFpsCounter());

            //window.draw(gui);
            window.display();
        }
    }



    public RenderWindow getWindow() {
        return window;
    }

}
