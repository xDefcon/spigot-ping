package com.xdefcon.spigotping.commands;

import com.xdefcon.spigotping.SpigotPing;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingReloadCommand implements CommandExecutor {
    private SpigotPing plugin;

    public PingReloadCommand(SpigotPing plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (!sender.hasPermission("spigotping.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("permission-system.no-perm-message")));
            return true;
        }

        plugin.reload();
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.GREEN + "Plugin reloaded.");
        }
        return true;
    }
}
