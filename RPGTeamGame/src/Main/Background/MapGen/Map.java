package Main.Background.MapGen;

import Main.Background.MapGen.CellularA.CellularAutomata;
import org.jsfml.system.Vector2f;

public class Map
{
    int chunkX;
    int chunkY;
    int chunkBlockSize;
    Chunk[][] chunkArray;
    CellularAutomata cA;
    public Map(int chunkSizeBlocks, int chunkSizePixels,int chunkX, int chunkY)
    {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkBlockSize = chunkSizeBlocks;
        chunkArray = new Chunk[chunkX][chunkY];
        cA = new CellularAutomata(chunkSizeBlocks,chunkSizePixels);
        generateMap();
        generateTunnels();
    }

    public void generateMap()
    {
        for (int a = 0; a< chunkX; a++)
        {
            for (int b = 0; b< chunkY;b++)
            {
                chunkArray[a][b] = new Chunk(cA.generateBinaryMapping(), new Vector2f(a,b));

            }
        }
    }
    public void generateTunnels()
    {
        for (int a = 0; a< chunkX; a++)
        {
            for (int b = 0; b< chunkY;b++)
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
    public void generateTunnelsBetweenChunks(Chunk c1, Chunk c2)
    {
        int tunnelWidth = 5;
        Vector2f dif = new Vector2f(c2.getcPosition().x - c1.getcPosition().x, c2.getcPosition().y - c1.getcPosition().y);
        Vector2f point1 = c1.genRandomPoint(0,chunkBlockSize-tunnelWidth-1);
        Vector2f point2 = c2.genRandomPoint(0,chunkBlockSize-tunnelWidth-1);
        float split = 0;
        if (dif.x == 1)
        {
            split = (chunkBlockSize - point1.x) / ((chunkBlockSize - point1.x) + point2.x);
            c1.drawLine(tunnelWidth,point1, new Vector2f(chunkBlockSize, point1.y + ((point2.y - point1.y) * split)));
            c2.drawLine(tunnelWidth,point2, new Vector2f(0, point1.y + ((point2.y - point1.y) * split)));

        }
        else if (dif.y == 1)
        {
            split = (chunkBlockSize - point1.y) / ((chunkBlockSize - point1.y) + point2.y);
            c1.drawLine(tunnelWidth,point1, new Vector2f(point1.x + ((point2.x - point1.x) * split), chunkBlockSize));
            c2.drawLine(tunnelWidth,point2, new Vector2f(point1.x + ((point2.x - point1.x) * split), 0));

        }

    }
    public Chunk getChunkAtPosition(Vector2f position)
    {
        return chunkArray[(int)position.x][(int)position.y];
    }

    public Chunk[][] getChunkArray() {
        return chunkArray;
    }
}
