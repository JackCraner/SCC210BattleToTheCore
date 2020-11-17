package Main;


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

    int renderRange = 3;

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
        generateBlockArray();
    }

    public void generateBlockArray()
    {
        blockArray.clear();
        int renderLoop = ((renderRange - 1)/2);
        for (int i = -renderLoop; i<= renderLoop; i++)
        {
            for (int j =- renderLoop; j<= renderLoop; j++)
            {
                try
                {
                    int[][] blockArrayInt = mapPlane.getChunkAtPosition(new Vector2f(playerObject.inChunk(chunkSizePixels).x + i, playerObject.inChunk(chunkSizePixels).y + j)).getChunkMapping();
                    for (int a = 0; a < chunkSizeBlocks; a++)
                    {
                        for (int b = 0; b< chunkSizeBlocks;b++)
                        {

                            blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i),(b*blockSizePixels) + (chunkSizePixels*j)),new Vector2f(16*blockArrayInt[a][b],0)));
                            blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i),(b*blockSizePixels) + (chunkSizePixels*j) +  blockSizePixels),new Vector2f(16*blockArrayInt[a][b] + 16,0)));
                            blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i) + blockSizePixels,(b*blockSizePixels) + (chunkSizePixels*j) + blockSizePixels),new Vector2f(16*blockArrayInt[a][b] + 16,16)));
                            blockArray.add(new Vertex( new Vector2f((a*blockSizePixels) + (chunkSizePixels * i) + blockSizePixels,(b*blockSizePixels) + (chunkSizePixels*j)),new Vector2f(16*blockArrayInt[a][b],16)));


                        }
                    }

                }
                catch (Exception e)
                {
                    //System.out.println("Chunk " + i + ", " + j);
                }

            }
        }
        /*
        int[][] blockArrayInt = mapPlane.getChunkAtPosition(playerObject.inChunk(chunkSizePixels)).getChunkMapping();
        for (int a = 0; a < chunkSizeBlocks; a++)
        {
            for (int b = 0; b< chunkSizeBlocks;b++)
            {

                blockArray.add(new Vertex( new Vector2f(a*blockSizePixels,b*blockSizePixels),new Vector2f(16*blockArrayInt[a][b],0)));
                blockArray.add(new Vertex( new Vector2f(a*blockSizePixels,b*blockSizePixels + blockSizePixels),new Vector2f(16*blockArrayInt[a][b] + 16,0)));
                blockArray.add(new Vertex( new Vector2f(a*blockSizePixels + blockSizePixels,b*blockSizePixels + blockSizePixels),new Vector2f(16*blockArrayInt[a][b] + 16,16)));
                blockArray.add(new Vertex( new Vector2f(a*blockSizePixels + blockSizePixels,b*blockSizePixels),new Vector2f(16*blockArrayInt[a][b],16)));


            }
        }

         */


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
