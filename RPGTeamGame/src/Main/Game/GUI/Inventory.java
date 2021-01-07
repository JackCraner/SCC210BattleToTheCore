package Main.Game.GUI;

import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Inventory
{
    //Initialising Inventory as a singleton
    private static Inventory inventory = new Inventory();           //Static variable for inventory
    public static Inventory getInventory(){
        return inventory;
    }    //Method to return the variable
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color background = new Color(128, 128, 128);   //The colour of the Inventory
    Color SlotColour1 = new Color(168, 168, 168);  //The colour of the Slots
    Color SlotColour2 = new Color(195, 195, 195);  //The colour of the Slots
    RectangleShape back;                                    //The back element
    int numSlots;                                           //The number of slots in the inventory
    RectangleShape[] Slot;                                  //Slots for player items (sprites)
    Vector2f InventorySize;                                 //The vector2f of the Inventory
    Vector2f SlotSize;                                      //The vector2f of the Inventory
    float SlotsEmpty;                                       //The number of lots free
    float SlotLength;                                       //The length of each slot

    /**
     * InitialiseInventory()
     * This constructs the InventoryBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * Also the Inventory can have any number of slots
     * @param numOfSlots The amount of slots the inventory is going to have
     */
    public void InitialiseInventory(int numOfSlots)
    {
        this.SlotsEmpty = numOfSlots;
        this.numSlots = numOfSlots;
        this.Slot = new RectangleShape[numOfSlots];
        SlotLength = (float)(Game.getGame().getWindow().getSize().x - 900);
        InventorySize = new Vector2f((float)(Game.getGame().getWindow().getSize().x - 400), 90);   //length, width of bar
        SlotSize = new Vector2f(SlotLength, 90);   //length, width of bar
        back = new RectangleShape(InventorySize);
        back.setFillColor(background);
        for (int i = 0; i < Slot.length; i++){
            Slot[i] = new RectangleShape(SlotSize);
            if (i % 2 == 0){
                Slot[i].setFillColor(SlotColour1);
            }
            else {
                Slot[i].setFillColor(SlotColour2);
            }
        }
    }

    /**
     * updateInventoryPosition()
     * This method updates the Inventory according to the current window size
     */
    public void updateInventoryPosition()
    {
        for (int i = 0; i < Slot.length; i++) {
            int temp = 200+((int)SlotLength*i);
            Slot[i].setPosition(temp, Game.getGame().getWindow().getSize().y - InventorySize.y - 30);
        }
        back.setPosition(200, Game.getGame().getWindow().getSize().y - InventorySize.y - 30);
    }

    /**
     * getTotalSlots()
     * This method returns the number of slots in the inventory
     * @return The number of slots
     */
    public int getTotalSlots(){
        return numSlots;
    }

    /**
     * getInventoryFront()
     * This method returns the front of the Inventory element
     * @return The front element
     */
    public RectangleShape getInventorySlots(int i)
    {
        return Slot[i];
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
        SlotsEmpty -= SlotsEmpty;
        Slot[x].setFillColor(background);
    }
}
