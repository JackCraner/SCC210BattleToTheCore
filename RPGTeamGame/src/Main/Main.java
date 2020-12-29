package Main;

import Main.Game.Game;
import Main.StartMenu.StartMenuController;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;

public class Main {

    public static void main(String[] args)
    {
         RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"CoreControl");
         StartMenuController newStartMenu = new StartMenuController(window);
         Game newGame = new Game(window);
    }
}
