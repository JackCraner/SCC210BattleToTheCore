package Main.MapGen.CellularA;

import java.util.ArrayList;

public class CellularAutomata
{


    //parameters of creation;
    private float initalEmptyFaceRate = 0.54f;    //0.52
    private int recursionDepth = 3;

    private int wallID = 0;
    private int EmptyID = 1;

    int chunkSizeBlocks;
    int chunkSizePixels;

    int[][] chunkBinaryMapping;
    Cave cMap;

    public CellularAutomata(int chunkSizeBlocks, int chunkSizePixels)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        chunkBinaryMapping = new int[chunkSizeBlocks][chunkSizeBlocks];
        cMap = new Cave(chunkSizeBlocks, chunkSizeBlocks);
    }

    public int[][] startMap(int[][] binaryMapping)
    {
        for (int a = 0; a <chunkSizeBlocks; a++)
        {
            for (int b = 0; b<chunkSizeBlocks; b++)
            {
                if ((Math.random()) > initalEmptyFaceRate)
                {
                    binaryMapping[a][b] =wallID;
                }
                else
                {
                    binaryMapping[a][b] = EmptyID;
                }

            }
        }

        return binaryMapping;


    }

    public int checkNeighbours(int[][] givenMap,int x, int y)
    {

        int spaceCount = 0;

        int xStart = Math.max(x-1, 0);
        int xEnd = Math.min(x+1, chunkSizeBlocks-1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,chunkSizeBlocks-1);


        for (int a = xStart; a <= xEnd; a++)
        {
            for (int b = yStart; b<= yEnd; b++)
            {

                if (givenMap[a][b] == EmptyID) spaceCount++;

            }
        }
        return spaceCount;


    }
    public int[][] updateMap(int[][] tempMap, int depth)
    {

        int[][] newMap = tempMap;
        for (int a = 0; a < chunkSizeBlocks; a++)
        {
            for (int b = 0; b< chunkSizeBlocks; b++)
            {
                if (checkNeighbours(tempMap, a, b) >= 5)
                {
                    newMap[a][b] = EmptyID;
                }
                else
                {
                    newMap[a][b] = wallID;
                }

            }
        }
        if (depth == 0)
        {
            return newMap;
        }
        else
        {
            return updateMap(newMap, depth-1);
        }

    }

    public ArrayList<Cave> findCaves(int[][] mapping)
    {
        ArrayList<Cave> foundCaves = new ArrayList<>();
        for (int a = 0; a < chunkSizeBlocks; a++) {
            for (int b = 0; b < chunkSizeBlocks; b++) {
                if (checkNeighbours(mapping, a, b) == 9) {
                    Cave locatedCave = new Cave(chunkSizePixels, chunkSizePixels);
                    foundCaves.add(caveRecursion(mapping,a, b, locatedCave));
                }
            }
        }
        return foundCaves;
    }

    public Cave caveRecursion(int[][] mapping, int x, int y, Cave newCave)
    {

        if (!(x < 0 || x > chunkSizeBlocks -1 || y < 0 || y > chunkSizeBlocks - 1))
        {
            int spaceCount = checkNeighbours(mapping, x, y);
            if (spaceCount >= 1)
            {
                if (mapping[x][y]==EmptyID)
                {
                    mapping[x][y] = -1;
                    newCave.addElement(x,y);
                    caveRecursion(mapping,x+1, y,newCave);
                    caveRecursion(mapping,x-1, y,newCave);
                    caveRecursion(mapping, x, y-1,newCave);
                    caveRecursion(mapping, x, y+1,newCave);
                }
            }
        }

        return newCave;

    }

    public int[][] generateBinaryMapping()
    {
        ArrayList<Cave> chunkCaves = findCaves(updateMap(startMap(chunkBinaryMapping),recursionDepth));
        int caveSize = 0;
        for (Cave c: chunkCaves)
        {
            if (c.caveSize() > caveSize)
            {
                caveSize = c.caveSize();
                cMap = c;

            }
        }
        return cMap.getArray();
    }
    public Cave getcMap()
    {
        return cMap;
    }


}

