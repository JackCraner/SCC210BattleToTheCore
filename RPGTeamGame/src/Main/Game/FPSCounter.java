package Main.Game;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class FPSCounter
{

    Font textFont = new Font();
    Text fpsCounter;


    public FPSCounter()
    {

        try
        {
            textFont.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator +"LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println("Font not found");
        }
        fpsCounter = new Text("HI", textFont, 100);     //Fps font and size
        fpsCounter.setPosition(new Vector2f(700,700));

    }

    public Text getFpsCounter() {
        return fpsCounter;
    }
    public void setFPS(int x)
    {
        fpsCounter.setString(String.valueOf(x));
    }

}
