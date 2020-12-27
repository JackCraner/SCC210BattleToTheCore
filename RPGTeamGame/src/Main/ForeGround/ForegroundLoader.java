package Main.ForeGround;

import Main.ForeGround.Entities.Chest;
import Main.ForeGround.Entities.Entity;
import Main.ForeGround.Entities.Torch;
import Main.Game;
import Main.Player.Player;
import Main.Shader.Light;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class ForegroundLoader implements Drawable
{
    Vector2f topLeft;
    Vector2f bottomRight;
    ArrayList<Entity> entityList = new ArrayList<>();
    ArrayList<Entity> activeEntities = new ArrayList<>();
    ArrayList<Light> lightList = new ArrayList<>();
    public ForegroundLoader(ArrayList<Torch> mapTorchList, ArrayList<Chest> mapChestList)
    {
        entityList.addAll(mapTorchList);
        entityList.addAll(mapChestList);
    }


    public void generateForegroundBox(Player p)
    {
        activeEntities = new ArrayList<>();
        float transform = ((Game.viewSize/2));
        topLeft = new Vector2f(p.getPosition().x - transform, p.getPosition().y - transform);
        bottomRight = new Vector2f(p.getPosition().x + transform, p.getPosition().y + transform);
        for (Entity e: entityList)
        {
            if (checkInForegroundBox(e.getPosition()))
            {
                activeEntities.add(e);

            }
        }

    }
    public Boolean checkInForegroundBox(Vector2f position)
    {
        int extra = 250;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            return true;
        }
        return false;
    }








    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for (Entity e: activeEntities)
        {
            e.draw(renderTarget,renderStates);
        }

    }
}
