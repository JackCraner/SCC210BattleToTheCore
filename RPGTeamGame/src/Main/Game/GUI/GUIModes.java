package Main.Game.GUI;


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.*;

interface HasChildren
{
    Set<Enum<?>> children();
}
enum GUIModes implements HasChildren {
    GAME,
    MENU;
    Set<Enum<?>> children;

    @Override
    public Set<Enum<?>> children() {
        return children != null ? Collections.unmodifiableSet(children) : null;
    }

    enum GAME
    {
        HEALTHBAR,MANABAR,XPBAR,INVENTORY
    }
    static {
        GAME.children = new LinkedHashSet<Enum<?>>();
        GAME.children.addAll(EnumSet.allOf(GAME.class));
    }
    enum MENU
    {
        INVENTORY
    }
    static {
        MENU.children = new LinkedHashSet<Enum<?>>();
        MENU.children.addAll(EnumSet.allOf(GAME.class));
    }


}

