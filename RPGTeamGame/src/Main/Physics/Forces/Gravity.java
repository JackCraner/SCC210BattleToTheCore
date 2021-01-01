package Main.Physics.Forces;

import Main.Physics.PhyMath;
import Main.Physics.RigidBody.RigidBody;
import org.jsfml.system.Vector2f;

public class Gravity implements ForceGenerator{
    private Vector2f gravity;

    public static Vector2f FORCE = new Vector2f(0.0f, 1.0f);

    public Gravity() {
        this.gravity = PhyMath.newVector2f(Gravity.FORCE);
    }

    @Override
    public void updateForce(RigidBody body, float dt) {
        body.addForce(Vector2f.mul(gravity, body.getMass()));
    }
}
