package Main.Physics.Primitives;

import Main.Physics.PhyMath;
import org.jsfml.system.Vector2f;

public class Ray {
    private Vector2f origin;
    private Vector2f direction;

    public Ray(Vector2f origin, Vector2f direction) {
        this.origin = origin;
        this.direction = PhyMath.normalise(direction);
    }

    public Vector2f getOrigin() {
        return this.origin;
    }

    public Vector2f getDirection() {
        return this.direction;
    }
}
