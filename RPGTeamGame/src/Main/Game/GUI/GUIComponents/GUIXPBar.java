package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.XPBar;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

/**
 * The XPBar GUI Component
 */
public class GUIXPBar extends GUIComponent<XPBar>
{
    private Color XPColour = new Color(12, 254, 12);   //The colour of the XP gained
    private Color XPEmpty1 = new Color(12, 187, 12);   //The colour of the empty XP Slot 1
    private Color XPEmpty2 = new Color(15, 145, 15);   //The colour of the empty XP Slot 2
    private RectangleShape[] XPSlot = new RectangleShape[12];   //The front element of the XP bar
    private RectangleShape back;                                //The back element of the XP bar
    private Vector2f XPSlotSize;                                //The vector2f of the XP gained
    private Vector2f XPBarBackSize;                             //The vector2f of the total XP
    private float totalXP = 100;                                //The value of the total XP the player needs to level up
    private float currentXP = 10;                               //The current XP value of the player
    private float XPSlotLength;                                 //The length of each slot

    /**
     * InitialiseXPBar()
     * This constructs the XPBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * Each XPSlot is half the length of the inventory slot
     * @param s the given HealthBar component which is to define the GUIComponent
     */
    public GUIXPBar(XPBar s)
    {
        super(s);
        totalXP = s.getMaxXP();
        currentXP = s.getMaxXP();
        XPSlotLength = (float)(Game.WINDOWSIZE-950);
        XPSlotSize = new Vector2f(XPSlotLength, 25);   //length, width of bar
        XPBarBackSize = new Vector2f((float)(Game.WINDOWSIZE-400), 25);     //length, width of bar
        back = new RectangleShape(XPBarBackSize);
        back.setFillColor(XPEmpty2);
        for (int i = 0; i < XPSlot.length; i++){
            XPSlot[i] = new RectangleShape(XPSlotSize);
            if (i % 2 == 0){
                XPSlot[i].setFillColor(XPEmpty1);
            }
            else {
                XPSlot[i].setFillColor(XPEmpty2);
            }
        }
        back.setPosition(200, Game.WINDOWSIZE - XPBarBackSize.y - 135);
    }

    /**
     * updateXPBarPosition()
     * This method updates the XPBar according to the current window size
     */
    @Override
    public void update()
    {
        currentXP = getT().getCurrentXP();
        for (int i = 0; i < XPSlot.length; i++){
            XPSlot[i].setPosition(200+((int)XPSlotLength*i), Game.getGame().getWindow().getSize().y - XPSlotSize.y - 135);
        }
        back.setPosition(200, Game.getGame().getWindow().getSize().y - XPBarBackSize.y - 135);
    }

    /**
     * draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(back,renderStates);
        for (int i = 0; i < XPSlot.length; i++){
            renderTarget.draw(XPSlot[i],renderStates);
        }
    }
}