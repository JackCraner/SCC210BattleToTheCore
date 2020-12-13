package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class GUIController implements Drawable
{
    Text fpsCounter;                //Displays the number of frames per second
    Font textFont = new Font();     //The font for Displaying text
    HealthBar hB;                   //Heath bar
    Inventory iB;                   //Inventory
    XPBar xpB;                      //XP bar
    MiniMap map;                    //Mini Map
    public GUIController()
    {
    }

    /**
     * initialiseGUI()
     * This method initialises the UI overlay of the game and all included elements
     * @param p The player
     */
    public void initializeGUI(Player p)
    {
        try
        {
            textFont.loadFromFile(Paths.get("Assets\\LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println("Font not found");
        }
        hB = new HealthBar(p);
        iB = new Inventory(p);
        xpB = new XPBar(p);
        map = new MiniMap(p);
        hB.takeDamage(40);
        hB.updateHealthBarPosition();
        iB.updateInventoryPosition();
        xpB.updateXPBarPosition();
        map.updateMiniMapPosition();
        fpsCounter = new Text("HI", textFont, 100);     //Fps font and size
        fpsCounter.setPosition(new Vector2f(Game.windowSize-970,30));
    }

    /**
     * updateGUI()
     * This method updates all the elements in the uI according to the current window size
     */
    public void updateGUI()
    {
        hB.updateHealthBarPosition();
        iB.updateInventoryPosition();
        xpB.updateXPBarPosition();
        map.updateMiniMapPosition();
        fpsCounter.setPosition(new Vector2f(Game.windowSize-970,30));
    }

    /**
     * setFPS()
     * This method sets the FPS value
     * @param x This is the new value of the FPS
     */
    public void setFPS(int x)
    {
        fpsCounter.setString(String.valueOf(x));
    }

    /**
     * draw()
     * This method overrides the draw method to draw each element in the UI overlay
     * @param renderTarget The target window to draw each element on
     * @param renderStates The texture for the element
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(hB.getHealthBarBack());
        renderTarget.draw(hB.getHealthBarFront());
        renderTarget.draw(iB.getInventoryBack());
        renderTarget.draw(iB.getInventoryFront());
        renderTarget.draw(xpB.getXPBarBack());
        renderTarget.draw(xpB.getXPBarFront());
        renderTarget.draw(map.getMiniMapFront());
        renderTarget.draw(map.getMiniMapBack());
        renderTarget.draw(fpsCounter);
    }
}
