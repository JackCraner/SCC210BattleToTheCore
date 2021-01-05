package Main.Game;

import Main.Game.ECS.Entity.Component;
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

import java.util.ArrayList;
import java.util.Arrays;

public class Game
{

    public static int WINDOWSIZE = 1000;


    public static GameObject PLAYER = Blueprint.player(new Vector2f(CellularAutomata.CHUNKSIZEPIXELSX/2,CellularAutomata.CHUNKSIZEPIXELSY/2));

    private static Game levelInstance = new Game();
    private boolean isRunning = false;

    private ArrayList<GameObject> blockList = new ArrayList<>();
    private ArrayList<GameObject> gameObjectList = new ArrayList<>();
    private ArrayList<GameSystem> systemList = new ArrayList<>();
    private RenderWindow window;


    FPSCounter testPerformance;
    Clock systemClock = new Clock();

    public static Game getGame()
    {
        return levelInstance;
    }

    public void generateLevel()
    {

    }


    public void startGame()
    {
        long startTime;
        long endTime;
        window = new RenderWindow(new VideoMode(WINDOWSIZE,WINDOWSIZE), "Test");





        startTime = System.nanoTime();
        gameObjectList.addAll(MapManager.getInstance().generateMap());
        //seperate blocks and objects --
        endTime = System.nanoTime();
        System.out.println("Building Map");
        System.out.println("Time Taken: " + (endTime-startTime) + "\n");


        gameObjectList.add(PLAYER);
        gameObjectList.add(Blueprint.player(new Vector2f(CellularAutomata.CHUNKSIZEPIXELSX/2+30,CellularAutomata.CHUNKSIZEPIXELSY/2+30)));
        gameObjectList.add(Blueprint.chest(new Vector2f(200,200)));


        systemList.add(MovementGameSystem.getSystemInstance());
        systemList.add(PhysicsGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());


        startTime = System.nanoTime();

        for (GameObject g : gameObjectList) {
            for (GameSystem s: systemList)
            {
               Component[] c = Blueprint.getCompatibleComponents(g,s);
                if (c !=null)
                {
                    s.addComponentArray(c);
                }

            }

        }
        endTime = System.nanoTime();
        System.out.println("Adding Components to Systems");
        System.out.println("Time Taken: " + (endTime-startTime));

        for (GameSystem s: systemList)
        {
            //System.out.println(s.toString());
        }



        //TESTING
        testPerformance = new FPSCounter();
        SystemManager.getInstance().init();
        isRunning = true;
        runGame();
    }

    public void runGame()
    {
        GUIManager gui = new GUIManager();
        int counter =0;
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

                for (GameSystem s: systemList)
                {

                   // s.update(event);



                }
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

            long start = 0,end = 0;
            for (GameSystem s: systemList)
            {
                start = System.nanoTime();
                s.update();
                end = System.nanoTime();
                //System.out.println("System: " + s.getClass() + " took " + (end - start));


            }

            window.draw(testPerformance.getFpsCounter());
            //window.draw(gui);
            window.display();
        }
    }



    public RenderWindow getWindow() {
        return window;
    }

    public ArrayList<GameObject> getGameObjectList() {
        return gameObjectList;
    }
}
