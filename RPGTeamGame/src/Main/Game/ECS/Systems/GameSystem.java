package Main.Game.ECS.Systems;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public abstract class GameSystem
{
    private int bitMaskRequirement =0;

    private ArrayList<GameObject> gameObjectList = new ArrayList<>();

    public ArrayList<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public void addGameObject(GameObject c)
    {
        gameObjectList.add(c);
    }
    public void refreshGameObjects()
    {
        gameObjectList.clear();
    }

    @Override
    public String toString()
    {
        /*
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

         */
        return null;
    }

    public int getBitMaskRequirement() {
        return bitMaskRequirement;
    }

    public void setBitMaskRequirement(int bitMaskRequirement) {
        this.bitMaskRequirement = bitMaskRequirement;
    }


    public abstract void update();

    public abstract void update(Event event);


}

//Notes on the game system
/*
-- make this abstract interface ensure all the gamesystems are singletons

-- Change the ArrayList<Class<? extends Component> to a more rigid structure of the classes the systems want so reduce downcasting

-- ensure all GameSystems are added to the systems array in level
 */
