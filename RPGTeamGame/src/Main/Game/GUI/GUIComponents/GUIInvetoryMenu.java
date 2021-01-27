package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Factory.Entity;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class GUIInvetoryMenu extends GUIComponent<Backpack>
{
    RectangleShape backgroundMenu = new RectangleShape();
    RectangleShape playerFrame = new RectangleShape();
    RectangleShape mainHand = new RectangleShape();

    public GUIInvetoryMenu(Backpack b)
    {
        super(b);
        backgroundMenu.setPosition(100,100);
        backgroundMenu.setFillColor(new Color(150,150,150,200));
        backgroundMenu.setSize(new Vector2f(800,800));
        Texture t = new Texture();
        try
        {
            t.loadFromFile(Paths.get("/home/cowleyb/h-drive/SCC210BattleToTheCore-ECSTesting/RPGTeamGame/Assets" + File.separator + "EntityTextures" + File.separator + "StatMenu2.png"));
        }
        catch(Exception e)
        {

        }
        backgroundMenu.setTexture(t);
        playerFrame.setPosition(590,300);
        playerFrame.setSize(new Vector2f(300,300));
        playerFrame.setTexture(TextureMap.TEXTUREMAP.get(Entity.PLAYER.textureString));
        playerFrame.setTextureRect(new IntRect(32, 0,32,32));
        mainHand.setPosition(540,530);
        mainHand.setSize(new Vector2f(200,200));


    }

    @Override
    public void update()
    {
        if (getT().getObjectsINBACKPACK().size() > 0)
        {
            mainHand.setTexture(TextureMap.TEXTUREMAP.get(getT().getObjectsINBACKPACK().get(0).getComponent(TextureComponent.class).textureString));
            mainHand.setRotation(-100);
            //mainHand.setTextureRect(new IntRect(32, 0,32,32));
        }
        else
        {
            mainHand.setTexture(null);
        }

    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(backgroundMenu);
        renderTarget.draw(playerFrame);
        if (mainHand.getTexture() != null)
        {
            renderTarget.draw(mainHand);
        }

    }
}
