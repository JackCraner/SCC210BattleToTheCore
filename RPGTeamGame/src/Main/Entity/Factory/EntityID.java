package Main.Entity.Factory;

import org.jsfml.graphics.Texture;

import java.io.File;
import java.nio.file.Paths;

public class EntityID
{

    public static String[] CHEST = {"Chest", "Chest_1.png"};
    public static  String[] PLAYER = {"Player", "Girl_1.png"};

    public static String[][] ENTITYLIST = {CHEST,PLAYER};

    public static Texture getTexture(String s)
    {
        Texture t = new Texture();
        try
        {
            t.loadFromFile(Paths.get("Assets"+ File.separator + s));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return t;
    }


}
