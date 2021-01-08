package Main.Game.MapGeneration;

public enum Map
{
    MAP1(0.1f,200,0.1f,100,(byte)25,(byte)31),
    MAP2(0.5f,200,0.1f,100,(byte)22,(byte)31);

    public final float torchSpawnRate;
    public final float torchMinDistance;
    public final float chestSpawnRate;
    public final float chestMinDistance;
    public final byte wallTextureID;
    public final byte emptyTextureID;
    Map(float torchSpawnRate, float torchMinDistance, float chestSpawnRate, float chestMinDistance, byte wallTextureID, byte emptyTextureID)
    {
        this.torchSpawnRate = torchSpawnRate;
        this.torchMinDistance = torchMinDistance;
        this.chestSpawnRate = chestSpawnRate;
        this.chestMinDistance = chestMinDistance;
        this.wallTextureID = wallTextureID;
        this.emptyTextureID = emptyTextureID;
    }

}
