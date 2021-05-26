package demo.project.projectile.task.impl;

import demo.project.projectile.task.ProjectileTask;
import demo.project.projectile.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.Optional;

public class ArrowManipulationTask extends ProjectileTask {

    private static final double ARROW_SPEED = 0.5;

    private final Vector v0;

    public ArrowManipulationTask(Projectile projectile, Entity target) {
        super(1L, 0L, 60L, projectile, target);
        this.v0 = projectile.getVelocity().normalize().clone();

        projectile.setVelocity(v0);
    }

    @Override
    public void tickFunction() {
        Optional<Entity> target = getTarget();

        if(target.isPresent() && !getProjectile().isDead()) {

            Location to = EntityUtil.getFixedLocation(target.get());
            Vector dv = to.subtract(getProjectile().getLocation()).toVector();
            getProjectile().setVelocity(
                    dv.normalize().multiply(ARROW_SPEED)
            );

            double theta = dv.angle(v0);
            if (theta > Math.PI / 4) {
                getProjectile().setVelocity(getProjectile().getVelocity().zero());
            }

        }

        // The projectile is still alive and hasn't found a target yet
        else if(!getProjectile().isDead()){
            getProjectile().setVelocity(getProjectile().getVelocity().normalize().multiply(ARROW_SPEED));
            setTarget(EntityUtil.findClosestEntity(getProjectile(), 5, false));
        }
    }

}
