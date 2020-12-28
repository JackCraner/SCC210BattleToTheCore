package Main.Physics.RigidBody;

import Main.Physics.PhyMath;
import Main.Physics.Primitives.*;
import org.jsfml.system.Vector2f;

public class IntersectionDetecter {

    // ========================================================
    // Point vs. Primitive Tests
    // ========================================================

    public static boolean pointOnLine(Vector2f point, Line line) {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;

        if (dx == 0.0f) {
            return PhyMath.compare(point.x, line.getStart().x);
        }

        float m = dy / dx;
        float b = line.getEnd().y - (m * line.getStart().x);

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
        pointLocalBoxSpace = PhyMath.rotate(pointLocalBoxSpace, box.getRigidBody().getRotation(),
                box.getRigidBody().getPosition());

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return pointLocalBoxSpace.x <= max.x && min.x <= pointLocalBoxSpace.x &&
                pointLocalBoxSpace.y <= max.y && min.y <= pointLocalBoxSpace.y;
    }

    // ========================================================
    // Line vs. Primitive Tests
    // ========================================================

    public static boolean lineAndCircle(Line line, Circle circle) {
        if (pointInCircle(line.getStart(),circle) || pointInCircle(line.getEnd(), circle)){
            return true;
        }

        Vector2f ab = Vector2f.sub(line.getEnd(), line.getStart());

        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineStart = Vector2f.sub(circleCenter, line.getStart());
        float t = PhyMath.dot(centerToLineStart, ab) / PhyMath.dot(ab, ab);

        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        Vector2f closestPoint = Vector2f.add(line.getStart(), Vector2f.mul(ab, t));

        return pointInCircle(closestPoint, circle);
    }

    public static boolean lineAndAABB(Line line, AABB box) {
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) {
            return true;
        }

        Vector2f unitVector = PhyMath.normalise(Vector2f.sub(line.getEnd(), line.getStart()));
        unitVector = new Vector2f((unitVector.x != 0) ? 1.0f / unitVector.x : 0f, (unitVector.y != 0) ? 1.0f / unitVector.y : 0f);

        Vector2f min = Vector2f.componentwiseMul(Vector2f.sub(box.getMin(), line.getStart()), unitVector);
        Vector2f max = Vector2f.componentwiseMul(Vector2f.sub(box.getMax(), line.getStart()), unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        return t > 0f && t * t < line.lengthSquared();
    }

