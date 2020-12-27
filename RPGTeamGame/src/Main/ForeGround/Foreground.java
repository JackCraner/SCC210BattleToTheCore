package Main.ForeGround;

import Main.Background.MapGen.Map;
import Main.ForeGround.Entities.Chest;
import Main.Player.Player;
import Main.ForeGround.Entities.Torch;
import Main.ForeGround.Interfaces.Illuminator;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;


public class Foreground implements Drawable
{
    Torch test = new Torch(new Vector2f(100,100));
    ArrayList<Torch> torchList = new ArrayList<>();
    ArrayList<Chest> chestList = new ArrayList<>();
    ArrayList<Illuminator> lightList = new ArrayList<>();
    ForegroundLoader fL;

    public Foreground(Player p, Map m)
    {
        lightList.add(p);
        lightList.add(test);
        torchList = m.getMapTorchList();
        chestList = m.getMapChestList();
        lightList.addAll(m.getMapTorchList());

        fL = new ForegroundLoader(torchList,chestList);
    }
    public void initaliseForeground(Player p)
    {
        fL.generateForegroundBox(p);
    }
    public void updateForeground(Player p)
    {
        fL.generateForegroundBox(p);
    }
    public ArrayList<Illuminator> getLightList() {
        return lightList;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
      fL.draw(renderTarget,renderStates);
    }
}
