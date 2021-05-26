package demo.project.projectile.task.impl;

import demo.project.projectile.task.ProjectileTask;
import demo.project.projectile.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.Optional;

public class EggManipulationTask extends ProjectileTask {

    private static final double SEARCH_SIZE = 20;

    public EggManipulationTask(Projectile projectile, Entity target) {
        super(1, 0, -1, projectile, target);
    }

    @Override
    public void tickFunction() {
        Optional<Entity> target = getTarget();

        if(target.isPresent() && !getProjectile().isDead()) {
            double eggSpeed = 1.2;

            Location to = EntityUtil.getFixedLocation(target.get());
            Vector dv = to.subtract(getProjectile().getLocation())
                    .toVector().normalize().multiply(eggSpeed);

            getProjectile().setVelocity(
                    dv
            );

        } else if(!getProjectile().isDead()){
            setTarget(EntityUtil.findClosestEntity(getProjectile(), SEARCH_SIZE, false));
        }
    }
}
