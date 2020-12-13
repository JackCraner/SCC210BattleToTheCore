package Main.Shader;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class ShaderController
{
    Shader mapShader = new Shader();
    public ShaderController()
    {
        try
        {
            mapShader.loadFromFile(Paths.get("Assets\\Vert2.vert"), Paths.get("Assets\\Frag2.frag"));
        }
        catch(Exception E){
            System.out.println(E);
        }



    }

    public Shader createShader(ConstTexture t)
    {
        mapShader.setParameter("texture", t);
        mapShader.setParameter("resolution", 1000, 1000);
        mapShader.setParameter("ambientData", 0.3f, 0.3f, 0.8f, 0.3f);
        mapShader.setParameter("lightData", 1.0f, 0.8f, 0.2f, 2);
        mapShader.setParameter("lightSize", 0.4f, 0f);
        mapShader.setParameter("position",0.5f, 0.5f);
        return mapShader;
    }




}
