package Main.Physics.Primitives;

import Main.Physics.PhyMath;
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

    public Vector2f getLocalMin() {
        return Vector2f.sub(this.rigidBody.getPosition(), this.halfSize);
    }

    public Vector2f getLocalMax() {
        return Vector2f.add(this.rigidBody.getPosition(), this.halfSize);
    }

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public Vector2f[] getVertices(){
        Vector2f min = this.getLocalMin();
        Vector2f max = this.getLocalMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        if (this.rigidBody.getRotation() != 0.0f) {
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = PhyMath.rotate(vertices[i], this.rigidBody.getRotation(), this.rigidBody.getPosition());
            }
        }
        return vertices;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(RigidBody rb) {
        this.rigidBody = rb;
    }

    public void setSize(Vector2f size) {
        this.size = PhyMath.newVector2f(size);
        this.halfSize = new Vector2f(size.x / 2.0f, size.y / 2.0f);
    }
}
