package Main.Game.GUI;

import Main.Game.ECS.Components.Backpack;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.Mana;
import Main.Game.ECS.Components.Stats;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.GUI.GUIComponents.GUIInvectory;
import Main.Game.GUI.GUIComponents.GUIManaBar;
import Main.Game.Game;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import java.util.HashMap;

/**
 * Controls all the GUI (At the moment, that being the onscreen bars)
 * Acts like all others managers, controls an ArrayList of components defined for the manager
 * -- IS a singleton
 */
public class GUIManager implements Drawable
{

    public static GameObject GUITarget = Game.PLAYER;       //The GameObject the GUI is locked on, so will display information about this GAMEOBJECT;
    private static GUIManager GUIinstance = new GUIManager();       //Singleton pattern (use google for better understanding)
    public static GUIManager getGUIinstance() {
        return GUIinstance;
    }



    private static HashMap<GUIComponentENUM,GUIComponent> guiComponentList = new HashMap<>();       //Hashmap (Speedy List) of all the GUI Components
    static{
        //If you want to add a new GUI Component, first add it to the ENUM, then make a new class for the new element which extends the abstract class
        //finally add a new instance of the new class with its paired enum to the hashmap
        guiComponentList.put(GUIComponentENUM.INVENTORY, new GUIInvectory(GUITarget.getComponent(Backpack.class)));
        guiComponentList.put(GUIComponentENUM.HEALTHBAR,new GUIHealthBar(GUITarget.getComponent(Stats.class).getComponent(Health.class)));
        guiComponentList.put(GUIComponentENUM.MANABAR,new GUIManaBar(GUITarget.getComponent(Stats.class).getComponent(Mana.class)));
    }

    private GUIManager()
    {
        //any code that needs to be run at the start is put here
        //this should stay empty unless absolutely necessary, Message me
    }

    /**
     * Updates the Single GUI Component given by e
     * @param e the GUI Enum of the component we want to update
     */
    public void GUIUpdate(GUIComponentENUM e)
    {
        //This should be called in GameSystems if a GUI Component needs to be updated
        //for example in BackPackGameSystem, when the player picks up or drops an item, we call GUIUpdate(GUIComponentENUM.Inventory)
        //to update the inventory
        guiComponentList.get(e).update();
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        //draws all the GUI elements
        for (GUIComponentENUM g: GUIComponentENUM.values())
        {
            renderTarget.draw(guiComponentList.get(g),renderStates);
        }
    }
}

/**
 * Future Plans
 * --- We will eventually need menus
 * My Thinking is that menus will still be an ArrayList of GUI Components same as the "In Game GUI"
 * But we will give the GUIManager different modes and multiple ArrayLists
 *      Mode: In-Game
 *      ArrayList: guiComponentList
 *
 *      Mode: Settings Menu
 *      ArrayList: guiComponentListSettings
 *
 *      Mode: Talent Menu
 *      ArrayList: guiComponentListTalents
 *
 * Make an ENUM for the different Modes and a Hashmap to attach the mode to the arrayList
 * Then a function which can swap modes
 *
 * Example
 * so a input game system can record the "ESC" button being pressed, call the GUIManager.switchModesTo(GUIModes.SettingsMenu)
 * Now the GUIManager knows what mode we are on and what to display
 * Now the GUIManager should only draw and update active components (components on the arrayList on the current Mode)
 */