    public static boolean lineAndBox2D(Line line, Box box) {
        float theta = -box.getRigidBody().getRotation();
        Vector2f center = box.getRigidBody().getPosition();
        Vector2f localStart = new Vector2f(line.getStart().x, line.getStart().y);
        Vector2f localEnd = new Vector2f(line.getEnd().x, line.getEnd().y);
        localStart = PhyMath.rotate(localStart, theta, center);
        localEnd = PhyMath.rotate(localEnd, theta, center);

        Line localLine = new Line(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAndAABB(localLine, aabb);
    }

    public static boolean rayCast(Circle circle, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f originToCircle = Vector2f.sub(circle.getCenter(),ray.getOrigin());
        float radiusSquared = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSquared = PhyMath.lengthSquared(originToCircle);

        // Project the vector from the ray origin onto the direction of the ray
        float a = PhyMath.dot(originToCircle, ray.getDirection());
        float bSq = originToCircleLengthSquared - (a * a);
        if (radiusSquared - bSq < 0.0f) {
            return false;
        }

        float f = (float)Math.sqrt(radiusSquared - bSq);
        float t = 0;
        if (originToCircleLengthSquared < radiusSquared) {
            // Ray starts inside the circle
            t = a + f;
        } else {
            t = a - f;
        }

        if (result != null) {
            Vector2f point = Vector2f.add(ray.getOrigin(), Vector2f.mul(ray.getDirection(), t));
            Vector2f normal = PhyMath.normalise(Vector2f.sub(point, circle.getCenter()));

            result.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean rayCast(AABB box, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f unitVector = PhyMath.normalise(ray.getDirection());
        unitVector = new Vector2f((unitVector.x != 0) ? 1.0f / unitVector.x : 0f, (unitVector.y != 0) ? 1.0f / unitVector.y : 0f);

        Vector2f min = Vector2f.componentwiseMul(Vector2f.sub(box.getMin(), ray.getOrigin()), unitVector);
        Vector2f max = Vector2f.componentwiseMul(Vector2f.sub(box.getMax(), ray.getOrigin()), unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0.0f; // && t * t < ray.getMaximum();

        if (!hit)
        {
            return false;
        }

        if (result != null) {
            Vector2f point = Vector2f.add(ray.getOrigin(), Vector2f.mul(ray.getDirection(), t));
            Vector2f normal = PhyMath.normalise(Vector2f.sub(ray.getOrigin(), point));

            result.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean rayCast(Box box, Ray ray, RaycastResult result) {
        RaycastResult.reset(result);

        Vector2f size = box.getHalfSize();
        Vector2f xAxis = new Vector2f(1, 0);
        Vector2f yAxis = new Vector2f(0, 1);
        xAxis = PhyMath.rotate(xAxis, -box.getRigidBody().getRotation(), new Vector2f(0, 0));
        yAxis = PhyMath.rotate(yAxis, -box.getRigidBody().getRotation(), new Vector2f(0, 0));

        Vector2f p = Vector2f.sub(box.getRigidBody().getPosition(), ray.getOrigin());
        // Project the direction of the ray onto each axis of the box
        Vector2f f = new Vector2f(
                PhyMath.dot(xAxis, ray.getDirection()),
                PhyMath.dot(yAxis, ray.getDirection())
        );
        // Next, project p onto every axis of the box
        Vector2f e = new Vector2f(
                PhyMath.dot(xAxis, p),
                PhyMath.dot(yAxis, p)
        );

        float[] tArr = {0, 0, 0, 0};
        for (int i=0; i < 2; i++) {
            if (PhyMath.compare(PhyMath.get(f, i), 0)) {
                // If the ray is parallel to the current axis, and the origin of the
                // ray is not inside, we have no hit
                if (-PhyMath.get(e, i) - PhyMath.get(size, i) > 0 || -PhyMath.get(e, i) + PhyMath.get(size, i) < 0) {
                    return false;
                }
                f = PhyMath.setComponent(f, i, 0.00001f); // Set it to small value, to avoid divide by zero
            }
            tArr[i * 2 + 0] = (PhyMath.get(e, i) + PhyMath.get(size, i)) / PhyMath.get(f, i); // tmax for this axis
            tArr[i * 2 + 1] = (PhyMath.get(e, i) - PhyMath.get(size, i)) / PhyMath.get(f, i); // tmin for this axis
        }

        float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
        float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));

        float t = (tmin < 0f) ? tmax : tmin;
        boolean hit = t > 0f; //&& t * t < ray.getMaximum();

        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2f point = Vector2f.add(ray.getOrigin(), Vector2f.mul(ray.getDirection(),t));
            Vector2f normal = PhyMath.normalise(Vector2f.sub(ray.getOrigin(), point));

            result.init(point, normal, t, true);
        }

        return true;
    }

    // =============================================================================
    // Circle vs. Primitive tests
    // =============================================================================

    public static boolean circleAndLine(Circle circle, Line line) {
        return lineAndCircle(line, circle);
    }

    public static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector2f vecBetweenCenters = Vector2f.sub(c1.getCenter(), c2.getCenter());
        float radiiSum = c1.getRadius() + c2.getRadius();
        return PhyMath.lengthSquared(vecBetweenCenters) <= radiiSum * radiiSum;
    }

    public static boolean circleAndAABB(Circle circle, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        Vector2f closestPointToCircle = PhyMath.newVector2f(circle.getCenter());
        if (closestPointToCircle.x < min.x) {
            closestPointToCircle = PhyMath.setXValue(closestPointToCircle, min.x);
        } else if (closestPointToCircle.x > max.x) {
            closestPointToCircle = PhyMath.setXValue(closestPointToCircle, max.x);;
        }

        if (closestPointToCircle.y < min.y) {
            closestPointToCircle = PhyMath.setYValue(closestPointToCircle, min.y);
        } else if (closestPointToCircle.y > max.y) {
            closestPointToCircle = PhyMath.setYValue(closestPointToCircle, max.y);
        }

        Vector2f circleToBox = Vector2f.sub(circle.getCenter(), closestPointToCircle);
        return PhyMath.lengthSquared(circleToBox) <= circle.getRadius() * circle.getRadius();
    }

    public static boolean circleAndBox2D(Circle circle, Box box) {
        // Treat the box just like an AABB, after we rotate the stuff
        Vector2f min = new Vector2f(0.0f, 0.0f);
        Vector2f max = Vector2f.mul(box.getHalfSize(),2.0f);

        // Create a circle in box's local space
        Vector2f r = Vector2f.sub(circle.getCenter(), box.getRigidBody().getPosition());
        r = PhyMath.rotate(r, -box.getRigidBody().getRotation(), new Vector2f(0.0f, 0.0f));
        Vector2f localCirclePos = Vector2f.add(r, box.getHalfSize());

        Vector2f closestPointToCircle = PhyMath.newVector2f(localCirclePos);
        if (closestPointToCircle.x < min.x) {
            closestPointToCircle = PhyMath.setXValue(closestPointToCircle, min.x);
        } else if (closestPointToCircle.x > max.x) {
            closestPointToCircle = PhyMath.setXValue(closestPointToCircle, max.x);;
        }

        if (closestPointToCircle.y < min.y) {
            closestPointToCircle = PhyMath.setYValue(closestPointToCircle, min.y);
        } else if (closestPointToCircle.y > max.y) {
            closestPointToCircle = PhyMath.setYValue(closestPointToCircle, max.y);
        }

        Vector2f circleToBox = Vector2f.sub(localCirclePos, closestPointToCircle);
        return PhyMath.lengthSquared(circleToBox) <= circle.getRadius() * circle.getRadius();
    }

    // =============================================================================
    // AABB vs. Primitive tests
    // =============================================================================

    public static boolean AABBAndCircle(AABB box, Circle circle) {
        return circleAndAABB(circle, box);
    }

    public static boolean AABBAndAABBB(AABB b1, AABB b2) {
        Vector2f axesToTest[] = {new Vector2f(0, 1), new Vector2f(1, 0)};
        for (int i=0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean AABBAndBox2D(AABB b1, Box b2) {
        Vector2f axesToTest[] = {
                new Vector2f(0, 1), new Vector2f(1, 0),
                new Vector2f(0, 1), new Vector2f(1, 0)
        };
        axesToTest[2] = PhyMath.rotate(axesToTest[2], b2.getRigidBody().getRotation(), new Vector2f(0.0f, 0.0f));
        axesToTest[3] = PhyMath.rotate(axesToTest[3], b2.getRigidBody().getRotation(), new Vector2f(0.0f, 0.0f));
        for (int i=0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }
        return true;
    }

    // =============================================================================
    // SAT helpers
    // =============================================================================

    private static boolean overlapOnAxis(AABB b1, AABB b2, Vector2f axis) {
        Vector2f interval1 = getInterval(b1, axis);
        Vector2f interval2 = getInterval(b2, axis);
        return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
    }

    private static boolean overlapOnAxis(AABB b1, Box b2, Vector2f axis) {
        Vector2f interval1 = getInterval(b1, axis);
        Vector2f interval2 = getInterval(b2, axis);
        return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
    }

    private static boolean overlapOnAxis(Box b1, Box b2, Vector2f axis) {
        Vector2f interval1 = getInterval(b1, axis);
        Vector2f interval2 = getInterval(b2, axis);
        return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
    }

    private static Vector2f getInterval(AABB rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f min = rect.getMin();
        Vector2f max = rect.getMax();

        Vector2f vertices[] = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };

        result = PhyMath.setXValue(result, PhyMath.dot(axis, vertices[0]));
        result = PhyMath.setYValue(result, result.x);
        for (int i=1; i < 4; i++) {
            float projection = PhyMath.dot(axis, vertices[i]);
            if (projection < result.x) {
                result = PhyMath.setXValue(result, projection);
            }
            if (projection > result.y) {
                result = PhyMath.setYValue(result, projection);
            }
        }
        return result;
    }

    private static Vector2f getInterval(Box rect, Vector2f axis) {
        Vector2f result = new Vector2f(0, 0);

        Vector2f vertices[] = rect.getVertices();

        result = PhyMath.setXValue(result, PhyMath.dot(axis, vertices[0]));
        result = PhyMath.setYValue(result, result.x);
        for (int i=1; i < 4; i++) {
            float projection = PhyMath.dot(axis, vertices[i]);
            if (projection < result.x) {
                result = PhyMath.setXValue(result, projection);
            }
            if (projection > result.y) {
                result = PhyMath.setYValue(result, projection);
            }
        }
        return result;
    }
}
