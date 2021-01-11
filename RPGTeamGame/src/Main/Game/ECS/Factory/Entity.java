package Main.Game.ECS.Factory;

public enum Entity
{
    BLOCK("Block","TileMapBig2.png"),
    PLAYER("Player", "Girl_1.png"),
    TORCH("Torch", "Torch.png"),
    CHEST("Chest", "Chest_1.png"),
    SWORD("ITEM", "Katana_1.png"),
    SWORDSWOOSH("Sword Swing", "SwordSwoosh.png"),
    WAND("Staff","Demon_Staff_1.png"),
    FIREBALL("Fire Ball", "FireBall.png"),
    ENEMY("Enemy", "SharkTexture.png");

    public final String name;
    public final String textureString;
    Entity(String name, String textureString)
    {
        this.name = name;
        this.textureString = textureString;
    }

}
