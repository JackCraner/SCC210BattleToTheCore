package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.EventManager;
import Main.Game.ECS.Communication.Events.GameEventTypes;
import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Size;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private HashMap<String,Texture> textureMap = new HashMap<>();
    private RenderTexture screenTexture = new RenderTexture();
    private RenderStates rS;
    private Sprite screenSprite = new Sprite();
    private VertexArray backGround = new VertexArray(PrimitiveType.QUADS);

    private RendererGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(TextureComponent.class, Position.class, Size.class));
        try
        {
            screenTexture.create(Game.WINDOWSIZE, Game.WINDOWSIZE);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        for(Entity entity: Entity.values()) {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + entity.textureString));
            } catch (Exception e) {
                System.out.println(e);
            }
            textureMap.put(entity.textureString, t);
        }

    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {

        // currently we calculate and draw every frame
        // need an event that something has moved (MovementSystem) to order this system to calculate the frame and draw
        // otherwise we just draw (big performance boost)

        //System.out.println("Num Objects: " +getGameObjectList().size());

        Layer graphicalLayer[] = new Layer[] {new Layer(), new Layer()};

        backGround.clear();

        //buildVertexArray();
        Vector2f curPos;
        Vector2f size;
        TextureComponent t;
        for(GameObject g: getGameObjectList())
        {
            curPos = g.getComponent(Position.class).position;
            size = g.getComponent(Size.class).size;
            t = g.getComponent(TextureComponent.class);

            if (t.layer - 1 < 0)
            {

                backGround.add(new Vertex(curPos, new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + size.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation + Blueprint.TEXTURESIZE.x, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x + size.x, curPos.y + size.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation + Blueprint.TEXTURESIZE.x, +Blueprint.TEXTURESIZE.x)));
                backGround.add(new Vertex(new Vector2f(curPos.x + size.x, curPos.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation, +Blueprint.TEXTURESIZE.x)));



            }
            else
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(curPos);
                s.setSize(size);
                s.setTexture(textureMap.get(t.textureString));
                if (t.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(t.tileMapLocation * (int)size.x, 0,(int)size.x,(int)size.y));
                }

                graphicalLayer[t.layer - 1].addDrawable(s);
            }

        }







        screenTexture.clear();
        screenTexture.draw(backGround,new RenderStates(textureMap.get(Entity.BLOCK.textureString)));
        for (Layer l: graphicalLayer)
        {
            screenTexture.draw(l);
        }

        Camera.cameraInstance().camerView.setCenter(Game.PLAYER.getComponent(Position.class).position);


        screenTexture.setView(Camera.cameraInstance().camerView);
        screenTexture.display();
        screenSprite.setTexture(screenTexture.getTexture());


        EventManager.getEventManagerInstance().addEvent(new GameEvent<>(screenSprite.getTexture(), GameEventTypes.TextureEvent));
        if (gameEvents.size() == 0)
        {
            Game.getGame().getWindow().draw(screenSprite);
        }
        else
        {
            rS = new RenderStates((Shader)gameEvents.get(0).getData());
            Game.getGame().getWindow().draw(screenSprite,rS);
        }


        //Level.getLevel().getWindow().draw(backGround,new RenderStates(textureMap.get((byte) 0)));
        //Player is always index 0 on the list of Objects



    }




    public static RendererGameSystem getSystemInstance() {
        return systemInstance;
    }


}

class Layer implements Drawable
{
    ArrayList<Drawable> drawablesInLayer = new ArrayList<>();
    public Layer()
    {

    }

    public ArrayList<Drawable> getDrawablesInLayer() {
        return drawablesInLayer;
    }
    public void addDrawable(Drawable d)
    {
        drawablesInLayer.add(d);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for(Drawable d: drawablesInLayer)
        {
            renderTarget.draw(d,renderStates);
        }
    }
}

