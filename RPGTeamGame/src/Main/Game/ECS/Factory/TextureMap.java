package Main.Game.ECS.Factory;

import org.jsfml.graphics.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

public class TextureMap
{
    public static HashMap<String, Texture> TEXTUREMAP = new HashMap<>();
    static{
        for(Entity entity: Entity.values()) {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + entity.textureString));
            } catch (Exception e) {
                System.out.println(e);
            }
            TEXTUREMAP.put(entity.textureString, t);
        }
    }

}
