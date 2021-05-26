package demo.project.projectile.task.impl;

import demo.project.projectile.task.ProjectileTask;
import demo.project.projectile.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.Optional;

public class EnderpearlManipulationTask extends ProjectileTask {

    private static final double PEARL_SPEED = 0.25;

    public EnderpearlManipulationTask(Projectile projectile, Entity target) {
        super(1, 0, -1, projectile, target);
    }

    @Override
    public void tickFunction() {
        Optional<Entity> target = getTarget();

        if(target.isPresent() && !getProjectile().isDead()) {
            Location to = EntityUtil.getFixedLocation(target.get());

            Vector dv = to.subtract(getProjectile().getLocation()).toVector();
            getProjectile().setVelocity(
                    dv.normalize().multiply(PEARL_SPEED)
            );
        }
    }

}
