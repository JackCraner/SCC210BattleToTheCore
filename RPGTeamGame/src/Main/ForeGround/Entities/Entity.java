package Main.ForeGround.Entities;


import Main.Game;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import java.nio.file.Paths;


public class Entity extends Sprite {
    int ID;
    int Health;
    int Damage;
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
            if (ID == 2)
            {
                entityTexture.loadFromFile(Paths.get("Assets\\Torch.png"));
            }
        }
        catch (Exception e)
        {

        }
    }

    public Vector2i inBlock()
    {
        return new Vector2i((int)(this.getPosition().x/ Game.blockSize), (int)(this.getPosition().y/Game.blockSize));
    }

}

