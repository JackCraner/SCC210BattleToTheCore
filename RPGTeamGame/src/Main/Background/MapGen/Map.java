package Main.Background.MapGen;

import Main.Background.MapGen.Chunks.Chunk;
import Main.Background.MapGen.Chunks.FormationChunk;
import Main.Background.MapGen.MapCreation.CellularA.CellularAutomata;
import Main.Background.MapGen.MapCreation.CreationOutput;
import Main.Background.MapGen.MapCreation.MapStylization.CreationAddTorches;
import Main.Background.MapGen.MapCreation.MapStylization.CreationOfChests;
import Main.ForeGround.Entities.Chest;
import Main.ForeGround.Entities.Torch;
import Main.ForeGround.Interfaces.Illuminator;
import Main.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

public class Map
{

    FormationChunk[][] chunkArray;
    Chunk mapChunk;
    CellularAutomata cA = new CellularAutomata();
    CreationOutput cO = new CreationOutput();
    int mapSizeBlocksX, mapSizeBlocksY;
    ArrayList<Torch> mapTorchList = new ArrayList<>();
    ArrayList<Chest> mapChestList = new ArrayList<>();
    public Map()
    {

        chunkArray = new FormationChunk[Game.numberOfChunksX][Game.numberOfChunksY];
        mapSizeBlocksX = Game.numberOfChunksX * Game.chunkSizeBlocks;
        mapSizeBlocksY = Game.numberOfChunksY * Game.chunkSizeBlocks;
        generateMap();
        generateTunnels();
        generateDeco();
        combineMap();
        addTorches();   //add torches
        addChests();    //add chests
        //add saves shrines

    }

    public void generateMap()
    {
        for (int a = 0; a< Game.numberOfChunksX; a++)
        {
            for (int b = 0; b< Game.numberOfChunksY;b++)
            {
                chunkArray[a][b] = new FormationChunk(cA.generateBinaryMapping(), new Vector2f(a,b));

            }
        }
    }
    public void generateTunnels()
    {
        for (int a = 0; a< Game.numberOfChunksX; a++)
        {
            for (int b = 0; b< Game.numberOfChunksY;b++)
            {
                try {
                    generateTunnelsBetweenChunks(chunkArray[a][b], chunkArray[a + 1][b]);
                }
                catch (Exception e)
                {

                }
                try {
                    generateTunnelsBetweenChunks(chunkArray[a][b],chunkArray[a][b+1]);
                }
                catch (Exception e)
                {

                }




            }
        }
    }
    public void generateTunnelsBetweenChunks(FormationChunk c1, FormationChunk c2)
    {
        int tunnelWidth = 5;
        Vector2f dif = new Vector2f(c2.getPosition().x - c1.getPosition().x, c2.getPosition().y - c1.getPosition().y);
        Vector2f point1 = c1.genRandomPoint(0,Game.chunkSizeBlocks-tunnelWidth-1);
        Vector2f point2 = c2.genRandomPoint(0,Game.chunkSizeBlocks-tunnelWidth-1);
        float split = 0;
        if (dif.x == 1)
        {
            split = (Game.chunkSizeBlocks - point1.x) / ((Game.chunkSizeBlocks - point1.x) + point2.x);
            c1.drawLine(tunnelWidth,point1, new Vector2f(Game.chunkSizeBlocks, point1.y + ((point2.y - point1.y) * split)));
            c2.drawLine(tunnelWidth,point2, new Vector2f(0, point1.y + ((point2.y - point1.y) * split)));

        }
        else if (dif.y == 1)
        {
            split = (Game.chunkSizeBlocks - point1.y) / ((Game.chunkSizeBlocks - point1.y) + point2.y);
            c1.drawLine(tunnelWidth,point1, new Vector2f(point1.x + ((point2.x - point1.x) * split), Game.chunkSizeBlocks));
            c2.drawLine(tunnelWidth,point2, new Vector2f(point1.x + ((point2.x - point1.x) * split), 0));

        }

    }
    public void generateDeco()
    {
        chunkArray = cO.generateOutput(chunkArray);
    }

    public void combineMap()
    {
        Block[][] mapArray = new Block[mapSizeBlocksX][mapSizeBlocksY];
        for (int a = 0; a<Game.numberOfChunksX;a++)
        {
            for (int b = 0; b<Game.numberOfChunksY;b++)
            {
                for (int a1 = 0; a1<Game.chunkSizeBlocks;a1++)
                {
                    for (int b1 =0; b1<Game.chunkSizeBlocks;b1++)
                    {
                        mapArray[a1 + (a*Game.chunkSizeBlocks)][b1 + (b*Game.chunkSizeBlocks)] = new Block(chunkArray[a][b].getBlockAt(new Vector2i(a1,b1)).getID(),chunkArray[a][b].getBlockAt(new Vector2i(a1,b1)).getTextureID(),a*Game.chunkSizePixels + a1*Game.blockSize, b1*Game.blockSize + b*Game.chunkSizePixels);

                    }
                }
            }
        }
        mapChunk = new Chunk(mapArray);

    }

    public Block[][] getBlockMapArray(Vector2f position, int size)
    {
        position = new Vector2f(position.x / Game.blockSize, position.y / Game.blockSize);
        Block[][] bMapping = new Block[size+2][size+2];
        int stepping = (int)(size/2);
        int countA = 0, countB = 0;
        for(int a = (int)position.x - stepping ; a < position.x + stepping; a++)
        {
            countB = 0;
            for(int b = (int)position.y - stepping ; b < position.y + stepping; b++)
            {
                try
                {
                    bMapping[countA][countB] = mapChunk.getBlockAt(a,b);
                }
                catch (Exception e)
                {
                    bMapping[countA][countB] = new Block(1,0,0);
                }
                countB++;
            }
            countA ++;
        }
        return bMapping;
    }

    public void addTorches()
    {
        CreationAddTorches cAT = new CreationAddTorches(mapChunk);
        mapTorchList = cAT.getTorchList();

    }
    public void addChests()
    {
        CreationOfChests cOC = new CreationOfChests(mapChunk);
        mapChestList = cOC.getChestList();

    }
    public ArrayList<Torch> getMapTorchList() {
        return mapTorchList;
    }

    public ArrayList<Chest> getMapChestList() {
        return mapChestList;
    }

    public Block getBlockAt(int a, int b)
    {
        return mapChunk.getBlockAt(new Vector2i(a,b));
    }

    public Chunk getMapChunk() {
        return mapChunk;
    }
}
