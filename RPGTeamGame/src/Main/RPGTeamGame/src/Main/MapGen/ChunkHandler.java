package Main.MapGen;


import org.jsfml.system.Vector2f;

import java.nio.file.Paths;
import java.util.ArrayList;

public class ChunkHandler
{

    ArrayList<Chunk> allChunks = new ArrayList<>();
    Chunk[] visibleChunks;


    Chunk map;
    int chunkSizeBlocks, chunkSizePixels, renderArea, initalGenerationArea;
    public ChunkHandler(int chunkSizeBlocks, int chunkSizePixels, int renderArea, int initalGenerationArea)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        if (renderArea % 2 == 0 || initalGenerationArea % 2 == 0)
        {
            System.out.println("Render/Generation Area MUST BE ODD");
            this.renderArea = renderArea -1;
            this.initalGenerationArea = initalGenerationArea -1;
        }
        else
        {
            this.renderArea = renderArea;
            this.initalGenerationArea = initalGenerationArea;
        }


        visibleChunks = new Chunk[renderArea*renderArea];
        massGeneration(this.initalGenerationArea,new Vector2f(0,0));
        updateVisibleChunks(new Vector2f(0,0));

    }

    public Chunk generateChunk(int chunkSizeBlocks, int chunkSizePixels, Vector2f chunkPosition)
    {
        return new Chunk(chunkSizeBlocks, chunkSizePixels, chunkPosition);
    }

    public int updateVisibleChunks(Vector2f center)
    {

        int counter = 0;
        int renderLoop = ((renderArea - 1) / 2);
        int[] chunkFlag = new int[visibleChunks.length];
        for (int a =-renderLoop; a <= renderLoop; a++) {
            for (int b = -renderLoop; b <= renderLoop; b++) {
                Vector2f loc = new Vector2f(center.x + a, center.y + b);
                boolean chunkExists = false;
                for (Chunk c : allChunks) {


                    if (c.equals(loc)) {
                        chunkExists = true;
                        visibleChunks[counter] = c;

                    }
                }
                if (chunkExists == true) {

                } else {
                    chunkFlag[counter] = 1;
                    visibleChunks[counter] = new Chunk(chunkSizeBlocks, chunkSizePixels, loc);
                    allChunks.add(visibleChunks[counter]);
                }

                counter++;
            }

        }


        for (int i = 0; i< chunkFlag.length;i++)
        {
            if (chunkFlag[i] == 1)
            {
                for (int a = 0; a < visibleChunks.length; a++)
                {
                    visibleChunks[i].getTunnel(visibleChunks[a]);
                }
            }
        }



        return counter;

    }

    public void massGeneration(int generationArea, Vector2f center)
    {

        int generationLoop = ((generationArea - 1) / 2);
        for (int a =-generationLoop; a <= generationLoop; a++)
        {
            for (int b= -generationLoop; b<=generationLoop;b++)
            {
                Vector2f loc = new Vector2f(center.x + a, center.y+ b);
                Chunk tempC = new Chunk(chunkSizeBlocks,chunkSizePixels,loc);

                allChunks.add(tempC);



            }

        }
        for (Chunk c: allChunks)
        {
            for (Chunk c2: allChunks)
            {
               c.getTunnel(c2);
            }
        }
    }



    public Chunk[] getVisibleChunks()
    {
        return visibleChunks;
    }
}







