package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class TextureComponent extends Component
{


    public Byte tileMapLocation = -1;
    public Byte layer =1;
    public Byte textureID;

    public TextureComponent()
    {

    }
    public TextureComponent(byte textureID)
    {
        this.textureID = textureID;
    }

    public TextureComponent(byte textureID, Byte tileMapLocation)
    {
        this.textureID = textureID;
        this.tileMapLocation = tileMapLocation;
    }


}
