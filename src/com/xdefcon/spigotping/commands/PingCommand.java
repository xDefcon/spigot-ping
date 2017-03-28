package com.xdefcon.spigotping.commands;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.utils.PingUtil;
import com.xdefcon.spigotping.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PingCommand implements CommandExecutor {
    private SpigotPing plugin;

    public PingCommand(SpigotPing plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info(ChatColor.RED + "This command is only executable as a Player.");
            return true;
        }

        final Player p = (Player) sender;
        if (args.length == 0) {
            if (!hasPerms(p, "spigotping.ping")) {
                String noPerm = plugin.getConfig().getString("permission-system.no-perm-message");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                return true;
            }
            String ping = "" + PingUtil.getPing(p);
            String customMex = plugin.getConfig().getString("ping-command.ping-message").replaceAll("%ping%", ping);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', customMex));
        } else {
            if (!hasPerms(p, "spigotping.ping.others")) {
                String noPerm = plugin.getConfig().getString("others-ping.not-allowed-message");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                return true;
            }
            String target = args.length > 0 ? args[0] : null;
            Player targetP = Bukkit.getPlayer(target);
            if (targetP == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("others-ping.player-not-found")));
                return true;
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("ping-command.ping-target-message")
                            .replace("%ping%", "" + PingUtil.getPing(targetP))
                            .replace("%target%", targetP.getName())));
        }
        if (plugin.getConfig().getBoolean("sound-manager.enabled")) {
            SoundUtil.playSound(p, plugin.getConfig().getString("sound-manager.sound-type"));
        }
        return true;
    }

    private boolean hasPerms(Player p, String perm) {
        return !plugin.getConfig().getBoolean("permission-system.enabled") || p.hasPermission(perm);
    }
}

