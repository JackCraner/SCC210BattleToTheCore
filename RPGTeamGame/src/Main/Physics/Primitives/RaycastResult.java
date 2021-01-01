package Main.Physics.Primitives;

import org.jsfml.system.Vector2f;

public class RaycastResult {
    private Vector2f point;
    private Vector2f normal;
    private float t;
    private boolean hit;

    public RaycastResult() {
        this.point = new Vector2f(0.0f, 0.0f);
        this.normal = new Vector2f(0.0f, 0.0f);
        this.t = -1;
        this.hit = false;
    }

    public void init(Vector2f point, Vector2f normal, float t, boolean hit) {
        this.point = new Vector2f(point.x, point.y);
        this.normal = new Vector2f(normal.x, normal.y);
        this.t = t;
        this.hit = hit;
    }

    public static void reset(RaycastResult result) {
        if (result != null) {
            result.point = new Vector2f(0.0f, 0.0f);
            result.normal = new Vector2f(0.0f, 0.0f);
            result.t = -1;
            result.hit = false;
        }
    }
}
