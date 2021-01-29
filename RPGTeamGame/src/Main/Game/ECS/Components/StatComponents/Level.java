package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

public class Level extends Component
{
    private float currentLevel;
    private float currentXP;
    public Level(float level)
    {
        this.currentLevel = level;
        this.currentXP = 0;
    }

    public float getCurrentLevel() {
        return currentLevel;
    }
    public void levelUP()
    {
        currentLevel++;
        currentXP =0;
    }

    public float getCurrentXP() {
        return currentXP;
    }
    public void addCurrentXP(float xp)
    {
        currentXP += xp;
    }

    @Override
    public Component clone() {
        return new Level(currentLevel);
    }
}
