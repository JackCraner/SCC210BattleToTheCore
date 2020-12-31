package Main;

import Main.Entity.Components.*;
import Main.Entity.Factory.Blueprint;
import Main.Entity.Factory.EntityID;
import Main.Entity.GameObject;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class Main
{


    public static void main(String[] args)
    {
        ArrayList<GameObject> gameObjectList = new ArrayList<>();
        RenderWindow window = new RenderWindow(new VideoMode(1000,1000), "Test");

        GameObject player = Blueprint.createGameObject(EntityID.PLAYER, new Vector2f(100,100));
        GameObject chest = Blueprint.createGameObject(EntityID.CHEST, new Vector2f(200,200));

        GameObject wall = new GameObject("Wall", new Vector2f(500,500));
        wall.addComponent(new SpriteController(new Vector2f(100,100), Color.RED));
        wall.addComponent(new BoxCollider());

        gameObjectList.add(wall);
        gameObjectList.add(player);
        gameObjectList.add(chest);

        window.setFramerateLimit(60);

        for (GameObject g: gameObjectList)
        {
            g.start();
        }
        while(window.isOpen())
        {
            for (Event event : window.pollEvents()) {

            }
            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
                window.close();
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                player.getComponent(Movement.class).move(new Vector2f(1,0));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                player.getComponent(Movement.class).move(new Vector2f(-1,0));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                player.getComponent(Movement.class).move(new Vector2f(0,-1));
            }
            if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                player.getComponent(Movement.class).move(new Vector2f(0,1));
            }





            window.clear();

            player.getComponent(BoxCollider.class).checkCollisions(wall.getComponent(BoxCollider.class).getBox());
            for (GameObject g: gameObjectList)
            {

                g.update(0);
                window.draw(g);
            }

            window.display();

        }
    }






}
