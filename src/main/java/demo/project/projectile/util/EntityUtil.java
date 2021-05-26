package demo.project.projectile.util;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.List;

public class EntityUtil {

    public static Location getFixedLocation(Entity entity) {
        Location location;

        if(entity instanceof LivingEntity) {
            location = ((LivingEntity) entity).getEyeLocation();
        } else {
            location = entity.getLocation();
        }

        return location;
    }

    public static Entity getTargetEntity(Player shooter, double r) {
        Location from = shooter.getEyeLocation();

        Entity target = null;
        for (Entity entity : shooter.getNearbyEntities(r, r, r)) {
            Location to = entity instanceof LivingEntity
                    ? ((LivingEntity) entity).getEyeLocation() : entity.getLocation();

            Vector v = to.toVector().subtract(from.toVector()).normalize();

            // Player is looking very close to the entity's position
            if(v.dot(from.getDirection()) > 0.9)
                target = entity;
                break;
        }

        return target;
    }

    public static Entity findClosestEntity(Projectile source, double r, boolean hostile) {
        List<Entity> nearby = source.getNearbyEntities(r, r, r);

        int entityIndex = -1;
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < nearby.size(); i++) {
            double s = nearby.get(i).getLocation().distance(source.getLocation());

            if(s < min && nearby.get(i) != source.getShooter()) {

                if(hostile && !(nearby instanceof Monster)) {
                    continue;
                }

                min = s;
                entityIndex = i;
            }

        }

        return entityIndex == -1 ? null : nearby.get(entityIndex);
    }

}
