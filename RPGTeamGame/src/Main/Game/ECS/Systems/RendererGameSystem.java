package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.Animation;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private Layer[] graphicLayers = {new Layer(),new Layer(), new Layer(), new Layer()};
    public RenderTexture screenTexture = new RenderTexture();
    private RenderStates rS;
    public Sprite screenSprite = new Sprite();
    private VertexArray backGround = new VertexArray(PrimitiveType.QUADS);
    private Font font= new Font();

    private static Vector2f standardRenderSize = new Vector2f(32,32);
    private RendererGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(TextureComponent.class, Position.class, TransformComponent.class));
        try
        {
            font.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
            screenTexture.create(Game.WINDOWSIZE, Game.WINDOWSIZE);
            screenTexture.setView(Camera.cameraInstance().camerView);
        }
        catch (Exception e)
        {
            System.out.println("Render System: " + e);
        }

    }
    @Override
    public void update(float dt)
    {

        // currently we calculate and draw every frame
        // need an event that something has moved (SpeedSystem) to order this system to calculate the frame and draw
        // otherwise we just draw (big performance boost)

        //System.out.println("Num Objects: " +getGameObjectList().transform.getSize()());


        //buildVertexArray();
        Vector2f curPos;
        TransformComponent transform;
        TextureComponent texture;
        if (Game.getGame().isRunning)
        {
            backGround.clear();
            RendererGameSystem.getSystemInstance().screenTexture.clear(new Color(43,39,39));
        }
        for(GameObject g: getGameObjectList())
        {
            curPos = g.getComponent(Position.class).getPosition();
            transform = g.getComponent(TransformComponent.class);
            texture = g.getComponent(TextureComponent.class);

            if (texture.texturetype == TextureTypes.BLOCK)
            {
                //// SO MANY VECTORS :CCCCC
                Vertex[] vertexArray = new Vertex[4];
                Vector2f[] texCordArray ={
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, 0),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, 0),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, +Blueprint.TEXTURESIZE.x),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, +Blueprint.TEXTURESIZE.x)
                };
                Vector2f[] texCords = new Vector2f[4];
                if (transform.getRotation() == 90)
                {

                   texCords[0] = texCordArray[1];
                   texCords[1] = texCordArray[2];
                   texCords[2] = texCordArray[3];
                   texCords[3] = texCordArray[0];

                }
                if (transform.getRotation() == 95)
                {

                    texCords[0] = texCordArray[0];
                    texCords[1] = texCordArray[3];
                    texCords[2] = texCordArray[2];
                    texCords[3] = texCordArray[1];

                }
                else if (transform.getRotation() == 180)
                {

                    texCords[0] = texCordArray[3];
                    texCords[1] = texCordArray[2];
                    texCords[2] = texCordArray[1];
                    texCords[3] = texCordArray[0];

                }
                else if (transform.getRotation() == 185)
                {

                    texCords[0] = texCordArray[2];
                    texCords[1] = texCordArray[1];
                    texCords[2] = texCordArray[0];
                    texCords[3] = texCordArray[3];

                }
                else if (transform.getRotation() == 270)
                {

                    texCords[0] = texCordArray[2];
                    texCords[1] = texCordArray[3];
                    texCords[2] = texCordArray[0];
                    texCords[3] = texCordArray[1];

                }
                else
                {
                    texCords[0] = texCordArray[0];
                    texCords[1] = texCordArray[1];
                    texCords[2] = texCordArray[2];
                    texCords[3] = texCordArray[3];
                }
                backGround.add(new Vertex(curPos,texCords[0]));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + transform.getSize().y),texCords[1]));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y + transform.getSize().y),texCords[2]));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y),texCords[3]));






            }
            if(texture.texturetype == TextureTypes.RECTANGLE)
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(new Vector2f(curPos.x, curPos.y));
                s.setSize(standardRenderSize);
                s.setRotation(transform.getRotation());
                s.setScale(transform.getScale().x,transform.getScale().y);
                s.setTexture(TextureMap.TEXTUREMAP.get(texture.textureString));
                if (BitMasks.checkIfContains(g.getBitmask(), Animation.class))
                {
                    Animation anim = g.getComponent(Animation.class);
                    texture.tileMapLocation = (byte)(anim.spriteSheetAnimation + anim.spriteSheetDirection);
                }
                if (texture.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(texture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                }

                graphicLayers[texture.layer-1].addRectangle(s);

                if((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0 && g.getComponent(Backpack.class).getObjectsINBACKPACK().size() > 0 && g.getComponent(Backpack.class).getCanUseItems())
                {
                    RectangleShape s1 = new RectangleShape();
                    s1.setPosition(new Vector2f(curPos.x-(transform.isFacingRight()? -10:10), curPos.y));
                    s1.setSize(standardRenderSize);
                    s1.setRotation(g.getComponent(Backpack.class).getObjectsINBACKPACK().get(0).getComponent(TransformComponent.class).getRotation());
                    TextureComponent mainHandTexture = g.getComponent(Backpack.class).getObjectsINBACKPACK().get(0).getComponent(TextureComponent.class);
                    s1.setTexture(TextureMap.TEXTUREMAP.get(mainHandTexture.textureString));

                    if (mainHandTexture.tileMapLocation >= 0)
                    {
                        s1.setTextureRect(new IntRect(mainHandTexture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                    }

                    graphicLayers[mainHandTexture.layer - 1].addRectangle(s1);
                }
                //Test
                if(BitMasks.checkIfContains(g.getBitmask(), Armor.class) && g.getComponent(Armor.class).getHelmet() != null)
                {
                    GameObject helmet = g.getComponent(Armor.class).getHelmet();
                    RectangleShape s1 = new RectangleShape();
                    s1.setPosition(new Vector2f(curPos.x, curPos.y));
                    s1.setSize(standardRenderSize);
                    TextureComponent helmetTexture = helmet.getComponent(TextureComponent.class);
                    s1.setTexture(TextureMap.TEXTUREMAP.get(helmetTexture.textureString));
                    if (BitMasks.checkIfContains(helmet.getBitmask(),Animation.class))
                    {
                        Animation anim = helmet.getComponent(Animation.class);
                        helmetTexture.tileMapLocation = (byte)(anim.spriteSheetAnimation + anim.spriteSheetDirection);
                    }
                    if (helmetTexture.tileMapLocation >= 0)
                    {
                        s1.setTextureRect(new IntRect(helmetTexture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                    }

                    graphicLayers[helmetTexture.layer - 1].addRectangle(s1);
                }
            }
            if (texture.texturetype == TextureTypes.TEXT)
            {
                Text t = new Text(texture.textureString,font,(int)transform.getSize().x);
                t.setColor(Color.YELLOW);
                t.setPosition(new Vector2f(curPos.x, curPos.y));
                graphicLayers[texture.layer - 1].addText(t);
            }
            if(texture.texturetype == TextureTypes.PARTICLE)
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(curPos);
                s.setSize(transform.getSize());
                try {
                    int[] rgbData =  Arrays.asList(texture.textureString.split(",")).stream().mapToInt(Integer::parseInt).toArray();
                    s.setFillColor(new Color(rgbData[0],rgbData[1],rgbData[2]));
                }
                catch (Exception e)
                {
                    System.out.println("Color Not Found");
                }
                graphicLayers[texture.layer - 1].addRectangle(s);


            }

        }




        Camera.cameraInstance().camerView.setCenter(Game.PLAYER.getComponent(Position.class).getPosition());
        screenTexture.setView(Camera.cameraInstance().camerView);


        screenTexture.draw(backGround,new RenderStates(TextureMap.TEXTUREMAP.get(Entity.BLOCK.textureString)));
        for (Layer layer: graphicLayers)
        {
            screenTexture.draw(layer);
        }




        screenTexture.display();
        screenSprite.setTexture(screenTexture.getTexture());

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
    ArrayList<Text> textInLayer = new ArrayList<>();
    public Layer()
    {

    }
    public void addRectangle(RectangleShape d)
    {
        drawablesInLayer.add(d);
    }
    public void addText(Text t)
    {
        textInLayer.add(t);
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
        for (Text t: textInLayer)
        {
            renderTarget.draw(t);
        }
        if (Game.getGame().isRunning)
        {
            drawablesInLayer.clear();
            textInLayer.clear();
        }

    }
}

