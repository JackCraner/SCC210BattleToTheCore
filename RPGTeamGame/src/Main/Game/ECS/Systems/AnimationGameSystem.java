package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.Menu.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;

public class AnimationGameSystem extends GameSystem
{


    private static AnimationGameSystem systemInstance = new AnimationGameSystem();

    public static AnimationGameSystem getSystemInstance() {
        return systemInstance;
    }
    private AnimationGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class,TransformComponent.class, Animation.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            Vector2f pos = g.getComponent(Position.class).getPosition();
            TransformComponent trans = g.getComponent(TransformComponent.class);
            Animation anim = g.getComponent(Animation.class);
            Vector2i mousePos = Mouse.getPosition(Game.getGame().getWindow());
            Vector2f camPos = new Vector2f(Camera.cameraInstance().camerView.getCenter().x - (Camera.cameraInstance().camerView.getSize().x/2),Camera.cameraInstance().camerView.getCenter().y - (Camera.cameraInstance().camerView.getSize().y/2));
            //find angle
            mousePos = new Vector2i((int)camPos.x + mousePos.x, (int)camPos.y + mousePos.y);
            float angle =   (float)((Math.atan2(mousePos.y - pos.y, mousePos.x- pos.x))*180/Math.PI);
            trans.setScale(new Vector2f(1,1));
            if (angle<45 && angle > -45)
            {
                anim.spriteSheetDirection = (byte)1*3;
                trans.setScale(new Vector2f(-1,1));
                trans.setFacingRight(true);
            }
            else if(angle >135 || angle < -135)
            {
                anim.spriteSheetDirection = (byte)1*3;
                trans.setFacingRight(false);

            }
            else if (angle > 0)
            {
                anim.spriteSheetDirection= (byte)0*3;

            }
            else
            {
                anim.spriteSheetDirection= (byte)2*3;

            }
            if (BitMasks.checkIfContains(g.getBitmask(),Speed.class))
            {
                anim.setAnimationTime(30/g.getComponent(Speed.class).getStat());
            }
            if (BitMasks.checkIfContains(g.getBitmask(), Inputs.class))
            {
                if (g.getComponent(Inputs.class).isMoving())
                {
                    if (anim.checkClock())
                    {
                        anim.resetClock();
                        anim.spriteSheetAnimation = (byte)oscillate(anim.animationCounter,0,2);
                        anim.animationCounter ++;

                    }
                    else{
                        anim.updateClock(dt);
                    }
                }
                else
                {
                    anim.animationCounter =0;
                    anim.finishClock();
                    anim.spriteSheetAnimation =1;
                }
            }


        }


    }
    public static int oscillate(int input, int min, int max)
    {
        int range = max - min ;
        return min + Math.abs(((input + range) % (range * 2)) - range);
    }
}
