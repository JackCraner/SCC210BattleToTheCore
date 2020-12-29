package Main.Game.ForeGround;

import Main.Game.Background.MapGen.Map;
import Main.Game.ForeGround.Entities.Entity;
import Main.Game.Game;
import Main.Game.Player.Player;
import Main.Game.ForeGround.Entities.Torch;
import Main.Game.ForeGround.Interfaces.Illuminator;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;


public class Foreground implements Drawable {

    Map map;
    Player p;
    Torch test = new Torch(new Vector2f(100, 100));

    ArrayList<Illuminator> lightList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();


    ActiveForeground activeForeground = new ActiveForeground();
    public Foreground(Player p, Map map) {
        this.p = p;
        this.map = map;


    }

    public void initaliseForeground()
    {
        lightList.add(p);
        entityList.addAll(map.getMapEntityList());

        for (Entity e : entityList) {
            if (e instanceof Torch) {
                lightList.add((Torch) e);
            }
        }
        activeForeground.generateForegroundBox(entityList,p);

    }

    public void updateForeground(Player p)
    {
        activeForeground.generateForegroundBox(entityList,p);
    }

    public ArrayList<Illuminator> getLightList() {
        return lightList;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
       renderTarget.draw(activeForeground,renderStates);
    }

}

 class ActiveForeground implements Drawable {

    Vector2f topLeft;
    Vector2f bottomRight;
    ArrayList<Entity> activeEntityList;
    public void generateForegroundBox (ArrayList<Entity> totalEntityList, Player p)
    {
        activeEntityList = new ArrayList<>();
        float transform = ((Game.VIEWSIZE / 2));
        topLeft = new Vector2f(p.getPosition().x - transform, p.getPosition().y - transform);
        bottomRight = new Vector2f(p.getPosition().x + transform, p.getPosition().y + transform);
        for (Entity e : totalEntityList) {
            if (checkInForegroundBox(e.getPosition())) {
                activeEntityList.add(e);
            }
        }

    }
    public Boolean checkInForegroundBox (Vector2f position)
    {
        int extra = 250;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra)) {
            return true;
        }
        return false;
    }

    @Override
    public void draw (RenderTarget renderTarget, RenderStates renderStates)
    {
        for (Entity e: activeEntityList)
        {
            renderTarget.draw(e, renderStates);
        }
    }
}
