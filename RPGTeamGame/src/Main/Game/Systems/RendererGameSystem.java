package Main.Game.Systems;

import Main.Game.Communication.GameEvent;
import Main.Game.Entity.Component;
import Main.Game.Entity.Components.NearPlayerFlag;
import Main.Game.Entity.Components.Position;
import Main.Game.Entity.Components.SpriteController;
import Main.Game.Level;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    @Override
    public ArrayList<Class<? extends Component>> systemComponentRequirements() {
        ArrayList<Class<? extends Component>> c = new ArrayList<>();

        c.add(Position.class);                      //the order of the requirements defines the layout of components in the array
        c.add(SpriteController.class);              //thus getComponentArrayList().get(i)[0] will always be position
                                                    //and getComponentArrayList().get(i)[1] will be SpriteController
        return c;
    }

    @Override
    public void update()
    {
        for(Component[] cA: getComponentArrayList())
        {
            ((SpriteController)cA[1]).getSpriteComponent().setPosition(((Position)cA[0]).position);
             Level.getLevel().getWindow().draw(((SpriteController)cA[1]).getSpriteComponent());
        }




    }

    @Override
    public void update(Event event) {

    }


    public static RendererGameSystem getSystemInstance() {
        return systemInstance;
    }


}
