package me.arvia.mcrtd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RtdCommandHandler implements CommandExecutor {
    private final CooldownManager cooldownManager;

    public RtdCommandHandler(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        if (cooldownManager.isCooldownActive(player)) {

        }

        player.sendMessage("Command was received by player: " + player.getName());
        return true;
    }
}
