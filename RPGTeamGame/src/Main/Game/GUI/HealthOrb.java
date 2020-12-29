package Main.Game.GUI;

import Main.Game.Player.Player;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class HealthOrb
{
    //spacing and items should scale with viewSize
    //Static Colors = BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
    Color HP = new Color(254, 22, 22);         //The colour of the current Health
    Color HPLost = new Color(209, 17, 17);     //The colour of the Health Lost
    CircleShape front;                               //The front element of the Health Orb
    CircleShape back;                                //The back element of the Health Orb
    Texture frontT = new Texture();
    Vector2f healthOrbLoc;                             //The vector2f of the Health Orb
    float healthOrbSize = 70;
    float totalHealth = 100;                            //The value of the total Health
    float currentHealth = 100;                          //The value of the current Health
    Player p;                                           //Object reference of player class
    Shader healthShader;
    /**
     * HealthOrb() constructor
     * This constructs the HealthOrb element with the current length and size of the window
     * The front and back element are set a size according to the window size
     * Both elements are set a color as well
     * @param p The player
     */
    public HealthOrb(Player p)
    {
        this.p = p;
        healthOrbLoc = new Vector2f((float)(Game.windowSize-870), 130); //length, width of bar
        front = new CircleShape(healthOrbSize,100);
        back = new CircleShape(healthOrbSize,100);
        try
        {
            frontT.loadFromFile(Paths.get("Assets" + File.separator +"HealthOrb.png"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        //front.setTexture(frontT);
        front.setFillColor(HPLost);
        back.setFillColor(HP);

    }

    /**
     * updateHealthOrbPosition()
     * This method updates the HealthOrb according to the current window size
     */
    public void updateHealthOrbPosition()
    {
        front.setPosition(50, Game.windowSize - healthOrbLoc.y - 30);
        back.setPosition(50, Game.windowSize - healthOrbLoc.y - 30);
    }
    
    public RenderStates getHealthShader()
    {
        healthShader = new Shader();
        try
        {
            healthShader.loadFromFile(Paths.get("Assets\\Ripple2.vert"), Paths.get("Assets\\Ripple.frag"));
        }
        catch(Exception E){
            System.out.println(E);
        }
        ConstTexture t = front.getTexture();
        healthShader.setParameter("time", 5);
        healthShader.setParameter("tex", t);        //texture is null as its just a colour
        healthShader.setParameter("tex2", t);
        RenderStates rS = new RenderStates(healthShader);
        return rS;
    }
    /**
     * getHealthOrbFront()
     * This method returns the front of the HealthOrb element
     * @return The front element
     */
    public CircleShape getHealthOrbFront()
    {
        
        return front;
    }

    /**
     * getHealthBarBack()
     * This method returns the back of the HealthOrb element
     * @return The back element
     */
    public CircleShape getHealthOrbBack()
    {
        return back;
    }

    /**
     * takeDamage()
     * This method removes health from the HealthOrb
     * @param x The amount of Health lost
     */
    public void takeDamage(int x)
    {
        currentHealth -=x;
        //front.setSize(new Vector2f(HealthOrbSize.x,HealthOrbSize.y* (currentHealth/100)));
    }
}
