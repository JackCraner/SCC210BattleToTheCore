package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SystemManager
{

    ArrayList<Class<? extends Component>> componentArray = new ArrayList<>(Arrays.asList(Position.class, Size.class, TextureComponent.class, Movement.class, Collider.class));
    HashMap<Class,ArrayList<Component>> fullComponentArray = new HashMap<>();
    ArrayList<GameSystem> systemArray = new ArrayList<>(Arrays.asList(MovementGameSystem.getSystemInstance(),PhysicsSystem.getSystemInstance(),RendererGameSystem.getSystemInstance()));


    private static SystemManager systemManager = new SystemManager();
    public static SystemManager getInstance()
    {
        return systemManager;
    }
   // Class<? extends Component> componentArray[] = {Position.class, Size.class};


    private SystemManager()
    {
        for(Class c: componentArray)
        {


            fullComponentArray.put(c,new ArrayList<>());

        }

    }
    public void init()
    {
        ArrayList<GameObject> gameObjects = Game.getGame().getGameObjectList();
        System.out.println(fullComponentArray.get(Position.class));
        for (GameObject g: gameObjects)
        {
            for (Component c: g.getComponentList())
            {

                fullComponentArray.get(c.getClass()).add(c);
            }
        }

        for (GameSystem s : systemArray)
        {

        }
    }




//array of generic arrayLists and then within the systems have an init that passing in the type they want
    //cast them to the type in this class
    // then in the system class define the type so its type safe
}
