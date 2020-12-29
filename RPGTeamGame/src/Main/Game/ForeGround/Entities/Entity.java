package Main.Game.ForeGround.Entities;


import Main.Game.Game;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.File;
import java.nio.file.Paths;

//should have an entityID and ID (one that is unique for each item and one that is unique between items of the same type)
public class Entity extends Sprite {
    int uniqueID, textureID;
    Texture entityTexture = new Texture();
    public Entity(int uniqueID, int textureID)
    {
        super();
        this.uniqueID = uniqueID;
        this.textureID = textureID;
        setEntityTexture();

    }
    public Entity(int textureID)
    {
        super();
        this.uniqueID = 0;
        this.textureID = textureID;
        setEntityTexture();

    }

    public int getUniqueID() {
        return uniqueID;
    }
    public int getTextureID()
    {
        return textureID;
    }
    public void setTextureID(int x)
    {
        textureID = x;
    }
    public Texture getEntityTexture()
    {
        return entityTexture;
    }
    public void setEntityTexture()
    {
        try
        {
            if (textureID == 1)
            {
                entityTexture.loadFromFile(Paths.get("Assets"+ File.separator +"Girl_1.png"));
            }
            if (textureID == 2)
            {
                entityTexture.loadFromFile(Paths.get("Assets"+ File.separator +"Torch.png"));
                this.setScale(2,2);
            }
            if (textureID == 3)
            {
                entityTexture.loadFromFile(Paths.get("Assets"+ File.separator +"Chest_1.png"));

            }
            if (textureID == 4)
            {
                entityTexture.loadFromFile(Paths.get("Assets"+ File.separator +"Save_Shrine.png"));

            }
            this.setTexture(entityTexture);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public Vector2i inBlock()
    {
        return new Vector2i((int)(this.getPosition().x/ Game.blockSize), (int)(this.getPosition().y/Game.blockSize));
    }
    public Vector2f getCenter()
    {
        return new Vector2f(this.getPosition().x + (this.getLocalBounds().width/2), this.getPosition().y + (this.getLocalBounds().height/2));
    }


}

