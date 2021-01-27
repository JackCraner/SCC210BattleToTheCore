package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Game;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.system.Vector2f;
import Main.Game.Managers.EntityManager;
import java.lang.System;

import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class InputGameSystem extends GameSystem {
    private static InputGameSystem systemInstance = new InputGameSystem();

    public static InputGameSystem getSystemInstance() {
        return systemInstance;
    }

    private InputGameSystem() {
        setBitMaskRequirement(BitMasks.getBitMask(Inputs.class));
    }

    @Override
    public void update(float dt) {
        for (GameObject g : getGameObjectList()) {

            Inputs gameObjectInputs = g.getComponent(Inputs.class);
            gameObjectInputs.backwards = false;
            gameObjectInputs.drop = false;
            gameObjectInputs.forward = false;
            gameObjectInputs.left = false;
            gameObjectInputs.pickUP = false;
            gameObjectInputs.use = false;
            gameObjectInputs.right = false;
            if (gameObjectInputs.inputType == InputTypes.HUMAN) {
                if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
                    gameObjectInputs.forward = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
                    gameObjectInputs.left = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
                    gameObjectInputs.backwards = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
                    gameObjectInputs.right = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.E)) {
                    gameObjectInputs.drop = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.RIGHT)) {
                    gameObjectInputs.use = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
                    gameObjectInputs.pickUP = true;
                }
            }
            if (gameObjectInputs.inputType == InputTypes.AI) {
                //AI
                Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
                Vector2f aiPos = g.getComponent(Position.class).getPosition();
                float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
                boolean playerVisible = false;
                if (distanceFromPlayer <= 100000) {
                    if (!gameObjectInputs.attacking) //comment this to test raycast
                        gameObjectInputs.attacking =
                                raycasting(aiPos, playerPos, (x, y) ->
                                {
                                    return isBlockAtPos(new Vector2f(x, y));
                                });
                }
                // g.getComponent(Inputs.class).attacking = true;
                if (gameObjectInputs.attacking) {
                    if (distanceFromPlayer > 50) {
                        //add sub to stop jitter
                        if (playerPos.x > aiPos.x) {
                            gameObjectInputs.right = true;
                        }
                        if (playerPos.x < aiPos.x) {
                            gameObjectInputs.left = true;
                        }
                        if (playerPos.y < aiPos.y) {
                            gameObjectInputs.forward = true;
                        }
                        if (playerPos.y > aiPos.y) {
                            gameObjectInputs.backwards = true;
                        }
                    } else {

                        gameObjectInputs.use = true;
                    }




                }

            }

        }
    }

    //maths utility function ideally

    private float vectorToDistance(Vector2f position1, Vector2f position2) {
        Vector2f sub = Vector2f.sub(position1, position2);
        return (float) Math.sqrt(sub.x * sub.x + sub.y * sub.y);
    }

    private float dotProduct(Vector2f position1, Vector2f position2) {
        // Vector2f sub = Vector2f.sub(position1, position2);
        return (position1.x * position2.x + position1.y * position2.y);
    }

    private Vector2f pointToVector(Vector2f position1, Vector2f position2) {
        return Vector2f.sub(position2, position1);
    }

    private interface func<T1, T2, TResult> {
        TResult invoke(T1 t1, T2 t2);
    }

    private boolean isBlockAtPos(Vector2f point) {
        ArrayList<GameObject> hello = EntityManager.getEntityManagerInstance().getGameObjectInVicinity(point, 0);
        for (GameObject g : hello) {
            if (g.getName() == Entity.BLOCK.name) {
                         Vector2f L = g.getComponent(Position.class).getPosition();
                Vector2f M = new Vector2f(L.x + 32, L.y);
                Vector2f N = new Vector2f(L.x, L.y + 32);
                float LMLM = dotProduct(pointToVector(L, M), pointToVector(L, M));
                float MNMN = dotProduct(pointToVector(M, N), pointToVector(M, N));
                float LMLP = dotProduct(pointToVector(L, M), pointToVector(L, point));
                float MNMP = dotProduct(pointToVector(M, N), pointToVector(M, point));

                if (0 < LMLP && LMLP < LMLM && 0 < MNMP && MNMP < MNMN) {
                    //System.out.println("True");
                    return true;
                }
            }


        }
        return false;
    }

    private boolean raycasting(Vector2f position1, Vector2f position2, func<Integer, Integer, Boolean> endloop) {
        //COME BACK TO THIS unknown bug
        int dx = (int) Math.abs(position2.x - position1.x);
        int dy = (int) Math.abs(position2.y - position1.y);

        int x = (int) position1.x;
        int y = (int) position1.y;

        int n = 1 + dx + dy;

        int x_inc = (position2.x > position1.x) ? 1 : -1;
        int y_inc = (position2.y > position1.y) ? 1 : -1;
        int error = dx - dy;
        //dx *=3;
        //dy*=3;
        boolean done = false;
        while (n > 0 && !done) {
            if (error >0){
                error-=dy;
                x+=x_inc;

            } else {
                error+=dx;
                y+=y_inc;

            }
            done = endloop.invoke(x, y);
            n --;

        }
        if (done) {
           // System.out.println("NOT FOUND");
            return false;
        } else {
           // System.out.println("FOUND");
            return true;
        }
    }
}
