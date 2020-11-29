package Main.Background.MapGen;


import Main.ForeGround.Entities.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.nio.file.Paths;

public class ChunkLoader implements Drawable
{
    VertexArray blockArray;
    int chunkSizeBlocks;
    int chunkSizePixels;
    int blockSizePixels;

    Texture blockTexture = new Texture();
    Map mapPlane;

    int viewRange;
    //Block[][][][] blockArrayChunkInt = new Block[3][3][][];
    Chunk[][] chunkArray = new Chunk[3][3];
    public ChunkLoader(int chunkSizeBlocks, int chunkSizePixels, Map mapPlane)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        this.mapPlane= mapPlane;
        try
        {
            blockTexture.loadFromFile(Paths.get("Assets\\Tilemap.png"));
        }
        catch(Exception E){}

        blockArray = new VertexArray(PrimitiveType.QUADS);
        blockSizePixels = (int)(chunkSizePixels/chunkSizeBlocks);
        viewRange = (chunkSizeBlocks/2) + 10;

    }
    public void generateMetaData(Player p)
    {


        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b< 3; b++)
            {
                try{

                    chunkArray[b][a] = mapPlane.getChunkAtPosition(new Vector2f(p.inChunk(chunkSizePixels).x + (b-1), p.inChunk(chunkSizePixels).y + (a-1)));
                }
                catch (Exception e)
                {
                    chunkArray[b][a]  = null;
                }


            }
        }
    }
    public void generateBlockArray(Player p)
    {

        blockArray.clear();
        int i = 0, j = 0;

        try
        {

           // System.out.println("Blocks: " + playerObject.inBlock(chunkSizePixels,blockSizePixels) + " Chunk: " + playerObject.inChunk(chunkSizePixels));
            for (int a = (int)p.inBlock(chunkSizePixels,blockSizePixels).x - viewRange; a <(int)p.inBlock(chunkSizePixels,blockSizePixels).x + viewRange; a++)
            {
                for (int b = (int)p.inBlock(chunkSizePixels,blockSizePixels).y - viewRange; b < (int)p.inBlock(chunkSizePixels,blockSizePixels).y + viewRange; b++)
                {
                    try
                    {
                        int blockID = whichChunk(chunkArray, a,b).getID();
                        blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i),(b*blockSizePixels) + (chunkSizePixels*j)),new Vector2f(16*blockID,0)));
                        blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i),(b*blockSizePixels) + (chunkSizePixels*j) +  blockSizePixels),new Vector2f(16*blockID + 16,0)));
                        blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i) + blockSizePixels,(b*blockSizePixels) + (chunkSizePixels*j) + blockSizePixels),new Vector2f(16*blockID + 16,16)));
                        blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i) + blockSizePixels,(b*blockSizePixels) + (chunkSizePixels*j)),new Vector2f(16*blockID,16)));
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

    private Block whichChunk(Chunk[][] cbArray, int x, int y)
    {
        Vector2i c = new Vector2i(1,1);
        int xChange = 0;
        int yChange = 0;
        int xpos = x;
        int ypos = y;

        if (x < 0)
        {
            xChange = -1;
            xpos = chunkSizeBlocks + x;
        }
        else if (x>= chunkSizeBlocks)
        {
            xChange = 1;
            xpos = x- chunkSizeBlocks;
        }

        if (y < 0)
        {
            yChange = -1;
            ypos = chunkSizeBlocks + y;
        }
        else if (y>= chunkSizeBlocks)
        {
            yChange = 1;
            ypos = y- chunkSizeBlocks;
        }
        return cbArray[(c.x + xChange)][(c.y + yChange)].getBlockAtVector(new Vector2i(xpos,ypos));


    }
    public VertexArray getArray()
    {
        return blockArray;
    }
    public RenderStates getTransform(RenderStates renderStates)
    {
        Transform t = new Transform(1,0,chunkSizePixels * chunkArray[1][1].getcPosition().x,0,1,chunkSizePixels * chunkArray[1][1].getcPosition().y,0,0,1);
        renderStates = new RenderStates(renderStates,t);
        renderStates = new RenderStates(renderStates,blockTexture);
        return renderStates;
    }
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {

        renderTarget.draw(blockArray,getTransform(renderStates));
    }
}
