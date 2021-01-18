package Main.Game.GUI.GUIComponents;

import Main.Game.GUI.GUIComponent;

import java.util.ArrayList;

public enum GUIModeEnum
{
    GAME(GUIHealthBar.class,GUIManaBar.class,GUIXPBar.class,GUIInvectory.class)
    ,MENU(GUIInvectory.class);
    private final Class<? extends GUIComponent>[] componentList;

    private GUIModeEnum(Class<? extends GUIComponent>... screenComponents)
    {
        componentList = screenComponents;
    }


}

