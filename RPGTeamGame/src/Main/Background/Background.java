package Main.Background;

import Main.Background.MapGen.ChunkLoader;
import Main.Background.MapGen.Map;
import Main.ForeGround.Entities.Player;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;


public class Background implements Drawable
{


    Map mapObject;
    ChunkLoader cH;


    Vector2f currentPlayerChunk;
    public Background()
    {
        mapObject = new Map();
        cH = new ChunkLoader(mapObject);

    }
    public void initialiseBackGround(Player p)
    {
        currentPlayerChunk = p.inChunk();
        cH.generateBlockArray(p);

    }
    public void updateBackGroundOnMove(Player p)
    {
        cH.generateBlockArray(p);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {

        cH.draw(renderTarget,cH.getTransform(renderStates));

    }

    public Map getMapObject() {
        return mapObject;
    }
}
