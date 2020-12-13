package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Inventory
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color background = new Color(128, 128, 128);   //The colour of the Inventory
    Color SlotColour = new Color(168, 168, 168);   //The colour of the Slots
    RectangleShape front;                                   //The front element
    RectangleShape back;                                    //The back element
    //RectangleShape[] Slot = new RectangleShape[6];        //6 slots for player items (sprites)
    Vector2f InventorySize;                                 //The vector2f of the Inventory
    Vector2f SlotSize;                                      //The vector2f of the Inventory
    float SlotsEmpty = 6;                                   //The number of lots free
    Player p;                                               //Object reference of player class

    /**
     * InventoryBar() constructor
     * This constructs the InventoryBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * @param p The player
     */
    public Inventory(Player p)
    {
        this.p = p;
        InventorySize = new Vector2f((float)(Game.windowSize - 200), 90);   //length, width of bar
        SlotSize = new Vector2f((float)(Game.windowSize - 900), 90);   //length, width of bar
        /*
        for (int i = 0; i < InventorySlots.length; i++){
            InventorySlots[i] = new Vector2f((float)(Game.windowSize-980), 50);
            Slot[i] = new RectangleShape(InventorySlots[i]);
        } */
        front = new RectangleShape(SlotSize);
        back = new RectangleShape(InventorySize);
        front.setFillColor(SlotColour);
        back.setFillColor(background);
    }

    /**
     * updateInventoryPosition()
     * This method updates the Inventory according to the current window size
     */
    public void updateInventoryPosition()
    {
        /*
        for (int i = 0; i < Slot.length; i++) {
            Slot[i].setPosition(100, Game.windowSize - InventorySlots[i].y - 30);
        } */
        front.setPosition(100, Game.windowSize - InventorySize.y - 30);
        back.setPosition(100, Game.windowSize - InventorySize.y - 30);
    }

    /**
     * getInventoryFront()
     * This method returns the front of the Inventory element
     * @return The front element
     */
    public RectangleShape getInventoryFront()
    {
        return front;
    }

    /**
     * getInventoryBack()
     * This method returns the back of the Inventory element
     * @return The Back element
     */
    public RectangleShape getInventoryBack()
    {
        return back;
    }

    /**
     * AddItem()
     * This method adds a item to the inventory
     * @param x The number of items added
     */
    public void AddItem(int x)
    {
        SlotsEmpty -=x;
        front.setSize(new Vector2f(InventorySize.x * (SlotsEmpty/100), InventorySize.y));
    }
}
