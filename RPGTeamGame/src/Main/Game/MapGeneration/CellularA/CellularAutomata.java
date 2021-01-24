package Main.Game.MapGeneration.CellularA;



import Main.Game.ECS.Factory.Blueprint;
import org.jsfml.system.Vector2i;

import java.util.*;
//Creates the skeleton of the map using Cellular Automata
//https://en.wikipedia.org/wiki/Cellular_automaton

public class CellularAutomata
{


    //parameters of creation;
    public static int CHUNKSIZEBLOCKSX = 500;       // total number of blocks in the X direction
    public static int CHUNKSIZEBLOCKSY = 500;       // total number of blocks in the Y direction
    public static int CHUNKSIZEPIXELSX = CHUNKSIZEBLOCKSX * (int)Blueprint.BLOCKSIZE.x; //Number of pixels of the total map in the X
    public static int CHUNKSIZEPIXELSY = CHUNKSIZEBLOCKSY * (int)Blueprint.BLOCKSIZE.y; // number of pixels of the total map in the Y
    public static byte WALLID = 0;  //ID of wall blocks
    public static byte EMPTYID = 1; //ID of empty blocks

    private float initalEmptyFaceRate = 0.53f;    //0.54
    private int recursionDepth = 3; //Depth of the cellular Automata

    int totalEmptySpace;        //number number of empty blocks
    byte[][] binaryMapping = new byte[CHUNKSIZEBLOCKSX][CHUNKSIZEBLOCKSY];  //2D array of 1s and 0s to define Wall and Empty block locations
    private static CellularAutomata caInstance = new CellularAutomata();

    public static CellularAutomata getInstance()
    {
        return caInstance;
    }

    /**
     * Generates a random 2D array of 1s and 0s of size CHUNKSIZEBLOCKSX and CHUNKSIZEBLOCKSY
     * @param randomNumberGenerator Random number generater seed (defines the seed of the map)
     * @return  The 2D array
     */
    public byte[][] startMap(Random randomNumberGenerator)
    {

        byte[][] binaryMapping = new byte[CHUNKSIZEBLOCKSX][CHUNKSIZEBLOCKSY];
        for (int a = 0; a <CHUNKSIZEBLOCKSX; a++)
        {
            for (int b = 0; b<CHUNKSIZEBLOCKSY; b++)
            {
                if (randomNumberGenerator.nextFloat() > initalEmptyFaceRate)
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

    /**
     * Returns the number of empty blocks around a given point X,Y
     * @param givenMap  The Binary map to check on
     * @param x The X value of the point in the map
     * @param y The Y value of the point in the map
     * @return The number of empty blocks around the point (0 - 8)
     */
    public static int checkNeighbours8(byte[][] givenMap, int x, int y)
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
    public static int checkNeighbours4(byte[][] givenMap, int x,int y)
    {
        int spaceCount = 0;

        int xStart = Math.max(x-1, 0);
        int xEnd = Math.min(x+1,CHUNKSIZEBLOCKSX -1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,CHUNKSIZEBLOCKSY -1);

        for (int a = xStart; a <= xEnd ;a++)
        {
            if(givenMap[a][y] == EMPTYID) spaceCount++;
        }
        for (int b = yStart; b<= yEnd; b++)
        {

            if (givenMap[x][b] == EMPTYID) spaceCount++;

        }
        return spaceCount;

    }


    /**
     * Recursively iterates over the map, performing Cellular Automata
     * @param tempMap  The map it iterates over
     * @param depth The recursion depth
     * @return  The final map output after the Cellular Automata is applied
     */
    public byte[][] updateMap(byte[][] tempMap, int depth)
    {


        for (int a = 0; a < CHUNKSIZEBLOCKSX; a++)
        {
            for (int b = 0; b< CHUNKSIZEBLOCKSY; b++)
            {
                if (checkNeighbours8(tempMap, a, b) >= 5)
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

    /**
     * Generates a Binary map and sets it to binaryMapping
     * -- Checks to ensure the mapping has atleast enough percentage of empty space or the map is regenerated recursively
     */
    public void generateBinaryMapping()
    {
        Random r = new Random();
        binaryMapping = floorFillController(updateMap(startMap(r), recursionDepth));
        if (totalEmptySpace < CHUNKSIZEBLOCKSX * CHUNKSIZEBLOCKSY * initalEmptyFaceRate)
        {
            System.out.println("Map failed, trying again");
            generateBinaryMapping();
        }

    }

    /**
     * Generates a binary cellular automata 2D array
     * @param randomNumberGenerator map seed
     * @return 2D array of bytes
     */
    public byte[][] generateBinaryMapping(Random randomNumberGenerator)
    {

        binaryMapping = floorFillController(updateMap(startMap(randomNumberGenerator), recursionDepth));
        if (totalEmptySpace < CHUNKSIZEBLOCKSX * CHUNKSIZEBLOCKSY * initalEmptyFaceRate)
        {
            generateBinaryMapping();
        }
        return binaryMapping;

    }


    /**
     * A flood fill algorithm is used to ensure the whole cave system is connected and reachable
     * -- It will remove all small disconnected cave parts and only return the main core cave
     * @param mappingCopy Copy of the 2D cellular automata array
     * @return  a new 2D array of bytes with only one cave
     */
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

    /**
     * The flood fill is Queue-Based instead of Recursion based because on large maps, StackOverFlows can occur on the recursive call stack
     * @param x The X position of the location being checked
     * @param y The Y position of the location being checked
     * @param fillID Each distinct cave is given a unique FillID
     * @param mapping   The binary cellular automata mapping being checked
     * @return The Size of the current FillID cave
     */
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

    /**
     * gets the current Cellular Automata Binary Mapping
     * @return The 2D array of bytes
     */
    public byte[][] getBinaryMapping() {
        return binaryMapping;
    }
}
