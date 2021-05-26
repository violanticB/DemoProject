package demo.project.projectile.listener;

import demo.project.projectile.task.ProjectileHandler;
import demo.project.projectile.task.impl.ArrowManipulationTask;
import demo.project.projectile.task.impl.EggManipulationTask;
import demo.project.projectile.task.impl.EnderpearlManipulationTask;
import demo.project.projectile.util.EntityUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.UUID;

public class CustomProjectileListener implements Listener {

    private final ProjectileHandler handler;

    public CustomProjectileListener(ProjectileHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        System.out.println("Player logging in: " + uuid.toString());
    }

    /*
     * The chickens were incredibly annoying while I was testing
     * the mechanics on the egg projectile.
     */
    @EventHandler
    public void onEgg(PlayerEggThrowEvent event) {
        event.setHatching(false);
    }

    @EventHandler
    public void onLand(ProjectileHitEvent event) {
        int eid = event.getEntity().getEntityId();
        if(handler.getIndefiniteTasks().containsKey(eid)) {
            handler.cancelTask(eid);
            handler.getIndefiniteTasks().remove(eid);
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        ProjectileSource shooter = event.getEntity().getShooter();
        if(!(shooter instanceof Player))
            return;

        /*
         * Player has launched arrow
         */
        if(event.getEntity() instanceof Arrow) {
            handler.start(new ArrowManipulationTask(event.getEntity(), null));
        }

        /*
         * Player has launched egg
         */
        else if(event.getEntity() instanceof Egg) {
            handler.start(new EggManipulationTask(event.getEntity(), null));
        }

        /*
         * Player has launched enderpearl
         */
        else if(event.getEntity() instanceof EnderPearl) {
            Entity target = EntityUtil.getTargetEntity((Player) shooter, 50);
            handler.start(new EnderpearlManipulationTask(event.getEntity(), target));
        }
    }

}
