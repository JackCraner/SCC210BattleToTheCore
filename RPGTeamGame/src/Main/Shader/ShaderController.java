package Main.Shader;

import Main.ForeGround.Entities.Player;
import Main.ForeGround.Interfaces.Illuminator;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
public class ShaderController
{
    Shader mapShader = new Shader();
    public ShaderController()
    {
        try
        {
            mapShader.loadFromFile(Paths.get("Assets" + File.separator + "Vert2.vert"), Paths.get("Assets" + File.separator + "Frag2.frag"));
        }
        catch(Exception E){
            System.out.println(E);
        }



    }

    public Shader createShader(ConstTexture t, ArrayList<Illuminator> lightList, Player p)
    {
        int maxNumLights = 28;
        mapShader.setParameter("texture", t);   //gives main texture to shader
        mapShader.setParameter("resolution", 1000, 1000);
        mapShader.setParameter("ambientData", 0.3f, 0.3f, 1f, 0f);
        //mapShader.setParameter("lights[]", 0);
        int counter = 0;
        int counter2= 0;
        for (Illuminator i: lightList)
        {
            if (i.getLight().getOnScreen(p) && counter < maxNumLights) {
                mapShader.setParameter("lights[" + counter + "].position", i.getLight().convertPositionToScreen(p));
                mapShader.setParameter("lights[" + counter + "].size", i.getLight().getSize());
                mapShader.setParameter("lights[" + counter + "].intensity", i.getLight().getIntensity());
                mapShader.setParameter("lights[" + counter + "].rgbData", i.getLight().getRgbData());
                counter++;
            }
            counter2 ++;
        }
        //Bug that makes lights act super wierd because the lights array isnt cleared between frames
        //super shit way of clearing lights array below, needs update

        for (int i = counter; i < maxNumLights; i++)
        {
            mapShader.setParameter("lights[" + counter + "].position", 0,0);
            mapShader.setParameter("lights[" + counter + "].size", 0);
            mapShader.setParameter("lights[" + counter + "].intensity",0);
            mapShader.setParameter("lights[" + counter + "].rgbData", 0,0,0);
        }


        //System.out.println(counter2 + "     onScreen  " + counter);
        //mapShader.setParameter("lightSize", 0.4f, 0f);
        //mapShader.setParameter("position",0.5f, 0.5f);      //puts the lights position at the center of the screen)
        /**
         * Issue
         * lights position has to be between 0 - 1 as it is bound by the screen
         * Players global position can be anything so in future need a way to convert between them
         */
        return mapShader;
    }




}
