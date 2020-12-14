package Main.Background;

import Main.Background.MapGen.ChunkLoader;
import Main.Background.MapGen.Map;
import Main.ForeGround.Entities.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;


public class Background implements Drawable
{
    int chunkSizeBlocks, chunkSizePixels;
    int numberChunkX;
    int numberChunkY;

    Map mapObject;
    ChunkLoader cH;


    Vector2f currentPlayerChunk;
    public Background(int chunkSizeBlocks, int chunkSizePixels, int numberChunkX, int numberChunkY)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        this.numberChunkX = numberChunkX;
        this.numberChunkY = numberChunkY;
        mapObject = new Map(chunkSizeBlocks,chunkSizePixels,numberChunkX,numberChunkY);
        cH = new ChunkLoader(chunkSizeBlocks,chunkSizePixels,mapObject);

    }
    public void initialiseBackGround(Player p)
    {
        currentPlayerChunk = p.inChunk(chunkSizePixels);
        cH.generateMetaData(p);
        cH.generateBlockArray(p);

    }
    public void updateBackGroundOnMove(Player p)
    {



        if (currentPlayerChunk.x != p.inChunk(chunkSizePixels).x || currentPlayerChunk.y != p.inChunk(chunkSizePixels).y)
        {
            currentPlayerChunk = p.inChunk(chunkSizePixels);
            cH.generateMetaData(p);

        }
        cH.generateBlockArray(p);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {

        cH.draw(renderTarget,cH.getTransform(renderStates));

    }

    public Map getMapObject() {
        return mapObject;
    }
}
