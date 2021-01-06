package Main.Game.ECS.Entity;

public abstract class  Component
{

//DESIGN PATTERN
    /*
    - Components Should always extend this abstract class
    - Components Should always be only DataTypes --- NO LOGIC ---
    - Components Should always be given a BitMask within EntityManager(At the Bottom) after the class is created


    Every Component should be as lightweight as possible to improve performance
    -- NEVER USE complex data types such as Texture
    -- AVOID using Long (64 byte)
    -- USE primitive datatyes such as int, String (32 byte)
    -- BEST to use byte (8 byte)
     */



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
