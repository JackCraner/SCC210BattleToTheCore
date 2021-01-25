package Main.Game.MapGeneration;

/**
 * Different Level Values
 *      Nothing to do with seed here
 * Defines different parameters between levels
 * For example, the final level will have less torches, thus making it more dark and scary so the TorchSpawnRate should be low
 */
public enum Map
{
    MAP1(0.1f,200,0.1f,100,(byte)3,(byte)31),
    MAP2(5f,100,0.1f,100,(byte)3,(byte)31);

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
