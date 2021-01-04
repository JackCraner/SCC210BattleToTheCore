package Main.Physics.Forces;

import Main.Physics.RigidBody.RigidBody;

import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    public void add(RigidBody rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.add(fr);
    }

    public void remove(RigidBody rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clear() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration fr : registry) {
            fr.fg.updateForce(fr.rb, dt);
        }
    }

    public boolean contains(RigidBody rb, ForceGenerator fg) {
        return registry.contains(new ForceRegistration(fg, rb));
    }

    public void zeroForces() {
        for (ForceRegistration fr : registry) {
            // TODO: IMPLEMENT ME
            //fr.rb.zeroForces();
        }
    }
}
