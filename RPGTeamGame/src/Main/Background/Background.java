package Main.Background;

import Main.Background.MapGen.ChunkLoader;
import Main.Background.MapGen.Map;
import Main.ForeGround.Entities.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;


public class Background implements Drawable
{
    int chunkSizeBlocks, chunkSizePixels;
    int numberChunkX;
    int numberChunkY;

    Map mapObject;
    ChunkLoader cH;
    Text fpsCounter;
    Font textFont = new Font();

    public Background(int chunkSizeBlocks, int chunkSizePixels, int numberChunkX, int numberChunkY)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.chunkSizePixels = chunkSizePixels;
        this.numberChunkX = numberChunkX;
        this.numberChunkY = numberChunkY;
        mapObject = new Map(chunkSizeBlocks,chunkSizePixels,numberChunkX,numberChunkY);
        cH = new ChunkLoader(chunkSizeBlocks,chunkSizePixels,mapObject);

    }
    public void initialiseBackGround(Player p)
    {
        cH.generateMetaData(p);
        cH.generateBlockArray(p);
        try
        {
            textFont.loadFromFile(Paths.get("Assets\\LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {

        }
        fpsCounter = new Text("HI", textFont, 100);
        fpsCounter.setPosition(new Vector2f((p.getPosition().x + (p.getpView().getSize().x)/2) - fpsCounter.getLocalBounds().width - 20,(p.getPosition().y - (p.getpView().getSize().y)/2) + fpsCounter.getLocalBounds().height));
    }
    public void updateBackGroundOnMove(Player p, int x, int y)
    {
        Vector2f currentPositionPlayer = p.inChunk(chunkSizePixels);
        fpsCounter.setPosition(new Vector2f((p.getPosition().x + (p.getpView().getSize().x)/2) - fpsCounter.getLocalBounds().width - 20,(p.getPosition().y - (p.getpView().getSize().y)/2) + fpsCounter.getLocalBounds().height));

        if (currentPositionPlayer.x != p.inChunk(chunkSizePixels).x || currentPositionPlayer.y != p.inChunk(chunkSizePixels).y)
        {
            System.out.println(p.inChunk(chunkSizePixels));
            cH.generateMetaData(p);

        }
        cH.generateBlockArray(p);
    }
    public void setFPS(int x)
    {
        fpsCounter.setString(String.valueOf(x));
    }
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(fpsCounter);
        cH.draw(renderTarget,cH.getTransform(renderStates));
    }
}
