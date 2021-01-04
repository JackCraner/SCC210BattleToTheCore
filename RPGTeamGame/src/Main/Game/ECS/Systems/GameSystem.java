package Main.Game.ECS.Systems;

import Main.Game.ECS.Entity.Component;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public abstract class GameSystem
{
    private ArrayList<Component[]> gameObjectList = new ArrayList<>();

    public ArrayList<Component[]> getComponentArrayList() {
        return gameObjectList;
    }
    public void addComponentArray(Component[] c)
    {
        gameObjectList.add(c);
    }

    @Override
    public String toString()
    {
        String s;
        s = this.getClass().toString() + "\n";
        ArrayList<Component[]> c = getComponentArrayList();
        for (int i = 0; i< c.size();i++)
        {
            s += ("Object " + i + " = " + getComponentArrayList().get(i)[0].getGameObject().toString() + "\n");
            s += "\tComponents: \n";
            for (int a = 0; a < systemComponentRequirements().size();a++)
            {
                s += "\t " + c.get(i)[a].toString() + "\n";
            }
            s += "\n";
        }
        s += "Total Number of Objects: " + c.size() + "\n";
        return s;
    }

    public abstract ArrayList<Class<? extends Component>> systemComponentRequirements();

    public abstract void update();

    public abstract void update(Event event);


}

//Notes on the game system
/*
-- make this abstract interface ensure all the gamesystems are singletons

-- Change the ArrayList<Class<? extends Component> to a more rigid structure of the classes the systems want so reduce downcasting

-- ensure all GameSystems are added to the systems array in level
 */
