package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector3f;
import org.jsfml.system.Vector3i;

import java.util.Random;

public class ParticleGameSystem extends GameSystem
{
    private static ParticleGameSystem systemInstance = new ParticleGameSystem();

    public static ParticleGameSystem getSystemInstance() {
        return systemInstance;
    }
    private ParticleGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Particles.class));

    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            if (BitMasks.checkIfContains(g.getBitmask(),Position.class))
            {
                Particles particle = g.getComponent(Particles.class);
                if (particle.getSpawnTime() > particle.getSpawnCooldown())
                {
                    particle.setSpawnTime(0);
                    Position p =  g.getComponent(Position.class);
                    TransformComponent t = g.getComponent(TransformComponent.class);
                    EntityManager.getEntityManagerInstance().addGameObject(new ParticleAtom(p.getPosition(),new Random().nextInt(360),t.getSize()).getG());
                }
                else
                {
                    particle.setSpawnTime(particle.getSpawnTime()+dt);
                }

            }

        }
    }
}
class ParticleAtom
{
    GameObject g = new GameObject("Particle");
    public ParticleAtom(Vector2f position, int angle, Vector2f size)
    {
        Vector3i colorData = new Vector3i(255,50,50);
        g.addComponent(new Speed(MovementTypes.LINEAR, 100));
        g.addComponent(new Position(new Vector2f(position.x + size.x/2, position.y+size.y/2) ,g));
        g.addComponent(new TransformComponent(new Vector2f(3,3),angle));
        g.addComponent(new LifeSpan(0.15f));
         g.addComponent(new Light(0.01f,1.3f,new Vector3f(colorData)));
        g.addComponent(new TextureComponent(TextureTypes.PARTICLE, (byte)1,"" + colorData.x + "," + colorData.y + "," + colorData.z));
    }

    public GameObject getG() {
        return g;
    }
}