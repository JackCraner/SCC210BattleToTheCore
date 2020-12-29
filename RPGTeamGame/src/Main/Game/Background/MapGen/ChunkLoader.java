package Main.Game.Background.MapGen;


import Main.Game.Background.MapGen.Chunks.Chunk;
import Main.Game.Player.Player;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.File;
import java.nio.file.Paths;

public class ChunkLoader implements Drawable
{
    VertexArray blockArray;



    Texture blockTexture = new Texture();
    Map mapPlane;
    int viewRange;
    Chunk c;
    public ChunkLoader(Map mapPlane)
    {
        this.mapPlane= mapPlane;
        try
        {
            blockTexture.loadFromFile(Paths.get("Assets" + File.separator +"TileMapBig2.png"));

        }
        catch(Exception E){
            System.out.println(E);
        }


        blockArray = new VertexArray(PrimitiveType.QUADS);
        viewRange = (int)Math.ceil((Game.viewSize/Game.blockSize)) +4;

    }

    public void generateBlockArray(Player p)
    {

        blockArray.clear();
        float transformF = (Game.viewSize / Game.blockSize) /2;
        Vector2f transformV = new Vector2f(p.inBlock().x - transformF -2, p.inBlock().y -transformF-2);
        c = new Chunk(mapPlane.getBlockMapArray(p.getPosition(), viewRange), transformV);
        try
        {
           // System.out.println("Blocks: " + playerObject.inBlock(Game.chunkSizePixels,Game.blockSize) + " Chunk: " + playerObject.inChunk(Game.chunkSizePixels));
            for (int a =0; a <viewRange; a++)
            {

                for (int b = 0; b < viewRange; b++)
                {
                    try
                    {
                        int blockID = c.getBlockAt(new Vector2i(a,b)).getTextureID();
                        blockArray.add(new Vertex( new Vector2f((a*Game.blockSize),(b*Game.blockSize)),new Vector2f(32*blockID,0)));
                        blockArray.add(new Vertex( new Vector2f((a*Game.blockSize),(b*Game.blockSize) +  Game.blockSize),new Vector2f(32*blockID + 32,0)));
                        blockArray.add(new Vertex( new Vector2f((a*Game.blockSize)  + Game.blockSize,(b*Game.blockSize) + Game.blockSize),new Vector2f(32*blockID + 32,32)));
                        blockArray.add(new Vertex( new Vector2f((a*Game.blockSize) + Game.blockSize,(b*Game.blockSize) ),new Vector2f(32*blockID,32)));
                    }
                    catch (Exception e)
                    {

                    }





                }
            }
        }
        catch (Exception e)
        {

        }


    }

    public VertexArray getArray()
    {
        return blockArray;
    }
    public Chunk getChunk()
    {
        return c;
    }
    public RenderStates getTransform(RenderStates renderStates)
    {
        try
        {
            Transform t = new Transform(1,0,Game.blockSize * c.getPosition().x,0,1,Game.blockSize * c.getPosition().y,0,0,1);
            renderStates = new RenderStates(renderStates,t);

        }
        catch (Exception e)
        {

        }

        renderStates = new RenderStates(renderStates,blockTexture);
        return renderStates;
    }
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {

        renderTarget.draw(blockArray,getTransform(renderStates));
    }
}
