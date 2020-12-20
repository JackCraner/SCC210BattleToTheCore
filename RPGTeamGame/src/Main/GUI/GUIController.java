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
    HealthOrb hO;                   //Heath bar
    Inventory iB;                   //Inventory
    XPBar xpB;                      //XP bar
    MagicOrb mO;
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
        hO = new HealthOrb(p);
        mO = new MagicOrb(p);
        iB = new Inventory(p,6);
        xpB = new XPBar(p, iB.getTotalSlots());
        map = new MiniMap(p);
        hO.takeDamage(40);
        xpB.AddXP(2);
        hO.updateHealthOrbPosition();
        mO.updateMagicOrbPosition();
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
        hO.updateHealthOrbPosition();
        mO.updateMagicOrbPosition();
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

    public MiniMap getMap()
    {
        return map;
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
        renderTarget.draw(hO.getHealthOrbBack());
        renderTarget.draw(hO.getHealthOrbFront());

        renderTarget.draw(iB.getInventoryBack());
        for (int i = 0; i < iB.getTotalSlots(); i++) {
            renderTarget.draw(iB.getInventorySlots(i));
        }

        renderTarget.draw(xpB.getXPBarBack());
        for (int i = 0; i < xpB.getTotalXPSlots(); i++) {
            renderTarget.draw(xpB.getXPBarSlot(i));
        }
        renderTarget.draw(mO.getMagicOrbBack());
        renderTarget.draw(mO.getMagicOrbFront());
        renderTarget.draw(map.getMiniMapFront());
        renderTarget.draw(map.getMiniMapBack());
        renderTarget.draw(fpsCounter);
    }
}
