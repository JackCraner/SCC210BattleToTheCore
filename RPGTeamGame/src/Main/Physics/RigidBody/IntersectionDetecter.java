package Main.Physics.RigidBody;

import Main.Physics.PhyMath;
import Main.Physics.Primitives.AABB;
import Main.Physics.Primitives.Box;
import Main.Physics.Primitives.Circle;
import org.jsfml.system.Vector2f;

public class IntersectionDetecter {

    public static boolean pointOnLine(Vector2f point, Vector2f[] line) {
        float dy = line[1].y - line[0].y;
        float dx = line[1].x - line[0].x;

        if (dx == 0.0f) {
            return PhyMath.compare(point.x, line[0].x);
        }

        float m = dy / dx;
        float b = line[1].y - (m * line[0].x);

        // Check the line equation
        return point.y == m * point.x + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle) {
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = Vector2f.sub(point, circleCenter);

        return PhyMath.lengthSquared(centerToPoint) <= circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2f point, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box box) {
        // Translate the point into local space
        Vector2f pointLocalBoxSpace = new Vector2f(point.x, point.y);
        PhyMath.rotate(pointLocalBoxSpace, box.getRigidBody().getRotation(),
                box.getRigidBody().getPosition());

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return pointLocalBoxSpace.x <= max.x && min.x <= pointLocalBoxSpace.x &&
                pointLocalBoxSpace.y <= max.y && min.y <= pointLocalBoxSpace.y;
    }

    public static boolean lineAndCircle(Vector2f[] line, Circle circle) {
        if (pointInCircle(line[0],circle) || pointInCircle(line[1], circle)){
            return true;
        }

        Vector2f ab = Vector2f.sub(line[1], line[0]);

        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineStart = Vector2f.sub(circleCenter, line[0]);
        float t = PhyMath.dot(centerToLineStart, ab) / PhyMath.dot(ab, ab);

        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        Vector2f closestPoint = Vector2f.add(line[0], Vector2f.mul(ab, t));

        return pointInCircle(closestPoint, circle);
    }
}
