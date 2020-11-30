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

    Vector2f currentPlayerChunk;
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
        currentPlayerChunk = p.inChunk(chunkSizePixels);
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
    public void updateBackGroundOnMove(Player p)
    {

        fpsCounter.setPosition(new Vector2f((p.getPosition().x + (p.getpView().getSize().x)/2) - fpsCounter.getLocalBounds().width - 20,(p.getPosition().y - (p.getpView().getSize().y)/2) + fpsCounter.getLocalBounds().height));

        if (currentPlayerChunk.x != p.inChunk(chunkSizePixels).x || currentPlayerChunk.y != p.inChunk(chunkSizePixels).y)
        {
            currentPlayerChunk = p.inChunk(chunkSizePixels);
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

        cH.draw(renderTarget,cH.getTransform(renderStates));
        renderTarget.draw(fpsCounter);
    }
}
