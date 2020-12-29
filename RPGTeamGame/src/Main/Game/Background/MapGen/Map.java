package Main.Game.Background.MapGen;

import Main.Game.Background.MapGen.Chunks.Chunk;
import Main.Game.Background.MapGen.Chunks.FormationChunk;
import Main.Game.Background.MapGen.MapCreation.CellularA.CellularAutomata;
import Main.Game.Background.MapGen.MapCreation.MapStylization.CreationAddBlockTextureID;
import Main.Game.Background.MapGen.MapCreation.MapStylization.CreationFixtures;
import Main.Game.ForeGround.Entities.Entity;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

public class Map
{

    FormationChunk[][] chunkArray;
    Chunk mapChunk;
    CellularAutomata cA = new CellularAutomata();
    int mapSizeBlocksX, mapSizeBlocksY;
    ArrayList<Entity> mapEntityList = new ArrayList<>();


    Vector2f playerStart;
    public Map()
    {

        chunkArray = new FormationChunk[Game.numberOfChunksX][Game.numberOfChunksY];
        mapSizeBlocksX = Game.numberOfChunksX * Game.CHUNKSIZEBLOCKS;
        mapSizeBlocksY = Game.numberOfChunksY * Game.CHUNKSIZEBLOCKS;
        generateMap();
        playerStart = generatePlayerStart();
        generateTunnels();
        generateBossRoom();
        generateDeco();
        combineMap();
        addFixtures();

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
    public Vector2f generatePlayerStart()
    {
        for (int a = 5; a< Game.CHUNKSIZEBLOCKS; a++)
        {
            for (int b = 0; b< Game.CHUNKSIZEBLOCKS-1; b++)
            {
                if (chunkArray[0][0].getBlockAt(a,b).getID() == Block.EMPTY && chunkArray[0][0].getBlockAt(a,b+1).getID() == Block.WALL)
                {
                    Vector2f bPos = chunkArray[0][0].getBlockAt(a,b).getPosition();
                    return new Vector2f(bPos.x * Game.blockSize,bPos.y * Game.blockSize);
                }

            }
        }
        return new Vector2f(0,0);
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
                    System.out.println(e);
                }
                try {
                    generateTunnelsBetweenChunks(chunkArray[a][b],chunkArray[a][b+1]);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }




            }
        }
    }
    public void generateTunnelsBetweenChunks(FormationChunk c1, FormationChunk c2)
    {
        int tunnelWidth = 5;
        Vector2f dif = new Vector2f(c2.getPosition().x - c1.getPosition().x, c2.getPosition().y - c1.getPosition().y);
        Vector2f point1 = c1.genRandomPoint(0,Game.CHUNKSIZEBLOCKS -tunnelWidth-1);
        Vector2f point2 = c2.genRandomPoint(0,Game.CHUNKSIZEBLOCKS -tunnelWidth-1);
        float split = 0;
        if (dif.x == 1)
        {
            split = (Game.CHUNKSIZEBLOCKS - point1.x) / ((Game.CHUNKSIZEBLOCKS - point1.x) + point2.x);
            c1.drawLine(tunnelWidth,point1, new Vector2f(Game.CHUNKSIZEBLOCKS, point1.y + ((point2.y - point1.y) * split)));
            c2.drawLine(tunnelWidth,point2, new Vector2f(0, point1.y + ((point2.y - point1.y) * split)));

        }
        else if (dif.y == 1)
        {
            split = (Game.CHUNKSIZEBLOCKS - point1.y) / ((Game.CHUNKSIZEBLOCKS - point1.y) + point2.y);
            c1.drawLine(tunnelWidth,point1, new Vector2f(point1.x + ((point2.x - point1.x) * split), Game.CHUNKSIZEBLOCKS));
            c2.drawLine(tunnelWidth,point2, new Vector2f(point1.x + ((point2.x - point1.x) * split), 0));

        }

    }
    public void generateBossRoom()
    {
        int randomChunk = (int)(Math.random() * 3);
        chunkArray[randomChunk][2].generateBossRoom();
    }

    public void generateDeco()
    {
        CreationAddBlockTextureID cAB = new CreationAddBlockTextureID();
        chunkArray = cAB.addBlocks(chunkArray);
    }

    public void combineMap()
    {
        Block[][] mapArray = new Block[mapSizeBlocksX][mapSizeBlocksY];
        for (int a = 0; a<Game.numberOfChunksX;a++)
        {
            for (int b = 0; b<Game.numberOfChunksY;b++)
            {
                for (int a1 = 0; a1<Game.CHUNKSIZEBLOCKS; a1++)
                {
                    for (int b1 = 0; b1<Game.CHUNKSIZEBLOCKS; b1++)
                    {
                        mapArray[a1 + (a*Game.CHUNKSIZEBLOCKS)][b1 + (b*Game.CHUNKSIZEBLOCKS)] = new Block(chunkArray[a][b].getBlockAt(new Vector2i(a1,b1)).getID(),chunkArray[a][b].getBlockAt(new Vector2i(a1,b1)).getTextureID(),a*Game.CHUNKSIZEPIXELS + a1*Game.blockSize, b1*Game.blockSize + b*Game.CHUNKSIZEPIXELS);

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

    public void addFixtures()
    {
        CreationFixtures cC = new CreationFixtures(mapChunk);
        mapEntityList = cC.getEntityList();

    }

    public ArrayList<Entity> getMapEntityList() {
        return mapEntityList;
    }

    public Vector2f getPlayerStart() {
        return playerStart;
    }

    public Block getBlockAt(int a, int b)
    {
        return mapChunk.getBlockAt(new Vector2i(a,b));
    }

    public Chunk getMapChunk() {
        return mapChunk;
    }
}
