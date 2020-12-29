package Main.Game.Background;

import Main.Game.Background.MapGen.ChunkLoader;
import Main.Game.Background.MapGen.Map;
import Main.Game.Player.Player;
import org.jsfml.graphics.*;


public class Background implements Drawable
{


    Map mapObject;
    ChunkLoader cH;


    public Background()
    {
        mapObject = new Map();
        cH = new ChunkLoader(mapObject);

    }
    public void initialiseBackGround(Player p)
    {
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
