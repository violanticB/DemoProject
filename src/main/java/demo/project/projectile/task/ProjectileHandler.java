package demo.project.projectile.task;

import demo.project.DemoPlugin;
import demo.project.projectile.listener.CustomProjectileListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectileHandler {

    private final BukkitScheduler scheduler;
    private final Map<Integer, Integer> indefiniteTasks;

    public ProjectileHandler(JavaPlugin plugin) {
        this.scheduler = plugin.getServer().getScheduler();
        this.indefiniteTasks = new ConcurrentHashMap<>();

        plugin.getServer().getPluginManager().registerEvents(
                new CustomProjectileListener(this),
                plugin
        );
    }

    public BukkitScheduler getScheduler() {
        return scheduler;
    }

    /**
     * Map that pairs task id for all projectiles that
     * are waiting to find a target
     * @return Indefinite tasks
     */
    public Map<Integer, Integer> getIndefiniteTasks() {
        return indefiniteTasks;
    }

    public void start (ProjectileTask task) {
        int id = scheduler.scheduleSyncRepeatingTask(DemoPlugin.get(), task, task.getDelayTicks(), task.getTicks());

        if(task.getDurationTicks() > -1) {
            scheduler.runTaskLater(DemoPlugin.get(), () -> {
                scheduler.cancelTask(id);
            }, task.getDurationTicks());
        } else {
            getIndefiniteTasks().put(task.getProjectile().getEntityId(), id);
        }
    }

    public void cancelTask (int projectileId) {
        scheduler.cancelTask(indefiniteTasks.get(projectileId));
    }

}
