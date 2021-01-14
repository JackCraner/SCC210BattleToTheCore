package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.Inputs;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

public class InputGameSystem extends GameSystem
{
    private static InputGameSystem systemInstance = new InputGameSystem();

    public static InputGameSystem getSystemInstance() {
        return systemInstance;
    }
    private InputGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Inputs.class));
    }
    @Override
    public void update(float dt)
    {
        for(GameObject g: getGameObjectList())
        {
            Inputs gameObjectInputs = g.getComponent(Inputs.class);
            gameObjectInputs.backwards = false;
            gameObjectInputs.drop = false;
            gameObjectInputs.forward = false;
            gameObjectInputs.left = false;
            gameObjectInputs.pickUP = false;
            gameObjectInputs.use = false;
            gameObjectInputs.right = false;
            if (gameObjectInputs.inputType == InputTypes.HUMAN)
            {
                if (Keyboard.isKeyPressed(Keyboard.Key.W))
                {
                    gameObjectInputs.forward = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.A))
                {
                    gameObjectInputs.left = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.S))
                {
                    gameObjectInputs.backwards = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.D))
                {
                    gameObjectInputs.right = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.E))
                {
                    gameObjectInputs.drop = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.RIGHT))
                {
                    gameObjectInputs.use = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.LEFT))
                {
                    gameObjectInputs.pickUP = true;
                }
            }
            if (gameObjectInputs.inputType == InputTypes.AI)
            {
                //AI

                gameObjectInputs.right = true;
            }

        }
    }
}
