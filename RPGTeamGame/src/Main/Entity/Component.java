package Main.Entity;

import Main.Entity.GameObject;
import Main.Main;

public abstract class  Component
{

    private GameObject gameObject = null;


    public final void setGameObject(GameObject g)
    {
        gameObject = g;
    }
    public GameObject getGameObject()
    {
        return gameObject;
    }

    public void update(float dt)
    {

    }
    public void start()
    {

    }

}
