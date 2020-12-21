package Main.Physics.RigidBody;

import org.jsfml.system.Vector2f;

public class RigidBody {

    private Vector2f position = new Vector2f(0.0f, 0.0f);
    private float rotation = 0.0f;

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
