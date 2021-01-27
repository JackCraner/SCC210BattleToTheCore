package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.Component;

public class TextureComponent extends Component
{

    public final TextureTypes texturetype;
    public Byte tileMapLocation = -1;
    public Byte layer =1;
    public String textureString;


    public TextureComponent(TextureTypes texturetype, String textureID)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
    }
    public TextureComponent(TextureTypes texturetype, Byte layer, String textureID)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
        this.layer = layer;
    }
    public TextureComponent(TextureTypes texturetype, String textureID, Byte tileMapLocation)
    {
        this.texturetype = texturetype;
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
    }
    public TextureComponent(TextureTypes texturetype, Byte layer, String textureID,Byte tileMapLocation)
    {
        this.texturetype = texturetype;
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
        this.layer = layer;
    }


    @Override
    public Component clone() {
        return new TextureComponent(texturetype,layer,textureString,tileMapLocation);
    }
}
