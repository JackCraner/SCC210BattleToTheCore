package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.Backpack;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import Main.Game.Menu.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class GUIInvectory extends GUIComponent<Backpack>
{
    private Color background = new Color(128, 128, 128);   //The colour of the Inventory
    private Color slotColour1 = new Color(168, 168, 168);  //The colour of the Slots
    private Color slotColour2 = new Color(195, 195, 195);  //The colour of the Slots
    private RectangleShape back;                                    //The back element
    private int numSlots = 6;                                           //The number of slots in the inventory
    private GameObject[] inventoryGameObjects = new GameObject[numSlots];
    private RectangleShape[] inventoryRectangles = new RectangleShape[numSlots];
    private Vector2f inventorySize;                                 //The vector2f of the Inventory
    private Vector2f slotSize;                                      //The vector2f of the Inventory
    private float slotsEmpty;                                       //The number of lots free
    private float slotLength;                                       //The length of each slot


    public GUIInvectory(Backpack b)
    {
        super(b);
        slotLength = (float)(Game.WINDOWSIZE - 900);
        slotSize = new Vector2f(slotLength, 90);
        inventorySize = new Vector2f((float)(Game.WINDOWSIZE - 400), 90);
        back = new RectangleShape(inventorySize);
        back.setFillColor(background);
        for (int i = 0; i < inventoryRectangles.length; i++){
            inventoryRectangles[i] = new RectangleShape(slotSize);
            if (i % 2 == 0){
                inventoryRectangles[i].setFillColor(slotColour1);
            }
            else {
                inventoryRectangles[i].setFillColor(slotColour2);
            }
            inventoryRectangles[i].setPosition(200 + (slotLength * i), Game.WINDOWSIZE - inventorySize.y - 30);
        }
        back.setPosition(200, Game.WINDOWSIZE - inventorySize.y - 30);

    }

    @Override
    public void update()
    {
        for(RectangleShape rec: inventoryRectangles)
        {
            rec.setTexture(null);
        }
        for (int a = 0; a < getT().getObjectsINBACKPACK().size(); a++ )
        {
            inventoryGameObjects[a] = getT().getObjectsINBACKPACK().get(a);
            inventoryRectangles[a].setTexture(TextureMap.TEXTUREMAP.get(inventoryGameObjects[a].getComponent(TextureComponent.class).textureString));
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(back,renderStates);
        for (RectangleShape r: inventoryRectangles)
        {
            if (r.getTexture()!=null)
            {
                Transform t = new Transform();
                t = Transform.rotate(t,-45,r.getPosition().x + r.getSize().x/2,r.getPosition().y+r.getSize().y/2);
                RenderStates rs = new RenderStates(renderStates,t);
                renderTarget.draw(r,rs);
            }
            else
            {
                renderTarget.draw(r,renderStates);
            }

        }
    }
}
