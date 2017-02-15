package com.xdefcon.spigotping;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main extends JavaPlugin {
    private FileConfiguration config;

    private static int getPing(Player p) {
        String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class<?> CraftPlayerClass = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
            Object CraftPlayer = CraftPlayerClass.cast(p);
            Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
            Object EntityPlayer = getHandle.invoke(CraftPlayer);
            Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            return ping.getInt(EntityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void onEnable() {
        config = getConfig();
        config.options().copyDefaults(true);
        config.options().copyHeader(true);
        saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        final Player p = (Player) sender;
        if (c.getName().equalsIgnoreCase("ping")) {
            String ping = "" + getPing(p);
            String customMex = config.getString("pingMessage").replaceAll("%ping%", ping);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', customMex));
            return false;
        }
        return false;
    }
}
