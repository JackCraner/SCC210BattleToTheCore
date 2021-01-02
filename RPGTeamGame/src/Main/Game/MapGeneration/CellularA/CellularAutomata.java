package Main.Game.MapGeneration.CellularA;



import java.util.ArrayList;

public class CellularAutomata
{


    //parameters of creation;
    public static int CHUNKSIZEBLOCKS = 100;
    public static int CHUNKSIZEPIXELS = 3200;
    public static byte WALLID = 0;
    public static byte EMPTYID = 1;

    private float initalEmptyFaceRate = 0.54f;    //0.52
    private int recursionDepth = 3;

    

    byte[][] chunkBinaryMapping =  new byte[CHUNKSIZEBLOCKS][CHUNKSIZEBLOCKS];
    Cave cMap = new Cave(CHUNKSIZEBLOCKS, CHUNKSIZEBLOCKS);

    private static CellularAutomata caInstance = new CellularAutomata();

    public static CellularAutomata getInstance()
    {
        return caInstance;
    }


    public byte[][] startMap(byte[][] binaryMapping)
    {
        for (int a = 0; a <CHUNKSIZEBLOCKS; a++)
        {
            for (int b = 0; b<CHUNKSIZEBLOCKS; b++)
            {
                if ((Math.random()) > initalEmptyFaceRate)
                {
                    binaryMapping[a][b] =WALLID;
                }
                else
                {
                    binaryMapping[a][b] = EMPTYID;
                }

            }
        }

        return binaryMapping;


    }

    public int checkNeighbours(byte[][] givenMap,int x, int y)
    {

        int spaceCount = 0;

        int xStart = Math.max(x-1, 0);
        int xEnd = Math.min(x+1,CHUNKSIZEBLOCKS -1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,CHUNKSIZEBLOCKS -1);


        for (int a = xStart; a <= xEnd; a++)
        {
            for (int b = yStart; b<= yEnd; b++)
            {

                if (givenMap[a][b] == EMPTYID) spaceCount++;

            }
        }
        return spaceCount;


    }
    public byte[][] updateMap(byte[][] tempMap, int depth)
    {

        byte[][] newMap = tempMap;
        for (byte a = 0; a < CHUNKSIZEBLOCKS; a++)
        {
            for (byte b = 0; b< CHUNKSIZEBLOCKS; b++)
            {
                if (checkNeighbours(tempMap, a, b) >= 5)
                {
                    newMap[a][b] = EMPTYID;
                }
                else
                {
                    newMap[a][b] = WALLID;
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

    public ArrayList<Cave> findCaves(byte[][] mapping)
    {
        ArrayList<Cave> foundCaves = new ArrayList<>();
        for (int a = 0; a < CHUNKSIZEBLOCKS; a++) {
            for (int b = 0; b <CHUNKSIZEBLOCKS; b++) {
                if (checkNeighbours(mapping, a, b) == 9) {
                    Cave locatedCave = new Cave(CHUNKSIZEBLOCKS, CHUNKSIZEPIXELS);
                    foundCaves.add(caveRecursion(mapping,a, b, locatedCave));
                }
            }
        }
        return foundCaves;
    }

    public Cave caveRecursion(byte[][] mapping, int x, int y, Cave newCave)
    {

        if (!(x < 0 || x > CHUNKSIZEBLOCKS -1 || y < 0 || y > CHUNKSIZEBLOCKS - 1))
        {
            int spaceCount = checkNeighbours(mapping, x, y);
            if (spaceCount >= 1)
            {
                if (mapping[x][y]==EMPTYID)
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

    public byte[][] generateBinaryMapping()
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


}

class Cave
{
    byte[][] caveArray;
    int caveSizeInt;
    public Cave(int chuckSize, int chuckPixels)
    {
        caveArray = new byte[chuckSize][chuckSize];
    }


    public void addElement(int x, int y)
    {
        caveArray[x][y] = 1;
        caveSizeInt ++;
    }

    public byte[][] getArray()
    {
        return caveArray;
    }
    public int caveSize()
    {
        return caveSizeInt;
    }

    public void showCaves()
    {
        System.out.println(caveSize());
    }
}
