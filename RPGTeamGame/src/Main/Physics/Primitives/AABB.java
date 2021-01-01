package Main.Physics.Primitives;

import Main.Physics.PhyMath;
import Main.Physics.RigidBody.RigidBody;
import org.jsfml.system.Vector2f;

// Axis Aligned Bounding Box
public class AABB {
    private Vector2f size = new Vector2f(0.0f,0.0f);
    private Vector2f halfSize;
    private RigidBody rigidBody = null;

    public AABB () {
        this.halfSize = Vector2f.mul(size, 0.5f);
    }

    public AABB (Vector2f min, Vector2f max){
        this.size = Vector2f.sub(max, min);
        this.halfSize = Vector2f.mul(size, 0.5f);
    }

    public Vector2f getMin() {
        return Vector2f.sub(this.rigidBody.getPosition(), this.halfSize);
    }

    public Vector2f getMax() {
        return Vector2f.add(this.rigidBody.getPosition(), this.halfSize);
    }

    public void setRigidBody(RigidBody rb) {
        this.rigidBody = rb;
    }

    public void setSize(Vector2f size) {
        this.size = PhyMath.newVector2f(size);
        this.halfSize = new Vector2f(size.x / 2.0f, size.y / 2.0f);
    }
}
