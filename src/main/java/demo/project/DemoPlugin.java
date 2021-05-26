package demo.project;

import demo.project.projectile.task.ProjectileHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DemoPlugin extends JavaPlugin {

    private static DemoPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        new ProjectileHandler(this);
    }

    public static DemoPlugin get() {
        return instance;
    }
}
