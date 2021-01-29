package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.graphics.Color;

public class Particles extends Component
{
    Color color;
    private final float spawnCooldown;
    private float spawnTime;
    public Particles(float spawnCooldown)
    {
        this.spawnCooldown = spawnCooldown;
    }

    public float getSpawnCooldown() {
        return spawnCooldown;
    }

    public float getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    @Override
    public Component clone() {
        return null;
    }
}
