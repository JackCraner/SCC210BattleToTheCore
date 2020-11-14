package Main.MapGen;

import Main.MapGen.CellularAutomata.CellularAutomata;
import Main.MapGen.PerlinNoise.PerlinNoise;
import Main.Sprites.Block;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Chunk
{
    private Generator binaryMappingObject;
    private int[][] binaryMapping;
    private Block[][] screenBlockArray;


    private int chunkSizeBlocks;
    private int chunkSizePixels;
    private Vector2f chunkPosition;

    private Path wallTexturePath = Paths.get("Assets\\WallTexture.png");
    private Path emptyTexturePath = Paths.get("Assets\\WaterTexture.png");
    private Path diamondTexturePath = Paths.get("Assets\\DiamondTexture.png");
    private Path grassTexturePath = Paths.get("Assets\\GrassTexture.png");
    private Texture wallTexture = new Texture();
    private Texture emptyTexture = new Texture();
    private Texture diamondTexture= new Texture();
    private Texture grassTextue = new Texture();

    //private Generator chunkDesign;


    int numOfTunnels = 3;
    int currentTunnels = 0;

    public Chunk(int chunkSizeBlocks, int chunkSizePixels, Vector2f chunkPosition)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        this.chunkPosition = chunkPosition;

        try
        {
            wallTexture.loadFromFile(wallTexturePath);
            emptyTexture.loadFromFile(emptyTexturePath);
            diamondTexture.loadFromFile(diamondTexturePath);
            grassTextue.loadFromFile(grassTexturePath);
        }
        catch(Exception E)
        {

        }
        binaryMappingObject = generateChunkCellularAutomata();
        binaryMapping = binaryMappingObject.getChunkBinaryMapping();
        screenBlockArray = createMap(binaryMapping);  //change this Line
    }

    public Generator generateChunkCellularAutomata()
    {
        return new CellularAutomata(chunkSizeBlocks,chunkSizePixels, chunkPosition);
    }

    public Generator generateChunkPerlinNoise()
    {
        return new PerlinNoise(chunkSizeBlocks,chunkSizePixels, chunkPosition);
    }

    public Block[][] createMap(int[][] mapBluePrint)
    {
        Vector2f[][] blockPosition = generateLocations();
        Block[][] blockMap = new Block[chunkSizeBlocks][chunkSizeBlocks];

        for (int a = 0; a < chunkSizeBlocks; a++)
        {
            for (int b = 0; b < chunkSizeBlocks; b++)
            {
                if (mapBluePrint[a][b] == 1)
                {

                    blockMap[a][b] = new Block(0,emptyTexture);


                }
                else
                {
                    blockMap[a][b] = new Block(0,wallTexture);

                }
                blockMap[a][b].setPosition(new Vector2f((chunkPosition.x * chunkSizePixels) + blockPosition[a][b].x, (chunkPosition.y * chunkSizePixels) + blockPosition[a][b].y));
                blockMap[a][b].scale(1f,1f);
            }
        }

        return blockMap;
    }

    public void drawScreen(RenderWindow window)
    {
        for (int a = 0; a < chunkSizeBlocks; a++)
        {
            for (int b = 0; b < chunkSizeBlocks; b++)
            {
                window.draw(screenBlockArray[a][b]);


            }
        }

    }

    public Vector2f[][] generateLocations()
    {
        Vector2f[][] locations = new Vector2f[chunkSizeBlocks][chunkSizeBlocks];
        float step = (chunkSizePixels / chunkSizeBlocks);


        for (int a = 0; a < chunkSizeBlocks; a++)
        {
            for (int b = 0; b < chunkSizeBlocks; b++)
            {
                locations[a][b] = new Vector2f(a*step, b*step);
            }
        }
        return locations;
    }


    public boolean equals(Vector2f o)
    {
        if (o.x == getChunkPosition().x && o.y == getChunkPosition().y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Vector2f getChunkPosition()
    {
        return chunkPosition;
    }

    public int[][] combineChunks(Chunk firstChunk, Chunk secondChunk, boolean isVertial)
    {
        int[][] combinedMap;
        int[][] firstBM = firstChunk.getBinaryMapping();
        int[][] secondBM = secondChunk.getBinaryMapping();
        if (isVertial)
        {
            combinedMap = new int[chunkSizeBlocks][chunkSizeBlocks * 2];
            for (int a = 0; a < chunkSizeBlocks; a++) {
                for (int b = 0; b < chunkSizeBlocks * 2; b++) {
                    if (b >= chunkSizeBlocks) {
                        combinedMap[a][b] = secondBM[a][b - chunkSizeBlocks];
                    } else {
                        combinedMap[a][b] = firstBM[a][b];
                    }


                }
            }
        }
        else
        {
            combinedMap = new int[chunkSizeBlocks * 2][chunkSizeBlocks];
            for (int a = 0; a < chunkSizeBlocks; a++) {
                for (int b = 0; b < chunkSizeBlocks * 2; b++) {
                    if (b >= chunkSizeBlocks) {
                        combinedMap[b][a] = secondBM[b- chunkSizeBlocks][a];
                    } else {
                        combinedMap[b][a] = firstBM[b][a];
                    }


                }
            }
        }

        return combinedMap;
    }

    public void splitChunks(int[][] combinedMap, Chunk firstChunk, Chunk secondChunk, boolean isVertial)
    {
        int[][] firstBM  = new int[chunkSizeBlocks][chunkSizeBlocks];
        int[][] secondBM = new int[chunkSizeBlocks][chunkSizeBlocks];

        if (isVertial)
        {
            for (int a = 0; a < chunkSizeBlocks; a++) {
                for (int b = 0; b < chunkSizeBlocks * 2; b++) {
                    if (b >= chunkSizeBlocks) {
                        secondBM[a][b - chunkSizeBlocks] = combinedMap[a][b];
                    } else {
                        firstBM[a][b] = combinedMap[a][b];
                    }


                }
            }
        }
        else
        {

            for (int a = 0; a < chunkSizeBlocks; a++) {
                for (int b = 0; b < chunkSizeBlocks * 2; b++) {
                    if (b >= chunkSizeBlocks) {
                        secondBM[b- chunkSizeBlocks][a] = combinedMap[b][a];
                    } else {
                        firstBM[b][a] = combinedMap[b][a];
                    }


                }
            }
        }

        firstChunk.setBinaryMapping(firstBM);
        secondChunk.setBinaryMapping(secondBM);
    }
    public int[][] doTunnel(int[][] combinedMap, Vector2f point1, Vector2f point2, boolean isVertical)
    {
        int tunnelWidth = 6;
        if (point1 != null && point2 != null)
        {


            float gradientCounter = 0;
            int counter = 0;
            if (isVertical)
            {
                float gradient = ((point2.x - point1.x) / (point2.y - point1.y));

                for (int a = (int)point1.y; a<=(int)point2.y;a++)
                {
                    for (int tW = 0; tW < tunnelWidth; tW ++)
                    {
                        combinedMap[(int)point1.x + counter+tW][a] = 1;
                    }

                    gradientCounter += gradient;
                    if (gradientCounter > 1)
                    {
                        counter +=1;
                        gradientCounter = 0;
                    }
                    if (gradientCounter < -1)
                    {
                        counter -=1;
                        gradientCounter = 0;
                    }
                }

            }
            else
            {

                float gradient = ((point2.y - point1.y) / (point2.x - point1.x));
                for (int a = (int)point1.x; a<=(int)point2.x;a++)
                {
                    for (int tW = 0; tW < tunnelWidth; tW ++)
                    {
                        combinedMap[a][(int)point1.y + counter+tW] = 1;
                    }

                    gradientCounter += gradient;
                    if (gradientCounter > 1)
                    {
                        counter +=1;
                        gradientCounter = 0;
                    }
                    if (gradientCounter < -1)
                    {
                        counter -=1;
                        gradientCounter = 0;
                    }
                }

            }
        }

        return combinedMap;

    }
    public void getTunnel(Chunk oChunk)
    {


        Vector2f angle = new Vector2f(oChunk.getChunkPosition().x - getChunkPosition().x,oChunk.getChunkPosition().y - getChunkPosition().y);
        if ((angle.x > 1 || angle.y > 1) || (angle.x < -1 || angle.y < -1) || (angle.x * angle.y != 0) || (angle.x == 0 && angle.y == 0))
        {

        }
        else
        {
            if (currentTunnels == numOfTunnels)
            {

            }
            else{
                currentTunnels++;

                Vector2f mePoint = genRandomPoint(this,0,chunkSizeBlocks-6);
                Vector2f otherPoint = genRandomPoint(oChunk,0, chunkSizeBlocks -6);
                int[][] combinedMap;

                if (angle.x > 0)
                {


                    combinedMap = combineChunks(this,oChunk,false);
                    otherPoint = new Vector2f(otherPoint.x + chunkSizeBlocks, otherPoint.y);
                    combinedMap = doTunnel(combinedMap,mePoint,otherPoint,false);
                    splitChunks(combinedMap,this,oChunk,false);


                }
                else if (angle.x < 0)
                {

                    combinedMap = combineChunks(oChunk,this,false);
                    mePoint = new Vector2f(mePoint.x + chunkSizeBlocks, mePoint.y);
                    combinedMap = doTunnel(combinedMap,otherPoint, mePoint, false);
                    splitChunks(combinedMap,oChunk,this,false);




                }
                else if (angle.y > 0)
                {

                    combinedMap = combineChunks(this,oChunk,true);
                    otherPoint = new Vector2f(otherPoint.x, otherPoint.y+ chunkSizeBlocks);
                    combinedMap = doTunnel(combinedMap,mePoint,otherPoint,true);
                    splitChunks(combinedMap,this,oChunk,true);




                }
                else
                {

                    combinedMap = combineChunks(oChunk,this,true);
                    mePoint = new Vector2f(mePoint.x, mePoint.y+ chunkSizeBlocks);
                    combinedMap = doTunnel(combinedMap,otherPoint,mePoint,true);
                    splitChunks(combinedMap,oChunk,this,true);







                }
            }







        }



    }



    public Vector2f genRandomPoint(Chunk c, int a, int b)
    {
        int[][] tBM = c.getBinaryMapping();
        int pointX = (int)(Math.random() * (b - a));
        int pointY = (int)(Math.random() * (b - a));

        if (tBM[pointX][pointY] == 1)
        {
            return new Vector2f(pointX,pointY);
        }
        else
        {
            return genRandomPoint(c,a,b);
        }
    }
    public int[][] getBinaryMapping()
    {
        return binaryMapping;
    }
    public void setBinaryMapping(int[][] bM)
    {
        binaryMapping = bM;
        screenBlockArray = createMap(binaryMapping);
    }
}
