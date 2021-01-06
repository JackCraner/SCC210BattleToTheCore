package Main.Game.QuadTree;

import Main.Game.ECS.Entity.GameObject;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.system.Vector2i;

public class QTRoot
{

    private static QTRoot QUADROOT = new QTRoot();

    public  int LEAFSIZE = 512;      //16 blocks --- 16 * 32

    public int NUMLEAFX = (int)Math.ceil(CellularAutomata.CHUNKSIZEPIXELSX / LEAFSIZE)+1;
    public int NUMLEAFY = (int)Math.ceil(CellularAutomata.CHUNKSIZEPIXELSY / LEAFSIZE)+1;


    QTLeaf[][] quadTree = new QTLeaf[NUMLEAFX][NUMLEAFY];


    private QTRoot()
    {

        for(int a = 0; a< NUMLEAFX; a++)
        {
            for (int b = 0; b< NUMLEAFY; b++)
            {
                quadTree[a][b] = new QTLeaf();
            }
        }
    }

    public QTLeaf getLeaf(Vector2i position)
    {
        return quadTree[position.x][position.y];
    }
    public static QTRoot getQuadTree()
    {
        return QUADROOT;
    }

    public void addGameObject(GameObject g, Vector2i leafPosition)
    {
        quadTree[leafPosition.x][leafPosition.y].addObjectToLeaf(g);
    }
    public void removeGameObject(GameObject g, Vector2i leafPosition)
    {
        quadTree[leafPosition.x][leafPosition.y].removeObjectFromLeaf(g);
    }
}
