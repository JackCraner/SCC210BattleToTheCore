package Main.Game.GUI;

import Main.Game.ECS.Components.Backpack;
import Main.Game.ECS.Components.HealthBar;
import Main.Game.ECS.Components.ManaBar;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.GUI.GUIComponents.GUIInvectory;
import Main.Game.GUI.GUIComponents.GUIManaBar;
import Main.Game.Game;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import java.util.HashMap;

public class GUIManager implements Drawable
{
    public static GameObject GUITarget = Game.PLAYER;
    private static GUIManager GUIinstance = new GUIManager();
    public static GUIManager getGUIinstance() {
        return GUIinstance;
    }



    private static HashMap<GUIComponentENUM,GUIComponent> guiComponentList = new HashMap<>();
    static{
        guiComponentList.put(GUIComponentENUM.INVENTORY, new GUIInvectory(GUITarget.getComponent(Backpack.class)));
        guiComponentList.put(GUIComponentENUM.HEALTHBAR,new GUIHealthBar(GUITarget.getComponent(HealthBar.class)));
        guiComponentList.put(GUIComponentENUM.MANABAR,new GUIManaBar(GUITarget.getComponent(ManaBar.class)));
    }

    private GUIManager()
    {

    }

    public void GUIUpdate(GUIComponentENUM e)
    {
        guiComponentList.get(e).update();
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for (GUIComponentENUM g: GUIComponentENUM.values())
        {
            renderTarget.draw(guiComponentList.get(g),renderStates);
        }
    }
}
