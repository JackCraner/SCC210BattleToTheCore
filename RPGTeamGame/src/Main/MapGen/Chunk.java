package Main.MapGen;


import Main.Main;
import org.jsfml.system.Vector2f;

public class Chunk
{

    private int[][] chunkBinaryMapping;
    private Vector2f cPosition;

    public Chunk(int[][] mapping, Vector2f cPosition)
    {
        chunkBinaryMapping = mapping;
        this.cPosition = cPosition;
    }
    public Vector2f getcPosition()
    {
        return cPosition;
    }
    public int[][] getChunkMapping()
    {
        return chunkBinaryMapping;
    }
    public int getBlockAtVector(Vector2f v)
    {
        return chunkBinaryMapping[(int)v.x][(int)v.y];
    }
    public Vector2f genRandomPoint(int a, int b) // between a and b which is empty
    {
        int pointX = (int)(Math.random() * (b - a));
        int pointY = (int)(Math.random() * (b - a));

        if (chunkBinaryMapping[pointX][pointY] == 1)
        {
            return new Vector2f(pointX,pointY);
        }
        else
        {
            return genRandomPoint(a,b);
        }
    }
    public void drawLine(int lineThickness, Vector2f point1, Vector2f point2)
    {
        float gradient = ((point2.y - point1.y) / (point2.x - point1.x));
        float c = point1.y - (gradient * point1.x);
        int startPoint = (int)Math.min(point1.x, point2.x);
        int endPoint = (int)Math.max(point1.x, point2.x);
        for (int a = startPoint; a<endPoint; a++)
        {
            for (int thick = 0; thick<lineThickness;thick++)
            {
                try {
                    chunkBinaryMapping[a][(int) (gradient * a + c) + thick] = 1;
                } catch (Exception e) {
                    System.out.println("A: " + a + " G: " + gradient + " C: " + c + " Y: " + (int) (gradient * a + c));
                }
            }
        }
        startPoint = (int)Math.min(point1.y, point2.y);
        endPoint = (int)Math.max(point1.y, point2.y);
        for (int a = startPoint; a<endPoint; a++)
        {
            for (int thick = 0; thick<lineThickness;thick++)
            {
                try {
                    chunkBinaryMapping[(int) ((a - c) / gradient + thick)][a] = 1;
                } catch (Exception e) {
                    System.out.println("A: " + a + " G: " + gradient + " C: " + c + " Y: " + (int) (gradient * a + c));
                }
            }

        }



    }

}
