package Main.Game;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.BoxCollider;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.SpriteController;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EntityID;
import Main.Game.ECS.Systems.GameSystem;
import Main.Game.ECS.Systems.MovementGameSystem;
import Main.Game.ECS.Systems.PositionGameSystem;
import Main.Game.ECS.Systems.RendererGameSystem;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
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
        window = new RenderWindow(new VideoMode(1000,1000), "Test");


        GameObject player = Blueprint.createGameObject(EntityID.PLAYER, new Vector2f(100,100));
        GameObject chest = Blueprint.createGameObject(EntityID.CHEST, new Vector2f(200,200));
        GameObject wall = new GameObject("Wall");
        wall.addComponent(new Position(500,500));
        wall.addComponent(new SpriteController(new Vector2f(100,100), Color.RED,new Vector2f(500,500)));
        wall.addComponent(new BoxCollider());

        gameObjectList.add(player);
        gameObjectList.add(wall);
        gameObjectList.add(chest);


        for (int a = 0; a < 10; a++)
        {
            GameObject playerTest = Blueprint.createGameObject(EntityID.PLAYER, new Vector2f(10 * a,10 * a));
            gameObjectList.add(playerTest);
        }
        systemList.add(PositionGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());
        systemList.add(MovementGameSystem.getSystemInstance());

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

        for (GameSystem s: systemList)
        {
            System.out.println(s.toString());
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
