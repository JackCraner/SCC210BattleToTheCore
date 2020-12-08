package Main.GUI;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class GUIController implements Drawable
{
    Text fpsCounter;
    Font textFont = new Font();
    HealthBar hB;
    public GUIController()
    {
    }

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
        hB.takeDamage(40);
        hB.updateHealthBarPosition();
        fpsCounter = new Text("HI", textFont, 100);
        fpsCounter.setPosition(new Vector2f(Game.windowSize-fpsCounter.getGlobalBounds().width,0));
    }

    public void updateGUI()
    {
        hB.updateHealthBarPosition();
        fpsCounter.setPosition(new Vector2f(Game.windowSize-fpsCounter.getGlobalBounds().width,0));
        System.out.println("counter: " + fpsCounter.getPosition());
    }

    public void setFPS(int x)
    {
        fpsCounter.setString(String.valueOf(x));
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(hB.getHealthBarBack());
        renderTarget.draw(hB.getHealthBarFront());
        renderTarget.draw(fpsCounter);
    }
}
