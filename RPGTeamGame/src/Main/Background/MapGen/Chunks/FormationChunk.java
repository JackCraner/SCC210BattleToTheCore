package Main.Background.MapGen.Chunks;


import Main.Background.MapGen.Block;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class FormationChunk extends Chunk
{

    private Block[][] chunkBlockMapping;
    private Vector2f cPosition;

    public FormationChunk(Block[][] mapping, Vector2f cPosition)
    {
        super(mapping,cPosition);
    }

    public Vector2f genRandomPoint(int a, int b) // between a and b which is empty
    {
        int pointX = (int)(Math.random() * (b - a));
        int pointY = (int)(Math.random() * (b - a));

        if (chunkBlockMapping[pointX][pointY].getID() == 1)
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
                    chunkBlockMapping[a][(int) (gradient * a + c) + thick].setID(1);
                } catch (Exception e) {

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
                    chunkBlockMapping[(int) ((a - c) / gradient + thick)][a].setID(1);
                } catch (Exception e) {

                }
            }

        }



    }

    public Vector2f getPosition()
    {
        return cPosition;
    }

}