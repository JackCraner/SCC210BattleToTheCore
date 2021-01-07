package Main.Game.GUI;

import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.io.File;
import java.nio.file.Paths;

public class GUIController implements Drawable
{   
    //All the UI elements are Singletons
    private static GUIController guicontroller = new GUIController();
    private static HealthOrb HO = HealthOrb.getHealthOrb();             //Heath Orb
    private static Inventory IB = Inventory.getInventory();             //Inventory
    private static XPBar XPB = XPBar.getXPBar();                        //XP bar
    private static MagicOrb MO = MagicOrb.getMagicOrb();                //Magic bar
    private static MiniMap map = MiniMap.getMiniMap();                  //Mini Map

    public static GUIController getGuicontroller(){
        return guicontroller;
    }

    Text fpsCounter;                //Displays the number of frames per second
    Font textFont = new Font();     //The font for Displaying text
    public GUIController()
    {
    }

    /**
     * initialiseGUI()
     * This method initialises the UI overlay of the game and all included elements
     */
    public void initializeGUI()
    {
        try
        {
            textFont.loadFromFile(Paths.get("Assets\\LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println("Font not found");
        }
        HO.InitialiseHealthOrb();
        MO.InitialiseMagicOrb();
        IB.InitialiseInventory(6);
        XPB.InitialiseXPBar(IB.getTotalSlots());
        map.InitialiseMiniMap();
        HO.takeDamage(40);
        XPB.AddXP(2);
        HO.updateHealthOrbPosition();
        MO.updateMagicOrbPosition();
        IB.updateInventoryPosition();
        XPB.updateXPBarPosition();
        map.updateMiniMapPosition();
        fpsCounter = new Text("HI", textFont, 100);     //Fps font and size
        fpsCounter.setPosition(new Vector2f(Game.getGame().getWindow().getSize().x-970,30));
    }

    /**
     * updateGUI()
     * This method updates all the elements in the uI according to the current window size
     */
    public void updateGUI()
    {
        HO.updateHealthOrbPosition();
        MO.updateMagicOrbPosition();
        IB.updateInventoryPosition();
        XPB.updateXPBarPosition();
        map.updateMiniMapPosition();
        fpsCounter.setPosition(new Vector2f(Game.getGame().getWindow().getSize().x-970,30));
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
        renderTarget.draw(HO.getHealthOrbBack());
        renderTarget.draw(HO.getHealthOrbFront());

        renderTarget.draw(IB.getInventoryBack());
        for (int i = 0; i < IB.getTotalSlots(); i++) {
            renderTarget.draw(IB.getInventorySlots(i));
        }

        renderTarget.draw(XPB.getXPBarBack());
        for (int i = 0; i < XPB.getTotalXPSlots(); i++) {
            renderTarget.draw(XPB.getXPBarSlot(i));
        }
        renderTarget.draw(MO.getMagicOrbBack());
        renderTarget.draw(MO.getMagicOrbFront());
        renderTarget.draw(map.getMiniMapFront());
        renderTarget.draw(map.getMiniMapBack());
        renderTarget.draw(fpsCounter);
    }
}
