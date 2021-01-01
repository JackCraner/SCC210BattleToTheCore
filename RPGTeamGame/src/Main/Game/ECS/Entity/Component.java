package Main.Game.ECS.Entity;

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


}
