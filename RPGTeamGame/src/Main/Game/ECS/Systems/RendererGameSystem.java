package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Size;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EntityID;
import Main.Game.Level;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private HashMap<Byte,Texture> textureMap = new HashMap<>();

    private RendererGameSystem()
    {

        for(Byte a = 0; a < EntityID.ENTITYTextureStringLIST.length; a++)
        {

            textureMap.put(a, EntityID.getTexture(EntityID.ENTITYTextureStringLIST[a]));
        }
    }


    @Override
    public ArrayList<Class<? extends Component>> systemComponentRequirements() {
        ArrayList<Class<? extends Component>> c = new ArrayList<>();

        c.add(Position.class);
        c.add(Size.class);          //the order of the requirements defines the layout of components in the array
        c.add(TextureComponent.class);              //thus getComponentArrayList().get(i)[0] will always be position
                                                    //and getComponentArrayList().get(i)[1] will be SpriteController
        return c;
    }

    @Override
    public void update()
    {
        VertexArray backGround = new VertexArray(PrimitiveType.QUADS);
        Layer graphicalLayer[] = new Layer[] {new Layer(), new Layer(), new Layer()};



        for(Component[] cA: getComponentArrayList())
        {
            Vector2f curPos = ((Position)cA[0]).position;
            Vector2f size = ((Size)cA[1]).size;
            TextureComponent b = ((TextureComponent)cA[2]);

            if (b.layer - 1 < 0)
            {

                backGround.add(new Vertex(curPos,new Vector2f(Blueprint.blockSize.x *b.tileMapLocation,0)));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + Blueprint.blockSize.y) ,new Vector2f(Blueprint.blockSize.x *b.tileMapLocation + Blueprint.blockSize.x,0)));
                backGround.add(new Vertex(new Vector2f(curPos.x + Blueprint.blockSize.x, curPos.y + Blueprint.blockSize.y),new Vector2f(Blueprint.blockSize.x *b.tileMapLocation+ Blueprint.blockSize.x,+ Blueprint.blockSize.x)));
                backGround.add(new Vertex(new Vector2f(curPos.x + Blueprint.blockSize.x, curPos.y),new Vector2f(Blueprint.blockSize.x *b.tileMapLocation,+ Blueprint.blockSize.x)));



            }
            else
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(curPos);
                s.setSize(size);
                s.setTexture(textureMap.get(b.textureID));
                if (b.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(b.tileMapLocation * (int)size.x, 0,(int)size.x,(int)size.y));
                }

                graphicalLayer[b.layer - 1].addDrawable(s);
            }

        }

        Level.getLevel().getWindow().draw(backGround,new RenderStates(textureMap.get((byte) 0)));

        for (Layer l: graphicalLayer)
        {
            Level.getLevel().getWindow().draw(l);
        }

        //Player is always index 0 on the list of Objects

        Camera.cameraInstance().camerView.setCenter(((Position)getComponentArrayList().get(0)[0]).position);
        Level.getLevel().getWindow().setView(Camera.cameraInstance().camerView);
    }

    @Override
    public void update(Event event)
    {

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

