package Main.Game.MapGeneration.CellularA;



import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Factory.Blueprint;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.nio.channels.ScatteringByteChannel;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

public class CellularAutomata
{


    //parameters of creation;
    public static int CHUNKSIZEBLOCKSX = 1000;
    public static int CHUNKSIZEBLOCKSY = 1000;
    public static int CHUNKSIZEPIXELSX = CHUNKSIZEBLOCKSX * (int)Blueprint.BLOCKSIZE.x;
    public static int CHUNKSIZEPIXELSY = CHUNKSIZEBLOCKSY * (int)Blueprint.BLOCKSIZE.y;
    public static byte WALLID = 0;
    public static byte EMPTYID = 1;

    private float initalEmptyFaceRate = 0.53f;    //0.54
    private int recursionDepth = 3;

    


    int totalEmptySpace;
    byte[][] binaryMapping = new byte[CHUNKSIZEBLOCKSX][CHUNKSIZEBLOCKSY];
    private static CellularAutomata caInstance = new CellularAutomata();

    public static CellularAutomata getInstance()
    {
        return caInstance;
    }


    public byte[][] startMap()
    {
        byte[][] binaryMapping = new byte[CHUNKSIZEBLOCKSX][CHUNKSIZEBLOCKSY];
        for (int a = 0; a <CHUNKSIZEBLOCKSX; a++)
        {
            for (int b = 0; b<CHUNKSIZEBLOCKSY; b++)
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
        int xEnd = Math.min(x+1,CHUNKSIZEBLOCKSX -1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,CHUNKSIZEBLOCKSY -1);


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


        for (int a = 0; a < CHUNKSIZEBLOCKSX; a++)
        {
            for (int b = 0; b< CHUNKSIZEBLOCKSY; b++)
            {
                if (checkNeighbours(tempMap, a, b) >= 5)
                {
                    tempMap[a][b] = EMPTYID;
                }
                else
                {
                    tempMap[a][b] = WALLID;
                }

            }
        }
        if (depth == 0)
        {
            return tempMap;
        }
        else
        {
            return updateMap(tempMap, depth-1);
        }

    }

    public void generateBinaryMapping()
    {
        binaryMapping = floorFillController(updateMap(startMap(), recursionDepth));
        if (totalEmptySpace < CHUNKSIZEBLOCKSX * CHUNKSIZEBLOCKSY * initalEmptyFaceRate)
        {
            generateBinaryMapping();
        }

    }



    public byte[][] floorFillController(byte[][] mappingCopy)
    {

        ArrayList<Integer> caveSizes = new ArrayList<>();
        for(int a = 0; a <CHUNKSIZEBLOCKSX;a++)
        {
            for(int b =0; b< CHUNKSIZEBLOCKSY; b++)
            {
                Integer caveS = floodFll(a,b, (byte)(caveSizes.size() + 2),mappingCopy);
                if (caveS > 0)
                {
                    caveSizes.add(caveS);
                }
            }
        }
        totalEmptySpace = Collections.max(caveSizes);
        Integer caveIndex = caveSizes.indexOf(totalEmptySpace) + 2;

        for (int a = 0; a<CHUNKSIZEBLOCKSX;a++)
        {
            for(int b = 0; b<CHUNKSIZEBLOCKSY; b++)
            {
                if (mappingCopy[a][b] == caveIndex)
                {
                    mappingCopy[a][b] = EMPTYID;
                }
                else
                {
                    mappingCopy[a][b] = WALLID;
                }
            }
        }
        return mappingCopy;

    }
    public int floodFll(int x, int y, byte fillID, byte[][] mapping)
    {

        Queue<Vector2i> nodeQueue = new ArrayDeque<>();
        int size= 0;
        if (mapping[x][y] == EMPTYID)
        {
            nodeQueue.add(new Vector2i(x,y));
        }

        while(!nodeQueue.isEmpty())
        {
            Vector2i currentNode = nodeQueue.poll();
            try
            {

                if (mapping[currentNode.x][currentNode.y] == EMPTYID)
                {


                    size ++;
                    mapping[currentNode.x][currentNode.y] = fillID;
                    nodeQueue.add(new Vector2i(currentNode.x + 1, currentNode.y));
                    nodeQueue.add(new Vector2i(currentNode.x - 1, currentNode.y));
                    nodeQueue.add(new Vector2i(currentNode.x, currentNode.y + 1));
                    nodeQueue.add(new Vector2i(currentNode.x, currentNode.y - 1));
                }
            }
            catch (Exception e)
            {

            }


        }

        return size;

    }

    public byte[][] getBinaryMapping() {
        return binaryMapping;
    }
}
