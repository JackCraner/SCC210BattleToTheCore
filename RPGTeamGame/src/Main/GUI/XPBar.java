package Main.GUI;

import Main.Player.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class XPBar
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color XPColour = new Color(12, 254, 12);   //The colour of the XP gained
    Color XPEmpty1 = new Color(12, 187, 12);   //The colour of the empty XP Slot 1
    Color XPEmpty2 = new Color(15, 145, 15);     //The colour of the empty XP Slot 2
    RectangleShape[] XPSlot;                             //The front element of the XP bar
    RectangleShape back;                                 //The back element of the XP bar
    Vector2f XPSlotSize;                                 //The vector2f of the XP gained
    Vector2f XPBarBackSize;                              //The vector2f of the total XP
    float totalXP = 100;                                 //The value of the total XP the player needs to level up
    float currentXP = 10;                                //The current XP value of the player
    float XPSlotLength;                                  //The length of each slot
    int TotalXPSlots;                                    //The number of slots in the XP bar
    Player p;                                            //Object reference of player class

    /**
     * XPBar() constructor
     * This constructs the XPBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * Each XPSlot is half the length of the inventory slot
     * @param p The player
     * @param numOfSlots The number of slots the inventory has
     */
    public XPBar(Player p, int numOfSlots)
    {
        this.p = p;
        this.TotalXPSlots = numOfSlots*2;
        this.XPSlot = new RectangleShape[TotalXPSlots];
        XPSlotLength = (float)(Game.windowSize-950);
        XPSlotSize = new Vector2f(XPSlotLength, 25);   //length, width of bar
        XPBarBackSize = new Vector2f((float)(Game.windowSize-400), 25);     //length, width of bar
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

    }

    /**
     * updateXPBarPosition()
     * This method updates the XPBar according to the current window size
     */
    public void updateXPBarPosition()
    {
        for (int i = 0; i < XPSlot.length; i++){
            XPSlot[i].setPosition(200+((int)XPSlotLength*i), Game.windowSize - XPSlotSize.y - 135);
        }
        back.setPosition(200, Game.windowSize - XPBarBackSize.y - 135);
    }

    /**
     * getTotalXPSlots()
     * This method returns the number of slots in the XP Bar
     * @return The number of slots
     */
    public int getTotalXPSlots(){
        return TotalXPSlots;
    }

    /**
     * getXPBarFront()
     * This method returns the front of the XPBar element
     * @return The front element
     */
    public RectangleShape getXPBarSlot(int i)
    {
        return XPSlot[i];
    }

    /**
     * getXPBarBack()
     * This method returns the back of the XPBar element
     * @return The back element
     */
    public RectangleShape getXPBarBack()
    {
        return back;
    }

    /**
     * AddXP()
     * This method adds XP to the XPBar
     * @param x The amount of XP gained
     */
    public void AddXP(int x)
    {
        /*
        if (front.getSize() >= back.getSize()){
        } */
        currentXP +=x;
        for (int i = 0; i < x; i++){
            XPSlot[i].setFillColor(XPColour);
        }
    }
}