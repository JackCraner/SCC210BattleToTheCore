package Main.Game.GUI;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

public class GUIManager implements Drawable
{

    Inventory inv = new Inventory(6);
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(inv.getInventoryBack());

        for (int i =0; i < inv.getTotalSlots(); i++)
        {
            renderTarget.draw(inv.getInventorySlots(i));
        }

    }
}
