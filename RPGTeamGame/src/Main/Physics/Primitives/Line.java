package Main.Physics.Primitives;

import Main.Physics.PhyMath;
import org.jsfml.system.Vector2f;

public class Line {
    private Vector2f from;
    private Vector2f to;

    public Line(Vector2f from, Vector2f to) {
        this.from = from;
        this.to = to;
    }

    public Vector2f getFrom() {
        return from;
    }

    public Vector2f getTo() {
        return to;
    }

    public Vector2f getStart() {
        return this.from;
    }

    public Vector2f getEnd() {
        return this.to;
    }

    public float lengthSquared() {
        return PhyMath.lengthSquared(Vector2f.sub(to, from));
    }
}
