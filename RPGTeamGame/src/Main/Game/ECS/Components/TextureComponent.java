package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class TextureComponent extends Component
{

    public final TextureType texturetype;
    public Byte tileMapLocation = -1;
    public Byte layer =1;
    public String textureString;


    public TextureComponent(TextureType texturetype, String textureID)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
    }
    public TextureComponent(TextureType texturetype, Byte layer, String textureID)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
        this.layer = layer;
    }
    public TextureComponent(TextureType texturetype, String textureID, Byte tileMapLocation)
    {
        this.texturetype = texturetype;
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
    }
    public TextureComponent(TextureType texturetype, String textureID, Byte tileMapLocation, Byte layer)
    {
        this.texturetype = texturetype;
        this.textureString= textureID;
        this.tileMapLocation = tileMapLocation;
        this.layer = layer;
    }


    @Override
    public Component clone() {
        return new TextureComponent(texturetype,textureString,tileMapLocation,layer);
    }
}
