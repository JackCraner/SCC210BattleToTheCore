package Main.GUI;

import Main.ForeGround.Entities.Player;
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
        fpsCounter.setPosition(new Vector2f((p.getPosition().x + (p.getpView().getSize().x)/2) - fpsCounter.getLocalBounds().width - 20,(p.getPosition().y - (p.getpView().getSize().y)/2)));
    }

    public void updateGUI(Player p)
    {
        hB.updateHealthBarPosition();
        fpsCounter.setPosition(new Vector2f((p.getPosition().x + (p.getpView().getSize().x)/2) - fpsCounter.getLocalBounds().width - 20,(p.getPosition().y - (p.getpView().getSize().y)/2)));
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
