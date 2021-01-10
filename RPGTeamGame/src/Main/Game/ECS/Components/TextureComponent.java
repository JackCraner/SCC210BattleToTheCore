package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class TextureComponent extends Component
{


    public Byte tileMapLocation = -1;
    public Byte layer =1;
    public String textureString;

    public TextureComponent()
    {

    }
    public TextureComponent(String textureID)
    {
        this.textureString = textureID;
    }
    public TextureComponent(Byte layer, String textureID)
    {
        this.textureString = textureID;
        this.layer = layer;
    }
    public TextureComponent(String textureID, Byte tileMapLocation)
    {
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
    }
    public TextureComponent(String textureID, Byte tileMapLocation, Byte layer)
    {
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
        this.layer = layer;
    }


    @Override
    public Component clone() {
        return new TextureComponent(textureString,tileMapLocation,layer);
    }
}
