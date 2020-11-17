package Main.MapGen;

public class Cave
{
    int[][] caveArray;
    int caveSizeInt;
    public Cave(int chuckSize, int chuckPixels)
    {
        caveArray = new int[chuckSize][chuckSize];
    }


    public void addElement(int x, int y)
    {
        caveArray[x][y] = 1;
        caveSizeInt ++;
    }

    public int[][] getArray()
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
