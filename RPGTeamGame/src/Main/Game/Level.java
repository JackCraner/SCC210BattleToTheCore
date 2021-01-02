package Main.Game;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EntityID;
import Main.Game.ECS.Systems.GameSystem;
import Main.Game.ECS.Systems.MovementGameSystem;
import Main.Game.ECS.Systems.PositionGameSystem;
import Main.Game.ECS.Systems.RendererGameSystem;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import Main.Game.MapGeneration.MapManager;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class Level
{
    private static Level levelInstance = new Level();

    private boolean isRunning = false;
    private ArrayList<GameObject> gameObjectList = new ArrayList<>();
    private ArrayList<GameSystem> systemList = new ArrayList<>();

    private RenderWindow window;


    FPSCounter testPerformance;
    Clock systemClock = new Clock();

    public static Level getLevel()
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
        window = new RenderWindow(new VideoMode(1000,1000), "Test");

        System.out.println(CellularAutomata.getInstance().generateBinaryMapping());




        gameObjectList.add(Blueprint.player(new Vector2f(100,100)));
        startTime = System.nanoTime();
        gameObjectList.addAll(MapManager.getInstance().generateMap());
        endTime = System.nanoTime();
        System.out.println("Building Map");
        System.out.println("Time Taken: " + (endTime-startTime) + "\n");
        gameObjectList.add(Blueprint.chest(new Vector2f(200,200)));





        systemList.add(PositionGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());
        systemList.add(MovementGameSystem.getSystemInstance());


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

        isRunning = true;
        runGame();
    }

    public void runGame()
    {
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

                    s.update(event);



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


            for (GameSystem s: systemList)
            {
                s.update();



            }

            window.draw(testPerformance.getFpsCounter());

            window.display();
        }
    }



    public RenderWindow getWindow() {
        return window;
    }
}
