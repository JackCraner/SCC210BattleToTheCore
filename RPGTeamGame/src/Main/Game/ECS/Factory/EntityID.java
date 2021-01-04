package Main.Game.ECS.Factory;

import org.jsfml.graphics.Texture;

import java.io.File;
import java.nio.file.Paths;

public class EntityID
{


    public static  Entity BLOCK = new Entity("Block", "TileMapBig2.png", (byte) 0);
    public static Entity PLAYER = new Entity("Player", "Girl_1.png", (byte) 1);
    public static Entity CHEST = new Entity("Chest", "Chest_1.png",((byte) 2));
    public static Entity TORCH = new Entity("Torch", "Torch.png",((byte) 3));
    public static String[] ENTITYTextureStringLIST = {BLOCK.textureString,PLAYER.textureString,CHEST.textureString};

    public static Texture getTexture(String s)
    {
        Texture t = new Texture();
        try
        {
            t.loadFromFile(Paths.get("Assets"+ File.separator +  "EntityTextures" + File.separator + s));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return t;
    }


}

class Entity
{
    public String name;
    public String textureString;
    public Byte textureID;

    public Entity(String name, String textureString, Byte textureID)
    {
        this.name = name;
        this.textureString = textureString;
        this.textureID = textureID;
    }
}
