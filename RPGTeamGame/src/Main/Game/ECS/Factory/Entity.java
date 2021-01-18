package Main.Game.ECS.Factory;

public enum Entity
{
    BLOCK("Block","TileMapBig2.png"),
    PLAYER("Player", "Girl_1.png"),
    TORCH("Torch", "Torch_1.png"),
    CHEST("Chest", "Chest_1.png"),
    SWORD("ITEM", "Katana_1.png"),
    SWORDSWOOSH("Sword Swing", "SwordAttackpng.png"),
    WAND("Staff","Demon_Staff_2.png"),
    FIREBALL("Fire Ball", "FireBall.png"),
    ENEMY("Enemy", "SharkTexture.png"),
    XPORB("XP","XP_1.png"),
    DamageText("Text", "Text");

    public final String name;
    public final String textureString;
    Entity(String name, String textureString)
    {
        this.name = name;
        this.textureString = textureString;
    }

}
