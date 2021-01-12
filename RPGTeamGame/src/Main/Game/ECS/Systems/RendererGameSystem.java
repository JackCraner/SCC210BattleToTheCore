package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.EventManager;
import Main.Game.ECS.Communication.Events.GameEventTypes;
import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private Layer[] graphicLayers = {new Layer(),new Layer(), new Layer()};
    private RenderTexture screenTexture = new RenderTexture();
    private RenderStates rS;
    private Sprite screenSprite = new Sprite();
    private VertexArray backGround = new VertexArray(PrimitiveType.QUADS);

    private RendererGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(TextureComponent.class, Position.class, TransformComponent.class));
        try
        {
            screenTexture.create(Game.WINDOWSIZE, Game.WINDOWSIZE);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {

        // currently we calculate and draw every frame
        // need an event that something has moved (MovementSystem) to order this system to calculate the frame and draw
        // otherwise we just draw (big performance boost)

        //System.out.println("Num Objects: " +getGameObjectList().transform.getSize()());
        backGround.clear();

        //buildVertexArray();
        Vector2f curPos;
        TransformComponent transform;
        TextureComponent texture;
        for(GameObject g: getGameObjectList())
        {
            curPos = g.getComponent(Position.class).getPosition();
            transform = g.getComponent(TransformComponent.class);
            texture = g.getComponent(TextureComponent.class);

            if (texture.layer - 1 < 0)
            {

                backGround.add(new Vertex(curPos, new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + transform.getSize().y), new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y + transform.getSize().y), new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, +Blueprint.TEXTURESIZE.x)));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y), new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, +Blueprint.TEXTURESIZE.x)));



            }
            else
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(new Vector2f(curPos.x, curPos.y));
                s.setSize(transform.getSize());
                s.setRotation(transform.getRotation());
                if ((g.getBitmask() & BitMasks.getBitMask(Movement.class)) !=0)
                {
                    if(g.getComponent(Movement.class).getIsFacingRight())
                    {
                        s.setScale(-1,1);
                    }
                }
                s.setTexture(TextureMap.TEXTUREMAP.get(texture.textureString));
                if (texture.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(texture.tileMapLocation * (int)transform.getSize().x, 0,(int)transform.getSize().x,(int)transform.getSize().y));
                }

                graphicLayers[texture.layer - 1].addDrawable(s);

                if((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0 && g.getComponent(Backpack.class).getObjectsINBACKPACK().size() > 0)
                {
                    RectangleShape s1 = new RectangleShape();
                    s1.setPosition(new Vector2f(curPos.x-(g.getComponent(Movement.class).getIsFacingRight() ? -10:10), curPos.y));
                    s1.setSize(transform.getSize());
                    s1.setRotation(g.getComponent(Backpack.class).getObjectsINBACKPACK().get(0).getComponent(TransformComponent.class).getRotation());
                    TextureComponent mainHandTexture = g.getComponent(Backpack.class).getObjectsINBACKPACK().get(0).getComponent(TextureComponent.class);
                    s1.setTexture(TextureMap.TEXTUREMAP.get(mainHandTexture.textureString));
                    if (mainHandTexture.tileMapLocation >= 0)
                    {
                        s1.setTextureRect(new IntRect(mainHandTexture.tileMapLocation * (int)transform.getSize().x, 0,(int)transform.getSize().x,(int)transform.getSize().y));
                    }

                    graphicLayers[mainHandTexture.layer - 1].addDrawable(s1);
                }
            }

        }







        screenTexture.clear();
        screenTexture.draw(backGround,new RenderStates(TextureMap.TEXTUREMAP.get(Entity.BLOCK.textureString)));
        for (Layer layer: graphicLayers)
        {
            screenTexture.draw(layer);
        }

        Camera.cameraInstance().camerView.setCenter(Game.PLAYER.getComponent(Position.class).getPosition());


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
    ArrayList<RectangleShape> drawablesInLayer = new ArrayList<>();
    public Layer()
    {

    }
    public void addDrawable(RectangleShape d)
    {
        drawablesInLayer.add(d);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for(RectangleShape d: drawablesInLayer)
        {
            Transform t = new Transform();
            t = Transform.rotate(t,d.getRotation(),d.getPosition().x + d.getSize().x/2,d.getPosition().y+d.getSize().y/2);
            t = Transform.scale(t,d.getScale(),new Vector2f(d.getPosition().x + d.getSize().x/2,d.getPosition().y+d.getSize().y/2));
            d.setRotation(0);
            d.setScale(1,1);
            RenderStates r = new RenderStates(renderStates,t);
            renderTarget.draw(d,r);
        }
        drawablesInLayer.clear();
    }
}

