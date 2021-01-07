package Main.Game.GUI;

import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class MiniMap
{
    //Initialising MiniMap as a singleton
    private static MiniMap minimap = new MiniMap();              //Static variable for minimap
    public static MiniMap getMiniMap(){
        return minimap;
    }       //Method to return the variable
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color backgroundColour = new Color(168, 168, 168);         //The colour of the Map background
    RectangleShape front;                                               //The front element of the MiniMap
    RectangleShape back;                                                //The back element of the MiniMap
    Vector2f MapSize;                                                   //The vector2f of the MiniMap

    /**
     * InitialiseMiniMap()
     * This constructs the XPBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     */
    public void InitialiseMiniMap(){
        MapSize = new Vector2f((float)(Game.getGame().getWindow().getSize().x - 780), 220);    //length, width of Map
        front = new RectangleShape(MapSize);
        back = new RectangleShape(MapSize);
        front.setFillColor(backgroundColour);
        back.setFillColor(backgroundColour);
    }

    /**
     * updateMapPosition()
     * This method updates the XPBar according to the current window size
     */
    public void updateMiniMapPosition() {
        front.setPosition(735, Game.getGame().getWindow().getSize().y - MapSize.y - 735);
        back.setPosition(735, Game.getGame().getWindow().getSize().y - MapSize.y - 735);
    }

    /**
     * getMapFront()
     * This method returns the front of the MiniMap element
     * @return The front element
     */
    public RectangleShape getMiniMapFront() {
        return front;
    }

    /**
     * getMapBack()
     * This method returns the back of the Minimap element

     * @return The back element
     */
    public RectangleShape getMiniMapBack() {
        return back;
    }
}
