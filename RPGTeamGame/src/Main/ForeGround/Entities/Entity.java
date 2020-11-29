package Main.ForeGround.Entities;


import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;


public class Entity extends Sprite {
    int ID;
    Texture entityTexture = new Texture();
    public Entity(int ID)
    {
        super();
        this.ID = ID;
        findEntityTexture();
    }

    public int getID() {
        return ID;
    }
    public Texture getEntityTexture()
    {
        return entityTexture;
    }
    public void findEntityTexture()
    {
        try
        {
            if (ID == 1)
            {
                entityTexture.loadFromFile(Paths.get("Assets\\Submarine.png"));
            }
        }
        catch (Exception e)
        {

        }
    }

}

