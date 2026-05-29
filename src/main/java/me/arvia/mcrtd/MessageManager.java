package me.arvia.mcrtd;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class MessageManager {
    private Server server;

    public MessageManager(Server server)
    {
        this.server = server;
    }

    public void broadcastString(String message, ChatColor color) {
        server.broadcastMessage(color + "[RTD] " + message);
    }
    public void broadcastString(String message) {
        server.broadcastMessage("[RTD] " + message);
    }

    public void sendString(CommandSender player, String message, ChatColor color)
    {
        player.sendMessage(color + "[RTD] " + message);
    }
    public void sendString(CommandSender player, String message)
    {
        player.sendMessage("[RTD] " + message);
    }

}
