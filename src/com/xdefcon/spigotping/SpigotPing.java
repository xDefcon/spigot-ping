package com.xdefcon.spigotping;

import com.xdefcon.spigotping.bstats.Metrics;
import com.xdefcon.spigotping.commands.PingCommand;
import com.xdefcon.spigotping.commands.PingReloadCommand;
import com.xdefcon.spigotping.tablist.PingTabList;
import org.bukkit.plugin.java.JavaPlugin;


public class SpigotPing extends JavaPlugin {
    private static SpigotPing instance;

    public static SpigotPing getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.getCommand("ping").setExecutor(new PingCommand(this));
        this.getCommand("pingreload").setExecutor(new PingReloadCommand(this));
        this.registerTasks();
        Metrics metrics = new Metrics(this);
    }

    @Override
    public void onDisable() {
        instance = null;
        this.getLogger().info("Cancelling tasks...");
        this.getServer().getScheduler().cancelTasks(this);
    }

    private void registerTasks() {
        if (this.getConfig().getBoolean("permission-system.enabled")) {
            this.getLogger().info("The permission-system is enabled. Please check that users have proper permissions.");
        }
        if (!this.getConfig().getBoolean("sound-manager.enabled")) {
            this.getLogger().info("The sound manager is disabled, no sounds will be played on commands. You can change this option in the config.");
        }
        if (!this.getConfig().getBoolean("tablist.enabled")) {
            this.getLogger().info("The tablist is disabled, the ping will not be shown as a prefix. You can change this option in the config.");
        } else {
            Long delay = this.getConfig().getLong("tablist.update-delay");
            new PingTabList(this).runTaskTimerAsynchronously(this, delay * 20, delay * 20);
            this.getLogger().info("TabList is enabled, task added with a delay of " + delay + " second/s.");
        }
    }

    public void reload() {
        this.getLogger().info("Reloading the plugin...");
        this.getServer().getScheduler().cancelTasks(this);
        this.reloadConfig();
        this.registerTasks();
        this.getLogger().info("Plugin reloaded.");
    }
}
