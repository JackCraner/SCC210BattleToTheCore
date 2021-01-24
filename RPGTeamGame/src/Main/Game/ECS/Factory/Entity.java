package Main.Game.ECS.Factory;

public enum Entity
{
    BLOCK("Block","WallTiles.png"),
    PLAYER("Player", "PlayerSpriteSheet.png"),
    TORCH("Torch", "Torch2.png"),
    CHEST("Chest", "chest2.png"),
    SWORD("ITEM", "Katana_1.png"),
    SWORDSWOOSH("Sword Swing", "SwordAttackpng.png"),
    WAND("Staff","Demon_Staff_2.png"),
    FIREBALL("Fire Ball", "FireBall.png"),
    ENEMY("Enemy", "SharkTexture.png"),
    XPORB("XP","XP_1.png"),
    DamageText("Text", "Text"),
    HELMET("Helmet","ChestPiece_2.png"),
    TRAPDOOR("Door","door.png");

    public final String name;
    public final String textureString;
    Entity(String name, String textureString)
    {
        this.name = name;
        this.textureString = textureString;
    }

}
