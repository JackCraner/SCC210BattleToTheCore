package Main.Physics.Primitives;

import Main.Physics.PhyMath;
import Main.Physics.Physics;
import Main.Physics.RigidBody.RigidBody;
import org.jsfml.system.Vector2f;

public class Box {
    private Vector2f size = new Vector2f(0.0f,0.0f);
    private Vector2f halfSize;
    private RigidBody rigidBody = null;

    public Box () {
        this.halfSize = Vector2f.mul(size, 0.5f);
    }

    public Box (Vector2f min, Vector2f max){
        this.size = Vector2f.sub(max, min);
        this.halfSize = Vector2f.mul(size, 0.5f);
    }

    public Vector2f getMin() {
        return Vector2f.sub(this.rigidBody.getPosition(), this.halfSize);
    }

    public Vector2f getMax() {
        return Vector2f.add(this.rigidBody.getPosition(), this.halfSize);
    }

    public Vector2f[] getVertices(){
        Vector2f min = this.getMin();
        Vector2f max = this.getMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        if (this.rigidBody.getRotation() != 0.0f) {
            for (Vector2f vert : vertices) {
                PhyMath.rotate(vert, this.rigidBody.getRotation(), this.rigidBody.getPosition());
            }
        }
        return vertices;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
