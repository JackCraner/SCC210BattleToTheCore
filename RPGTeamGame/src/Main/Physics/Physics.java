package Main.Physics;

import Main.Background.MapGen.Block;
import Main.Background.MapGen.Chunk;
import Main.Background.MapGen.Map;
import Main.ForeGround.Entities.MovingEntity;
import Main.Game;
import org.jsfml.system.Vector2f;

public class Physics {
    private static Physics instance = null;

    Cblock [][] cBlockArray;

    public static Physics getInstance(){
        if (instance == null) instance = new Physics();
        return instance;
    }

    private Physics()
    {

    }

    public void setBlockArray (Map inputMap)
    {
        Chunk [][] chunkArray = inputMap.getChunkArray();

        int xCN = chunkArray.length;
        int yCN = chunkArray[0].length;

        int xN = xCN * Game.chunkSizeBlocks;
        int yN = yCN * Game.chunkSizeBlocks;

        cBlockArray = new Cblock [xN][yN];

        for (int i = 0; i < xCN; i ++) {
            for (int j = 0; j < xCN; j++){
                Chunk refChunk = chunkArray[i][j];

                for (int x = 0; x < Game.chunkSizeBlocks; x ++){
                    for (int y = 0; y < Game.chunkSizeBlocks; y ++){
                        Block refBlock = refChunk.getBlockAtVector(new Vector2f(x,y));
                        cBlockArray[i * Game.chunkSizeBlocks + x][j * Game.chunkSizeBlocks + y]
                                = new Cblock(new Vector2f(i * Game.chunkSizeBlocks + x, j * Game.chunkSizeBlocks + y), refBlock.getID() == 0);
                    }
                }
            }
        }
    }

    public boolean checkEntityOnGround(MovingEntity entity) {
        Vector2f position = new Vector2f(entity.getPosition().x / Game.blockSize, entity.getPosition().y / Game.blockSize);
        Vector2f underPosition = new Vector2f((int)position.x, (int)position.y + 1);

        if (position.y + 1 == cBlockArray[0].length)
        {
            return true;
        }

        if (cBlockArray[(int) underPosition.x][(int) underPosition.y].getisCollidable() &&
        new CEntity(position, 1,1).getcBox().checkCollistion(
                cBlockArray[(int) underPosition.x][(int) underPosition.y].getcBox())) return true;
        return false;
    }
}
