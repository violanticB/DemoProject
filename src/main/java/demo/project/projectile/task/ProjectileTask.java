package demo.project.projectile.task;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

import java.util.Optional;

public abstract class ProjectileTask implements Runnable {

    private long ticks;
    private long delayTicks;
    private long durationTicks;
    private final Projectile projectile;
    private Entity target;

    public ProjectileTask(Projectile projectile, Entity target) {
        this.projectile = projectile;
        this.target = target;
    }

    public ProjectileTask(long ticks, long delayTicks, long durationTicks,
                          Projectile projectile,
                          Entity target) {
        this.ticks = ticks;
        this.delayTicks = delayTicks;
        this.durationTicks = durationTicks;
        this.projectile = projectile;
        this.target = target;
    }

    public long getTicks() {
        return ticks;
    }

    public long getDelayTicks() {
        return delayTicks;
    }

    public long getDurationTicks() {
        return durationTicks;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Optional<Entity> getTarget() {
        return Optional.ofNullable(target);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void run() {
        tickFunction();
    }

    public abstract void tickFunction();
}

