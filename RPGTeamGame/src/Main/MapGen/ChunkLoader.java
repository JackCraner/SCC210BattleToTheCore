package Main.MapGen;


import Main.Main;
import Main.Sprites.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class ChunkLoader implements Drawable
{
    VertexArray blockArray;
    int chunkSizeBlocks;
    int chunkSizePixels;
    int blockSizePixels;

    Texture blockTexture = new Texture();
    Map mapPlane;
    Player playerObject;

    int viewRange;
    int [][][][] blockArrayChunkInt = new int[3][3][][];

    public ChunkLoader(int chunkSizeBlocks, int chunkSizePixels, Map mapPlane, Player playerObject)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        this.mapPlane= mapPlane;
        this.playerObject = playerObject;
        try
        {
            blockTexture.loadFromFile(Paths.get("Assets\\Tilemap.png"));
        }
        catch(Exception E){}

        blockArray = new VertexArray(PrimitiveType.QUADS);
        blockSizePixels = (int)(chunkSizePixels/chunkSizeBlocks);
        viewRange = (chunkSizeBlocks/2) + 10;
        generateMetaData();
        generateBlockArray();
    }
    public void generateMetaData()
    {


        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b< 3; b++)
            {
                try{
                    blockArrayChunkInt[b][a] = mapPlane.getChunkAtPosition(new Vector2f(playerObject.inChunk(chunkSizePixels).x + (b-1), playerObject.inChunk(chunkSizePixels).y + (a-1))).getChunkMapping();
                }
                catch (Exception e)
                {
                    blockArrayChunkInt[b][a] = null;
                }


            }
        }
    }
    public void generateBlockArray()
    {

        blockArray.clear();
        int i = 0, j = 0;

        try
        {

           // System.out.println("Blocks: " + playerObject.inBlock(chunkSizePixels,blockSizePixels) + " Chunk: " + playerObject.inChunk(chunkSizePixels));
            for (int a = (int)playerObject.inBlock(chunkSizePixels,blockSizePixels).x - viewRange; a <(int)playerObject.inBlock(chunkSizePixels,blockSizePixels).x + viewRange; a++)
            {
                for (int b = (int)playerObject.inBlock(chunkSizePixels,blockSizePixels).y - viewRange; b < (int)playerObject.inBlock(chunkSizePixels,blockSizePixels).y + viewRange; b++)
                {
                    try
                    {
                        int blockID = whichChunk(blockArrayChunkInt, a,b);
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

    private int whichChunk(int[][][][] cbArray, int x, int y)
    {
        Vector2f c = new Vector2f(1,1);
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
        return cbArray[(int)(c.x + xChange)][(int)(c.y + yChange)][xpos][ypos];

    }
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderStates = new RenderStates(renderStates,blockTexture);
        Transform t = new Transform(1,0,chunkSizePixels * playerObject.inChunk(chunkSizePixels).x,0,1,chunkSizePixels * playerObject.inChunk(chunkSizePixels).y,0,0,1);
        renderStates = new RenderStates(renderStates,t);
        renderStates = new RenderStates(renderStates,blockTexture);
        renderTarget.draw(blockArray,renderStates);
    }
}
