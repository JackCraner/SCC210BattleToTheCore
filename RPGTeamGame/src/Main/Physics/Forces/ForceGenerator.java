package Main.Physics.Forces;

import Main.Physics.RigidBody.RigidBody;

public interface ForceGenerator {
    void updateForce(RigidBody body, float dt);
}
