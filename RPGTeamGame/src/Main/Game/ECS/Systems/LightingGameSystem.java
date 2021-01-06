package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Light;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.Game;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.RejectedExecutionException;

public class LightingGameSystem extends GameSystem
{
    private static LightingGameSystem lightingGameSystem = new LightingGameSystem();
    public Shader mapShader = new Shader();
    private Texture t;
    public static LightingGameSystem getLightingGameSystem() {
        return lightingGameSystem;
    }

    private LightingGameSystem()
    {
        setBitMaskRequirement(EntityManager.getEntityManagerInstance().produceBitMask(Light.class));
        try
        {
            mapShader.loadFromFile(Paths.get("Assets" + File.separator + "Shaders" +File.separator+"Vert2.vert"), Paths.get("Assets" + File.separator  + "Shaders" + File.separator+ "Frag2.frag"));
        }
        catch(Exception E){
            System.out.println(E);
        }

    }

    @Override
    public void update()
    {
        t = (Texture)RendererGameSystem.getSystemInstance().screenTexture.getTexture();
        int maxNumLights = 28;
        mapShader.setParameter("texture", t);   //gives main texture to shader
        mapShader.setParameter("resolution", 1000, 1000);
        mapShader.setParameter("ambientData", 0.3f, 0.3f, 1f, 0f);

        int counter =0;
        for(GameObject g: getGameObjectList())
        {
            mapShader.setParameter("lights[" + counter + "].position",convertGlobalPositionToScreenPosition(g.getPosition()));
            mapShader.setParameter("lights[" + counter + "].size",g.getComponent(Light.class).size);
            mapShader.setParameter("lights[" + counter + "].intensity", g.getComponent(Light.class).intensity);
            mapShader.setParameter("lights[" + counter + "].rgbData", g.getComponent(Light.class).rgbData);
            counter ++;
            if (counter > maxNumLights)
            {
                break;
            }
        }

    }

    @Override
    public void update(Event event) {
    }

    public Vector2f convertGlobalPositionToScreenPosition(Vector2f position)
    {
        float transform = Camera.cameraInstance().camerView.getSize().x/2;
        float extra = 250f;

        Vector2f topLeft = new Vector2f(Game.PLAYER.getPosition().x - transform, Game.PLAYER.getPosition().y - transform);
        Vector2f bottomRight = new Vector2f(Game.PLAYER.getPosition().x + transform, Game.PLAYER.getPosition().y + transform);
        float lightPosY = 0 , lightPosX = 0;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            lightPosY = ((bottomRight.y - position.y) / (bottomRight.y- topLeft.y));
            lightPosX = ((topLeft.x - position.x) / (topLeft.x - bottomRight.x));
        }
        return new Vector2f(lightPosX,lightPosY);

    }

}
