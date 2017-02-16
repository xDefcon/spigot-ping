package com.xdefcon.spigotping.utils;

import com.xdefcon.spigotping.SpigotPing;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    private static float volume = (float) SpigotPing.getInstance().getConfig().get("sound-manager.volume");
    private static float pitch = (float) SpigotPing.getInstance().getConfig().get("sound-manager.pitch");

    public static void playSound(Player p, String soundType) {
        p.playSound(p.getLocation(), Sound.valueOf(soundType), volume, pitch);
    }
}
