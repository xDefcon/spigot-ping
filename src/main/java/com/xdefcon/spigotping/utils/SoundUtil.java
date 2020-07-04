package com.xdefcon.spigotping.utils;

import com.xdefcon.spigotping.SpigotPing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void playSound(Player p, String soundType) {
        float volume = Float.parseFloat(SpigotPing.getInstance().getConfig().getString("sound-manager.volume"));
        float pitch = Float.parseFloat(SpigotPing.getInstance().getConfig().getString("sound-manager.pitch"));
        try {
            p.playSound(p.getLocation(), Sound.valueOf(soundType), volume, pitch);
        } catch (IllegalArgumentException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SpigotPing] Can not play a sound. " +
                    "The specified sound-type is not available in your server version, please ensure to read carefully the config file.");
        }
    }
}
