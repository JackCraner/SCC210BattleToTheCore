package Main.Physics.RigidBody;

import Main.Physics.PhyMath;
import org.jsfml.system.Vector2f;

public class RigidBody {
    //private Transform rawTransform;

    private Vector2f position = new Vector2f(0.0f, 0.0f);
    private float rotation = 0.0f;
    private float mass = 0.0f;
    private float inverseMass = 0.0f;

    private Vector2f forceAccum = new Vector2f(0.0f, 0.0f);
    private Vector2f linearVelocity = new Vector2f(0.0f, 0.0f);
    private float angularVelocity = 0.0f;
    private float linearDamping = 0.0f;
    private float angularDamping = 0.0f;

    private boolean fixedRotation = false;

    public void physicsUpdate(float dt) {
        if (this.mass == 0.0f) return;

        // Calculate linear velocity
        Vector2f acceleration = Vector2f.mul(forceAccum, this.inverseMass);
        this.linearVelocity = Vector2f.add(this.linearVelocity ,Vector2f.mul(acceleration, dt));

        // Update the linear position
        this.position = Vector2f.add(this.position, Vector2f.mul(this.linearVelocity ,dt));

        //synchCollisionTransforms();
        clearAccumulators();
    }

    /* THIS IS FOR GAME TRANSFORM THAT IS NOT IMPLEMENTED IN THIS GAME CODE
    public void synchCollisionTransforms() {
        if (rawTransform != null) {
            this.rawTransform.position.set(this.position);
        }
    }
    */

    public void clearAccumulators() {
        this.forceAccum = new Vector2f(0.0f, 0.0f);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setTransform(Vector2f position, float rotation) {
        this.position = PhyMath.newVector2f(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.position = PhyMath.newVector2f(position);
    }

    public float getRotation() {
        return rotation;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    public void addForce(Vector2f force) {
        this.forceAccum = Vector2f.add(this.forceAccum, force);
    }

    /* THIS IS FOR GAME TRANSFORM THAT IS NOT IMPLEMENTED IN THIS GAME CODE
    public void setRawTransform(Transform rawTransform) {
        this.rawTransform = rawTransform;
        this.position = PhyMath.newVector2f(rawTransform.position);
    }
    */
}
