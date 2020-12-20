package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class MiniMap {
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color backgroundColour = new Color(168, 168, 168);         //The colour of the Map background
    RectangleShape front;                                               //The front element of the MiniMap
    RectangleShape back;                                                //The back element of the MiniMap
    Vector2f MapSize;                                                   //The vector2f of the MiniMap
    Player p;                                                           //Object reference of player class

    Sprite mapSprite;
    /**
     * MiniMap() constructor
     * This constructs the XPBar element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     *
     * @param p The player
     */
    public MiniMap(Player p) {
        this.p = p;
        MapSize = new Vector2f((float) (Game.windowSize - 780), 220);    //length, width of Map
        front = new RectangleShape(MapSize);
        back = new RectangleShape(MapSize);
        front.setFillColor(backgroundColour);
        back.setFillColor(backgroundColour);
        mapSprite = new Sprite();
    }

    /**
     * updateMapPosition()
     * This method updates the XPBar according to the current window size
     */
    public void updateMiniMapPosition() {
        front.setPosition(735, Game.windowSize - MapSize.y - 735);
        back.setPosition(735, Game.windowSize - MapSize.y - 735);
        mapSprite.setPosition(735, Game.windowSize - MapSize.y - 735);
        mapSprite.setScale(0.25f,0.25f);
    }

    public void updateMiniMapGraphic(ConstTexture t)
    {
        mapSprite.setTexture(t);
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


    public Sprite getMiniMapSprite()
    {
        return mapSprite;
    }
}
