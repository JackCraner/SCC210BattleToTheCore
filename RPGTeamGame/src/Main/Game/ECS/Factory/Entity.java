package Main.Game.ECS.Factory;

public enum Entity
{
    BLOCK("Block","TileMapBig2.png"),
    PLAYER("Player", "Girl_1.png"),
    TORCH("Torch", "Torch.png"),
    CHEST("Chest", "Chest_1.png");


    public final String name;
    public final String textureString;
    Entity(String name, String textureString)
    {
        this.name = name;
        this.textureString = textureString;
    }

}
