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
                t.loadFromFile(Paths.get("/home/cowleyb/h-drive/SCC210BattleToTheCore-ECSTesting/RPGTeamGame/Assets" + File.separator + "EntityTextures" + File.separator + entity.textureString));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(entity.textureString, t);
        }
    }

}
