package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Size;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EntityID;
import Main.Game.Game;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private HashMap<Byte,Texture> textureMap = new HashMap<>();
    private RenderTexture screenTexture = new RenderTexture();
    private Sprite screenSprite = new Sprite();
    private static int totalBlocks = CellularAutomata.CHUNKSIZEBLOCKSX * CellularAutomata.CHUNKSIZEBLOCKSY;
    private VertexArray backGround = new VertexArray(PrimitiveType.QUADS);
    Vector2i renderDistanceinBlocks = new Vector2i((int)Math.ceil((Camera.cameraInstance().camerView.getSize().x/2)/Blueprint.BLOCKSIZE.x), (int)Math.ceil((Camera.cameraInstance().camerView.getSize().y/2)/Blueprint.BLOCKSIZE.y));

    private RendererGameSystem()
    {

        for(Byte a = 0; a < EntityID.ENTITYTextureStringLIST.length; a++)
        {

            textureMap.put(a, EntityID.getTexture(EntityID.ENTITYTextureStringLIST[a]));
        }
        try
        {
            screenTexture.create(Game.WINDOWSIZE,Game.WINDOWSIZE);
        }
        catch(Exception e)
        {
            System.out.println(e);
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

        // currently we calculate and draw every frame
        // need an event that something has moved (MovementSystem) to order this system to calculate the frame and draw
        // otherwise we just draw (big performance boost)

       //System.out.println("Num Objects: " + getComponentArrayList().size());

        Layer graphicalLayer[] = new Layer[] {new Layer(), new Layer(), new Layer()};

       // backGround.clear();

        buildVertexArray();
        for(int a = totalBlocks ; a < getComponentArrayList().size();a++)
        {
            Vector2f curPos = ((Position)getComponentArrayList().get(a)[0]).position;
            Vector2f size = ((Size)getComponentArrayList().get(a)[1]).size;
            TextureComponent b = ((TextureComponent)getComponentArrayList().get(a)[2]);



            float distanceBetween = Math.max(Math.abs(Game.PLAYER.getComponent(Position.class).position.x - curPos.x), Math.abs(Game.PLAYER.getComponent(Position.class).position.y - curPos.y));
            if (distanceBetween < (Camera.cameraInstance().camerView.getSize().x/2) + 100)
            {
                if (b.layer - 1 < 0)
                {




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

        }



        screenTexture.clear(Color.WHITE);
        screenTexture.draw(backGround,new RenderStates(textureMap.get((byte) 0)));
        for (Layer l: graphicalLayer)
        {
            screenTexture.draw(l);
        }
        Camera.cameraInstance().camerView.setCenter(Game.PLAYER.getComponent(Position.class).position);
        screenTexture.setView(Camera.cameraInstance().camerView);
        screenTexture.display();
        screenSprite.setTexture(screenTexture.getTexture());
        Game.getGame().getWindow().draw(screenSprite);

        //Level.getLevel().getWindow().draw(backGround,new RenderStates(textureMap.get((byte) 0)));
        //Player is always index 0 on the list of Objects



    }
    public void buildVertexArray()
    {
        int rendersizeX = (int)(Camera.cameraInstance().camerView.getSize().x/Blueprint.BLOCKSIZE.x) + 3;
        int rendersizeY =  (int)(Camera.cameraInstance().camerView.getSize().y/Blueprint.BLOCKSIZE.y) + 3;


        // items in the componentArray of index 0 to CellularAutomata.CHUNKBLOCKX * CellularAutomata.CHUNKBLOCKY are blocks
        backGround.clear();
        ArrayList<Component[]> tempList = getComponentArrayList();
        Vector2i topLeftBlock = new Vector2i((int) (Game.PLAYER.getComponent(Position.class).position.x / Blueprint.BLOCKSIZE.x) - renderDistanceinBlocks.x, (int) (Game.PLAYER.getComponent(Position.class).position.y / Blueprint.BLOCKSIZE.y) - renderDistanceinBlocks.y);
        int startPos = Math.max(topLeftBlock.x * CellularAutomata.CHUNKSIZEBLOCKSY + topLeftBlock.y, 0);
        int endposX = Math.min(startPos + (rendersizeX * CellularAutomata.CHUNKSIZEBLOCKSY), CellularAutomata.CHUNKSIZEBLOCKSX * (CellularAutomata.CHUNKSIZEBLOCKSY-1) +1);
        for (int a = startPos; a < endposX; a += CellularAutomata.CHUNKSIZEBLOCKSY)
        {
            for (int b = a; b < a + rendersizeY; b++) {
                Component[] curBlock = tempList.get(b);
                Vector2f curPos = ((Position) curBlock[0]).position;
                Vector2f size = ((Size) curBlock[1]).size;
                TextureComponent t = ((TextureComponent) curBlock[2]);
                backGround.add(new Vertex(curPos, new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + size.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation + Blueprint.TEXTURESIZE.x, 0)));
                backGround.add(new Vertex(new Vector2f(curPos.x + size.x, curPos.y + size.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation + Blueprint.TEXTURESIZE.x, +Blueprint.TEXTURESIZE.x)));
                backGround.add(new Vertex(new Vector2f(curPos.x + size.x, curPos.y), new Vector2f(Blueprint.TEXTURESIZE.x * t.tileMapLocation, +Blueprint.TEXTURESIZE.x)));

            }
        }

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

