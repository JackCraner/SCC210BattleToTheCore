package Main.Physics;

import Main.Background.MapGen.Block;
import Main.Background.MapGen.Map;
import Main.Game;
import Main.Physics.Forces.ForceRegistry;
import Main.Physics.Forces.Gravity;
import Main.Physics.Primitives.AABB;
import Main.Physics.RigidBody.IntersectionDetector;
import Main.Physics.RigidBody.RigidBody;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Physics {
    private static Physics instance = null;

    private List<AABB> backgroundColliable;
    private List<AABB> entitys;

    private ForceRegistry forceRegistry;
    private List<RigidBody> rigidbodies;
    private Gravity gravity;
    private float fixedUpdate;

    public Physics(float fixedUpdateDt) {
        this.backgroundColliable = new ArrayList<>();
        this.entitys = new ArrayList<>();

        this.forceRegistry = new ForceRegistry();
        this.rigidbodies = new ArrayList<>();
        this.gravity = new Gravity();
        this.fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {
        fixedUpdate();
    }

    public void fixedUpdate() {
        forceRegistry.updateForces(fixedUpdate);

        // Update the velocities of all rigidbodies
        for (int i=0; i < rigidbodies.size(); i++) {
            rigidbodies.get(i).physicsUpdate(fixedUpdate);
        }
    }

    public void addRigidbody(RigidBody body) {
        this.rigidbodies.add(body);
        this.forceRegistry.add(body, gravity);
    }



    public void setBackground(Map map) {
        Block[][] blocks =  map.getMapChunk().getbMapping();

        for (Block[] bLine : blocks)
        {
            for (Block b : bLine)
            {
                if (b.getID() == Block.WALL)
                {
                    if (this.checkBlockForAir(b, blocks))
                    {
                        RigidBody aabbBlockBody = new RigidBody();
                        aabbBlockBody.setTransform(b.getCenterPosition());
                        AABB aabbBlock = new AABB();
                        aabbBlock.setSize(new Vector2f(Game.blockSize, Game.blockSize));
                        aabbBlock.setRigidBody(aabbBlockBody);

                        backgroundColliable.add(aabbBlock);
                    }
                }
            }
        }
    }

    public boolean checkBlockForAir(Block b, Block[][] map) {
        Vector2i bLocation = b.inBlock();

        try {
            if (map[bLocation.x - 1][bLocation.y].getID() == Block.EMPTY ||
                    map[bLocation.x + 1][bLocation.y].getID() == Block.EMPTY ||
                    map[bLocation.x][bLocation.y - 1].getID() == Block.EMPTY ||
                    map[bLocation.x][bLocation.y + 1].getID() == Block.EMPTY) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public void addEntity(RigidBody entity) {
        AABB aabbEntity = new AABB();
        aabbEntity.setSize(new Vector2f(Game.blockSize, Game.blockSize));
        aabbEntity.setRigidBody(entity);

        entitys.add(aabbEntity);


        this.addRigidbody(entity);
    }

    public boolean checkCollision() {
        for (AABB aabbEntity : entitys) {
            for (AABB aabbBlock : backgroundColliable) {
                if (IntersectionDetector.AABBAndAABB(aabbEntity, aabbBlock)) {
                    return true;
                }
            }
        }

        return false;
    }
}

