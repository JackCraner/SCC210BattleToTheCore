package Main.Physics.Primitives;

import Main.Physics.RigidBody.RigidBody;
import org.jsfml.system.Vector2f;

public class Circle {
    private float radius = 1.0f;
    private RigidBody rigidBody = null;

    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return this.rigidBody.getPosition();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRigidBody(RigidBody rb) {
        this.rigidBody = rb;
    }
}
