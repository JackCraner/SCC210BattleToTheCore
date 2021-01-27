package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Mana;
import Main.Game.GUI.GUIComponent;
import Main.Game.Menu.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

public class GUIManaBar extends GUIComponent<Mana>
{
    private Color HP = new Color(10, 153, 249);         //The colour of the current Health
    private Color HPLost = new Color(3, 72, 117);     //The colour of the Health Lost
    private RectangleShape front;                               //The front element of the Health Orb
    private RectangleShape back;                                //The back element of the Health Orb
    private Vector2f manaOrbSize;                             //The vector2f of the Health Orb
    private float totalMana;                           //The value of the total Health
    private float currentMana;                          //The value of the current Health
    private float topLeftY;

    public GUIManaBar(Mana s)
    {
        super(s);
        totalMana = s.getMaxMana();
        currentMana = s.getMaxMana();
        manaOrbSize = new Vector2f((float)(Game.WINDOWSIZE-870), 130); //length, width of bar
        front = new RectangleShape(manaOrbSize);
        back = new RectangleShape(manaOrbSize);
        front.setPosition(820, Game.WINDOWSIZE - manaOrbSize.y - 30);
        back.setPosition(820, Game.WINDOWSIZE- manaOrbSize.y - 30);
        topLeftY = front.getPosition().y;
        front.setFillColor(HPLost);
        back.setFillColor(HP);
    }

    @Override
    public void update()
    {
        currentMana = getT().getStat();
        front.setPosition(new Vector2f(front.getPosition().x,topLeftY + (manaOrbSize.y *(1-(currentMana/totalMana)))));
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(front,renderStates);
        renderTarget.draw(back,renderStates);
    }
}
