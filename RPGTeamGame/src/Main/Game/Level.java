package Main.Game;

import Main.Game.Entity.Component;
import Main.Game.Entity.Components.BoxCollider;
import Main.Game.Entity.Components.Position;
import Main.Game.Entity.Components.SpriteController;
import Main.Game.Entity.GameObject;
import Main.Game.Factory.Blueprint;
import Main.Game.Factory.EntityID;
import Main.Game.Systems.GameSystem;
import Main.Game.Systems.MovementGameSystem;
import Main.Game.Systems.PositionGameSystem;
import Main.Game.Systems.RendererGameSystem;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
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


        window.setFramerateLimit(60);
        isRunning = true;
        runGame();
    }

    public void runGame()
    {
        while (isRunning)
        {

            while(window.isOpen())
            {
                for (Event event : window.pollEvents()) {

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



                window.display();
            }
        }

    }

    public RenderWindow getWindow() {
        return window;
    }
}
