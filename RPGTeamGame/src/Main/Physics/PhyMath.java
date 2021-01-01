package Main.Physics;

import org.jsfml.system.Vector2f;

public class PhyMath {

    public static Vector2f rotate(Vector2f vec, float angleDeg, Vector2f origin){
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float) Math.cos(Math.toRadians(angleDeg));
        float sin = (float) Math.sin(Math.toRadians(angleDeg));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec = new Vector2f(xPrime, yPrime);
        return vec;
    }

    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2, float epsilon) {
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }

    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }

    // ==============================================
    // Vector Operators
    // ==============================================
    public static Vector2f newVector2f(Vector2f vec) {
        return new Vector2f(vec.x, vec.y);
    }

    public static Vector2f setXValue(Vector2f vec, float xValue) {
        vec = new Vector2f(xValue, vec.y);
        return vec;
    }

    public static Vector2f setYValue(Vector2f vec, float yValue) {
        vec = new Vector2f(vec.x, yValue);
        return vec;
    }

    public static float dot(Vector2f vec1, Vector2f vec2){
        return vec1.x * vec2.x + vec1.y * vec2.y;
    }

    public static float angle(Vector2f vec1, Vector2f vec2) {
        float dot = vec1.x * vec2.x + vec1.y * vec2.y;
        float det = vec1.x * vec2.y - vec1.y * vec2.x;
        return (float) Math.atan2(det, dot);
    }

    public static float lengthSquared(Vector2f vec) {
        return vec.x * vec.x + vec.y * vec.y;
    }

    public static float length(Vector2f vec) {
        return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
    }

    public static Vector2f normalise(Vector2f vec) {
        float l = PhyMath.length(vec);
        return new Vector2f(vec.x / l, vec.y / l);
    }

    public static float get(Vector2f vec, int component) throws IllegalArgumentException {
        switch (component) {
            case 0:
                return vec.x;
            case 1:
                return vec.y;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Vector2f setComponent(Vector2f vec ,int component, float value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                vec = PhyMath.setXValue(vec, value);
                break;
            case 1:
                vec = PhyMath.setYValue(vec, value);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return vec;
    }
}
